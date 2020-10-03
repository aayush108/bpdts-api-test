import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

//@RunWith(DataProviderRunner.class)
public class MyTest {


    @Test(dataProvider = "SampleTestData", dataProviderClass = ApiHelper.class)
    public void myCxptest(String nino, String fieldIds) throws IOException, IOException {
//        String nino = ApiHelper.getPropValues("nino");
//        String fieldIds = ApiHelper.getPropValues("fieldId3");
        Response response = ApiHelper.getFieldValues(nino, fieldIds);

    }

    @Test
    public void systemDir() {
        String abc = System.getProperty("user.dir");
        System.out.print(abc);
    }

}
