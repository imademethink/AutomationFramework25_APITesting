package pkg_Demo1;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;

public class Demo3 {

    public final String baseUri = "http://imademethink.pythonanywhere.com";

    // User registration
    public final String endPointv1Register        = "/v1/register";
    public String bodyRegister                     = "{\"user\": \"james@bond.com\", \"password\": \"London007\"}";

    @Test
    public void Test_Demo31_Register(){
        System.out.println("Test_Demo31_Register");
        bodyRegister        = bodyRegister.replace("james","james" + generateRandomNum());
        System.out.println("Request body       : " + bodyRegister);


        // before
        RequestSpecification requestSpecification =
                given()
                        .baseUri(baseUri)
                        .basePath(endPointv1Register)
                        .contentType(ContentType.JSON);
        // actual
        requestSpecification =
                requestSpecification.
                        body(bodyRegister)
                        .when();
        Response response = requestSpecification
                .post();

        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 201){
            Assert.fail("Invalid response");
        }
        String responseAsString = response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);

        if(jsonPath.get("register").toString().equals("Registration success")){
            System.out.println("Received message: " + jsonPath.get("register").toString());
        }else{
            Assert.fail("Registration success message not received");
        }

        if(jsonPath.get("userid").toString().startsWith("userid_")){
            System.out.println("Received userid : " + jsonPath.get("userid").toString());
        }else{
            Assert.fail("Registration userid message not received");
        }

        // after
        RestAssured.reset();

        System.out.println("\n");
    }

    @Test
    public void Test_Demo32_Register(){
        System.out.println("Test_Demo32_Register");
        bodyRegister        = bodyRegister.replace("james","james" + generateRandomNum());
        System.out.println("Request body       : " + bodyRegister);

        // before
        RequestSpecification requestSpecification =
                given()
                        .baseUri(baseUri)
                        .basePath(endPointv1Register)
                        .contentType(ContentType.JSON);
        // actual
        requestSpecification =
                requestSpecification.
                        body(bodyRegister)
                        .when();
        Response response = requestSpecification
                .post();

        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 201){
            Assert.fail("Invalid response");
        }
        String responseAsString = response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);

        if(jsonPath.get("register").toString().equals("Registration success")){
            System.out.println("Received message: " + jsonPath.get("register").toString());
        }else{
            Assert.fail("Registration success message not received");
        }

        if(jsonPath.get("userid").toString().startsWith("userid_")){
            System.out.println("Received userid : " + jsonPath.get("userid").toString());
        }else{
            Assert.fail("Registration userid message not received");
        }

        // after
        RestAssured.reset();

        System.out.println("\n");
    }


    public static String generateRandomNum() {
        Random r = new Random();
        return String.valueOf(r.nextInt((9999 - 1000) + 1) + 1000);
    }


}
