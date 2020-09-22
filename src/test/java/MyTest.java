import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import javax.management.StandardEmitterMBean;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class MyTest {


    @Test
    public void myCxptest() throws IOException {
        String nino = ApiHelper.getPropValues("nino");
        String fieldIds = ApiHelper.getPropValues("fieldId3");
        Response response = ApiHelper.getFieldValues(nino, fieldIds);

    }

}
