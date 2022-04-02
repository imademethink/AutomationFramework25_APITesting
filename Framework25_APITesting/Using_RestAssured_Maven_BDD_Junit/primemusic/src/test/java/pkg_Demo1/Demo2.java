package pkg_Demo1;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import java.util.Random;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class Demo2 {

    public final String baseUri = "http://imademethink.pythonanywhere.com";

    // User registration
    public final String endPointv1Register        = "/v1/register";
    public String bodyRegister                     = "{\"user\": \"james@bond.com\", \"password\": \"London007\"}";


    @Test
    public void Test_Demo21_Register(){
        String sRandomNumber = generateRandomNum();
        bodyRegister        = bodyRegister.replace("james","james" + sRandomNumber);
        System.out.println("Test_Demo21_Register");
        System.out.println("Request body: " + bodyRegister);

        given()
               .baseUri(baseUri)
               .basePath(endPointv1Register)
               .contentType(ContentType.JSON)
               .accept(ContentType.JSON)
               .body(bodyRegister)
        .when()
               .post()
        .then()
               .statusCode(201)
               .body("register", equalTo("Registration success"))
               .body("userid", startsWith("userid_"));
        System.out.println("\n");
    }

    @Test
    public void Test_Demo22_Register(){
        String sRandomNumber = generateRandomNum();
        bodyRegister        = bodyRegister.replace("james","james" + sRandomNumber);
        System.out.println("Test_Demo22_Register");
        System.out.println("Request body: " + bodyRegister);

        RequestSpecification requestSpecification =
                                given()
                                       .baseUri(baseUri)
                                       .basePath(endPointv1Register)
                                       .contentType(ContentType.JSON)
                                       .body(bodyRegister)
                                       .when();
        Response response = requestSpecification
                                 .post();
        response.then()
                    .statusCode(201)
                    .body("register", equalTo("Registration success"))
                    .body("userid", startsWith("userid_"));
        System.out.println("\n");
    }

    @Test
    public void Test_Demo23_Register(){
        String sRandomNumber = generateRandomNum();
        bodyRegister        = bodyRegister.replace("james","james" + sRandomNumber);
        System.out.println("Test_Demo23_Register");
        System.out.println("Request body: " + bodyRegister);

        RequestSpecification requestSpecification =
                given()
                    .baseUri(baseUri)
                    .basePath(endPointv1Register)
                    .contentType(ContentType.JSON)
                    .body(bodyRegister)
                    .when();
        Response response = requestSpecification
                .post();
        response
                .then()
                    .statusCode(201)
                    .body("register", equalTo("Registration success"))
                    .body("userid", startsWith("userid_"));
        System.out.println("\n");
    }

    @Test
    public void Test_Demo24_Register(){
        String sRandomNumber = generateRandomNum();
        bodyRegister        = bodyRegister.replace("james","james" + sRandomNumber);
        System.out.println("Test_Demo24_Register");
        System.out.println("Request body      : " + bodyRegister);

        RequestSpecification requestSpecification =
                given()
                        .baseUri(baseUri)
                        .basePath(endPointv1Register)
                        .contentType(ContentType.JSON)
                        .body(bodyRegister)
                        .when();
        Response response = requestSpecification
                .post();
        System.out.println("Response http code : " + response.getStatusCode());
        System.out.println("Response body      : " + response.asString());
        response
                .then()
                .statusCode(201)
                .body("register", equalTo("Registration success"))
                .body("userid", startsWith("userid_"));
        System.out.println("\n");
    }

    @Test
    public void Test_Demo25_Register(){
        System.out.println("Test_Demo25_Register");
        bodyRegister        = bodyRegister.replace("james","james&");
        System.out.println("Request body      : " + bodyRegister);

        RequestSpecification requestSpecification =
                given()
                        .baseUri(baseUri)
                        .basePath(endPointv1Register)
                        .contentType(ContentType.JSON)
                        .body(bodyRegister)
                        .when();
        Response response = requestSpecification
                .post();

        System.out.println("Response http code : " + response.getStatusCode());
        System.out.println("Response body      : " + response.asString());
        if(response.getStatusCode() != 201){
            Assert.fail("Invalid response");
        }

        response
                .then()
                .statusCode(201)
                .body("register", equalTo("Registration success"))
                .body("userid", startsWith("userid_"));
        System.out.println("\n");
    }









    public static String generateRandomNum() {
        Random r = new Random();
        return String.valueOf(r.nextInt((9999 - 1000) + 1) + 1000);
    }


}
