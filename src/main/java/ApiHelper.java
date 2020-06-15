import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    static String baseUrl = "http://bpdts-test-app-v2.herokuapp.com";

    public static RequestSpecification buildSpecification() {
        return new RequestSpecBuilder()
                .setUrlEncodingEnabled(true)
                .addHeader("Content-Type", "application/json")
                .setBaseUri(baseUrl)
                .build();
    }

    public static Response getCityUsers(String city){
        RestAssured.requestSpecification = buildSpecification();
        return given().pathParam("city",city)
                .when().log().all()
                .get("/city/{city}/users")
                .then().log().all()
                .extract().response();
    }

    public static Response getUserById(String id){
        RestAssured.requestSpecification = buildSpecification();
        return given().pathParam("id",id)
                .when().log().all()
                .get("/user/{id}")
                .then().log().all()
                .extract().response();
    }

    public static Response getInstruction(){
        RestAssured.requestSpecification = buildSpecification();
        return  given().when().log().all()
                .get("/instructions")
                .then().log().all()
                .extract().response();
    }

    public static Response getUsers(){
        RestAssured.requestSpecification = buildSpecification();
        return  given().when().log().all()
                .get("/users")
                .then().log().all()
                .extract().response();
    }

    public static String getInstructionExpectedBody()  {
        try {
            return FileUtils.readFileToString(new File("src/main/resources/instructionResponse.json"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}