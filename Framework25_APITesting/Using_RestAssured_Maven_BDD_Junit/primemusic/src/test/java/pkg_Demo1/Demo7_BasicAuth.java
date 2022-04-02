package pkg_Demo1;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.*;
import org.junit.runners.MethodSorters;
import java.util.Calendar;
import java.util.Random;
import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Demo7_BasicAuth {

    public final String baseUri       = "http://imademethink.pythonanywhere.com";

    public static String basicAuthuser    = "diyuser";
    public static String basicAuthPassword= "diypwd";

    public static String userEmail    = null;
    public static String userPassword = null;
    public static String firstname    = "Sherlock";
    public static String lastname     = "Holmes";
    public static String captcha      = null;

    // User registration endpoint
    public final String endPointv2Register        = "/v2/register";
    // User login endpoint
    public final String endPointv2Login           = "/v2/login";
    // User change password endpoint
    public final String endPointv2UpdateProfile  = "/v2/updateprofile";
    // User get account endpoint
    public final String endPointv2GetAccount      = "/v2/getaccount";
    // User logout endpoint
    public final String endPointv2Logout          = "/v2/logout";

    RequestSpecification requestSpecification = null;

    @Before
    public void setup(){
        System.out.println("==before==");
        requestSpecification = given().baseUri(baseUri);
        // set data type for request and response
        requestSpecification = requestSpecification.contentType(ContentType.JSON).accept(ContentType.JSON);
        // add basic authentication details
        requestSpecification = requestSpecification.auth().basic(basicAuthuser,basicAuthPassword);
    }

    @After
    public void teardown(){
        System.out.println("==after==");
        RestAssured.reset();
        System.out.println("\n");
    }

    @Test
    public void Test_Demo71_Register(){
        System.out.println("...........Test_Demo71_Register...........");
        // prepare request body
        userEmail               = "jo" + generateRandomNum() + "@aa.com";
        userPassword            = "London" + generateRandomNum();
        captcha                 = getCurrentYear();
        JSONObject jsonRegister = new JSONObject();
        jsonRegister.put("user",userEmail);
        jsonRegister.put("password",userPassword);
        jsonRegister.put("firstname",firstname);
        jsonRegister.put("lastname",lastname);
        jsonRegister.put("captcha",captcha);
        System.out.println("Request body       : " + jsonRegister.toJSONString());

        // raise POST request
        requestSpecification = requestSpecification.basePath(endPointv2Register).body(jsonRegister.toJSONString()).when();
        Response response     = requestSpecification.post();

        System.out.println("Response http code : " + response.getStatusCode());
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        // analyse HTTP response code
        if(response.getStatusCode() != 201){ Assert.fail("Invalid response");}

        // analyse HTTP response body
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
    public void Test_Demo72_Login(){
        System.out.println("...........Test_Demo72_Login...........");
        // prepare request body
        JSONObject jsonLogin  = new JSONObject();
        jsonLogin.put("user",userEmail);
        jsonLogin.put("password",userPassword);
        System.out.println("Request body       : " + jsonLogin.toJSONString());

        // raise POST request
        requestSpecification = requestSpecification.basePath(endPointv2Login).body(jsonLogin.toJSONString()).when();
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
    public void Test_Demo73_UpdateProfile(){
        System.out.println("...........Test_Demo73_UpdateProfile...........");
        // prepare request body
        JSONObject jsonUpdateProfile  = new JSONObject();
        jsonUpdateProfile.put("user",userEmail);
        jsonUpdateProfile.put("password",userPassword);
        userPassword                   = userPassword.replace("London","Paris");
        jsonUpdateProfile.put("newpassword",userPassword);
        firstname                      = "Clint";
        lastname                       = "Eastwood";
        jsonUpdateProfile.put("firstname",firstname);
        jsonUpdateProfile.put("lastname",lastname);
        System.out.println("Request body       : " + jsonUpdateProfile.toJSONString());

        // raise PUT request
        requestSpecification = requestSpecification.basePath(endPointv2UpdateProfile).body(jsonUpdateProfile.toJSONString()).when();
        Response response     = requestSpecification.put();

        // analyse HTTP response code
        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("updateprofile").toString().equals("Update profile success")){
            System.out.println("Received message: " + jsonPath.get("updateprofile").toString());
        }else{
            Assert.fail("Update profile success message not received");
        }
    }

    @Test
    public void Test_Demo74_GetAccount(){
        System.out.println("...........Test_Demo74_GetAccount...........");
        // prepare request query params
        requestSpecification.queryParam("user",userEmail);
        requestSpecification.queryParam("password",userPassword);
        System.out.println("Request query params      : " + userEmail + " " + userPassword);

        // raise GET request
        requestSpecification = requestSpecification.basePath(endPointv2GetAccount).when();
        Response response     = requestSpecification.get();

        // analyse HTTP response code
        System.out.println("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        System.out.println("Response body      : " + responseAsString);

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(! jsonPath.get("account").toString().isEmpty()){
            System.out.println("Received message: " + jsonPath.get("account").toString());
        }else{
            Assert.fail("Get Account message not received");
        }
    }

    @Test
    public void Test_Demo75_Logout(){
        System.out.println("...........Test_Demo75_Logout...........");
        // prepare request body
        JSONObject jsonLogin  = new JSONObject();
        jsonLogin.put("user",userEmail);
        jsonLogin.put("password",userPassword);
        System.out.println("Request body       : " + jsonLogin.toJSONString());

        // raise POST request
        requestSpecification = requestSpecification.basePath(endPointv2Logout).body(jsonLogin.toJSONString()).when();
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

    public static String getCurrentYear() {
        return String.valueOf( Calendar.getInstance().get(Calendar.YEAR) );
    }

}


