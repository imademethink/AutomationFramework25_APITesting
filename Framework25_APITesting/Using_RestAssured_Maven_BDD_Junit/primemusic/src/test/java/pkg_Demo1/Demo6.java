package pkg_Demo1;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Demo6 {

    public final String baseUri       = "http://imademethink.pythonanywhere.com";
    public static String userEmail    = null;
    public static String userPassword = null;

    // User registration endpoint
    public final String endPointv1Register        = "/v1/register";
    // User login endpoint
    public final String endPointv1Login           = "/v1/login";
    // User change password endpoint
    public final String endPointv1Changepassword  = "/v1/changepassword";
    // User logout endpoint
    public final String endPointv1Logout          = "/v1/logout";

    RequestSpecification requestSpecification = null;

    @Before
    public void setup(){
        System.out.println("==before==");
        requestSpecification = given().baseUri(baseUri).contentType(ContentType.JSON);
    }

    @After
    public void teardown(){
        System.out.println("==after==");
        RestAssured.reset();
        System.out.println("\n");
    }

    @Test
    public void Test_Demo61_Register(){
        System.out.println("...........Test_Demo61_Register...........");
        // prepare request body
        userEmail               = "james" + generateRandomNum() + "@bond.com";
        userPassword            = "London" + generateRandomNum();
        JSONObject jsonRegister = new JSONObject();
        jsonRegister.put("user",userEmail);
        jsonRegister.put("password",userPassword);
        System.out.println("Request body       : " + jsonRegister.toJSONString());

        // raise POST request
        requestSpecification = requestSpecification.basePath(endPointv1Register).body(jsonRegister.toJSONString()).when();
        Response response     = requestSpecification.post();

        // analyse HTTP response code
        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 201){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
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
    }

    @Test
    public void Test_Demo62_Login(){
        System.out.println("...........Test_Demo62_Login...........");
        // prepare request body
        JSONObject jsonLogin  = new JSONObject();
        jsonLogin.put("user",userEmail);
        jsonLogin.put("password",userPassword);
        System.out.println("Request body       : " + jsonLogin.toJSONString());

        // raise POST request
        requestSpecification = requestSpecification.basePath(endPointv1Login).body(jsonLogin.toJSONString()).when();
        Response response     = requestSpecification.post();

        // analyse HTTP response code
        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("login").toString().equals("Login success")){
            System.out.println("Received message: " + jsonPath.get("login").toString());
        }else{
            Assert.fail("Login success message not received");
        }

        if(! jsonPath.get("token").toString().isEmpty()){
            System.out.println("Received token : " + jsonPath.get("token").toString());
        }else{
            Assert.fail("Login token not received");
        }
    }

    @Test
    public void Test_Demo63_ChangePassword(){
        System.out.println("...........Test_Demo63_ChangePassword...........");
        // prepare request body
        JSONObject jsonChangePassword  = new JSONObject();
        jsonChangePassword.put("user",userEmail);
        jsonChangePassword.put("password",userPassword);
        userPassword                   = userPassword.replace("London","Paris");
        jsonChangePassword.put("newpassword",userPassword);
        System.out.println("Request body       : " + jsonChangePassword.toJSONString());

        // raise PUT request
        requestSpecification = requestSpecification.basePath(endPointv1Changepassword).body(jsonChangePassword.toJSONString()).when();
        Response response     = requestSpecification.put();

        // analyse HTTP response code
        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("changepassword").toString().equals("Change password success")){
            System.out.println("Received message: " + jsonPath.get("changepassword").toString());
        }else{
            Assert.fail("Change password success message not received");
        }
    }

    @Test
    public void Test_Demo64_Logout(){
        System.out.println("...........Test_Demo64_Logout...........");
        // prepare request body
        JSONObject jsonLogin  = new JSONObject();
        jsonLogin.put("user",userEmail);
        jsonLogin.put("password",userPassword);
        System.out.println("Request body       : " + jsonLogin.toJSONString());

        // raise POST request
        requestSpecification = requestSpecification.basePath(endPointv1Logout).body(jsonLogin.toJSONString()).when();
        Response response     = requestSpecification.post();

        // analyse HTTP response code
        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("logout").toString().equals("Logout success")){
            System.out.println("Received message: " + jsonPath.get("logout").toString());
        }else{
            Assert.fail("Logout success message not received");
        }
    }


    public static String generateRandomNum() {
        Random r = new Random();
        return String.valueOf(r.nextInt((9999 - 1000) + 1) + 1000);
    }


}


