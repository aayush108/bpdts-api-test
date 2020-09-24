import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

@RunWith(DataProviderRunner.class)
public class ApiHelper {

    static String baseUrl = "http://bpdts-test-app-v2.herokuapp.com";
    static String baseURI = "https://test-api.peracto3/pub/";

    public static RequestSpecification buildSpecification() {

        return new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .addHeader("Content-Type", "application/json")
                .setBaseUri(baseUrl)
                .log(LogDetail.ALL)
                .build();
    }

    public static Response getCityUsers(String city) {
        RestAssured.requestSpecification = buildSpecification();
        return given().pathParam("city", city)
                .when()
                .get("/city/{city}/users")
                .then().log().all()
                .extract().response();
    }

    public static Response getUserById(String id) {
        RestAssured.requestSpecification = buildSpecification();
        return given().pathParam("id", id)
                .when().log().all()
                .get("/user/{id}")
                .then().log().all()
                .extract().response();
    }

    public static Response getInstruction() {
        RestAssured.requestSpecification = buildSpecification();
        return given().when()
                .get("/instructions")
                .then().log().all()
                .extract().response();
    }

    public static Response getUsers() {
        RestAssured.requestSpecification = buildSpecification();
        return given().when().log().all()
                .get("/users")
                .then().log().all()
                .extract().response();
    }

    public static String getInstructionExpectedBody() {
        try {
            return FileUtils.readFileToString(new File("src/main/resources/instructionResponse.json"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RequestSpecification cxpRequestSpec() {
        return new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .setBaseUri("http://localhost:8080")
                .setBasePath("challenge/v0")
                .addHeader("myHeader", "testHeader")
                .setContentType(ContentType.JSON)
//                .addQueryParam("fieldIds", )
                .log(LogDetail.ALL)
                .build();

    }

    public static Response getFieldValues(String nino, String fieldIds) throws IOException {
        RestAssured.requestSpecification = cxpRequestSpec();
        return given()
                .pathParam("nino", nino)
                .queryParam("fieldIds", fieldIds)
                .when()
                .get("/{nino}")
                .then().log().all()
                .extract().response();
    }

    public static String getPropValues(String property) throws IOException {
        InputStream inputStream;
        String result = "";
        try {
            Properties prop = new Properties();
            String propFileName = "testData.properties";
            inputStream = ApiHelper.class.getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            result = prop.getProperty(property);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return result;
    }

    @DataProvider(name = "SampleTestData")
    public static Object[][] loadTestData() {
        return new Object[][]{
                {"nino1", "1,3,4"},
                {"nino2", "1,2"},
                {"nino3", "1,2,3"},
        };
    }
}

