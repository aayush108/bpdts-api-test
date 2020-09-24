import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;


//@RunWith(DataProviderRunner.class)
public class MyTest {


    @Test(dataProvider = "SampleTestData", dataProviderClass = ApiHelper.class)
//    @UseDataProvider("loadTestData")
    public void myCxptest(String nino, String fieldIds) throws IOException {
//        String nino = ApiHelper.getPropValues("nino");
//        String fieldIds = ApiHelper.getPropValues("fieldId3");
        Response response = ApiHelper.getFieldValues(nino, fieldIds);

    }

}
