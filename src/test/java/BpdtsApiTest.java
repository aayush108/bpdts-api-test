import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners(ExtentReportListener.class)
public class BpdtsApiTest extends ExtentReportListener {


    @Test
    public void verifyGetCityUsersAPI() {

        Response response = ApiHelper.getCityUsers("london");
        ArrayList<String> list = new ArrayList<String>();
        list = response.path("");
        assertThat(response.statusCode()).as("Verify response code is 200").isEqualTo(HttpStatus.SC_OK);
        assertThat(list.size()).as("Verify that the response is an array with size 0").isEqualTo(0);

        test.log(LogStatus.INFO, "Response body is", response.getBody().prettyPrint());
    }

    @Test
    public void verifyGetInstructionAPI() throws JSONException {
        Response response = ApiHelper.getInstruction();
        String expectedBody = ApiHelper.getInstructionExpectedBody();
        String actualBody = response.getBody().asString();
        assertThat(response.statusCode()).as("Verify response code is 200").isEqualTo(HttpStatus.SC_OK);
//        Assertions.assertThat(expectedBody).as("Verify the response body").isEqualTo(actualBody);
        JSONAssert.assertEquals(expectedBody, actualBody, JSONCompareMode.STRICT);

//        test.log(LogStatus.INFO,"Strating test execution");
        test.log(LogStatus.INFO, response.getBody().prettyPrint());
    }


    @Test
    public void verifyGetUsersByIdAPI() {
        Response response = ApiHelper.getUserById("123");

        assertThat(response.statusCode()).as("Verify response code is 200").isEqualTo(HttpStatus.SC_OK);
        Assertions.assertThat((String) response.path("first_name")).as("Verify First Name").isEqualTo("Anderea");
        Assertions.assertThat((String) response.path("last_name")).as("Verify Last Name").isEqualTo("Highnam");
        Assertions.assertThat((String) response.path("email")).as("Verify email").isEqualTo("ahighnam3e@wix.com");
        Assertions.assertThat((String) response.path("city")).as("Verify city").isEqualTo("Merkezköy");

        test.log(LogStatus.INFO, "Response body is", response.getBody().prettyPrint());
    }


    @Test
    public void checkGetUserById_ForAn_IncorrectId(){
        String expectedMessage = "Id 99999 doesn't exist. You have requested this URI [/user/99999] but did you mean /user/<string:id> ?";
        Response response = ApiHelper.getUserById("99999");

        assertThat(response.statusCode()).as("Verify response code is 404").isEqualTo(HttpStatus.SC_NOT_FOUND);
        Assertions.assertThat((String) response.path("message")).as("Verify error message").isEqualTo(expectedMessage);
        test.log(LogStatus.INFO, "Response body is", response.getBody().prettyPrint());

    }

    @Test
    public void checkGetUsersAPIResponse() {
        Response response = ApiHelper.getUsers();
        ArrayList<String> list = response.path("");

        assertThat(response.statusCode()).as("Verify response code is 200").isEqualTo(HttpStatus.SC_OK);
        assertThat(list.size()).as("Verify the number of users").isEqualTo(1000);

        Assertions.assertThat((String)response.path("[1].first_name")).as("Verify First Name of the 2nd user").isEqualTo("Bendix");
        Assertions.assertThat((String)response.path("[1].last_name")).as("Verify Last Name of the 2nd user").isEqualTo("Halgarth");
        Assertions.assertThat((String)response.path("[1].email")).as("Verify email of the 2nd user").isEqualTo("bhalgarth1@timesonline.co.uk");
        Assertions.assertThat((String)response.path("[1].ip_address")).as("Verify IP address of the 2nd user").isEqualTo("4.185.73.82");

        test.log(LogStatus.INFO, "Response body is", response.getBody().prettyPrint());
    }

}
