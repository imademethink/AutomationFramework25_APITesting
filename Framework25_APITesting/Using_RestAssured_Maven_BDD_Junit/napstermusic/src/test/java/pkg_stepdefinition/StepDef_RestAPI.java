package pkg_stepdefinition;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.http.Status;
import com.jayway.restassured.path.json.JsonPath;
import com.sun.mail.iap.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;
import pkg_global.GlobalObjects;
import pkg_utility.Utility_General;
import javax.swing.text.AbstractDocument;

public class StepDef_RestAPI extends GlobalObjects {

    private static final Logger myLog = Logger.getLogger(StepDef_RestAPI.class);

    @When("^Register user with valid data$")
    public void Register_user_with_valid_data(){
        myLog.info("Register_user_with_valid_data");

        JSONObject bodyRegister = utilAPI.RegisterPrepareRequest(endPointv2Register, "happypath");

        // prepare request body
        userEmail               = "jo" + Utility_General.generateRandomNum() + "@aa.com";
        userPassword            = "London" + Utility_General.generateRandomNum();
        captcha                 = Utility_General.getCurrentYear();

        // raise POST request
        myLog.info("Setting endpoint   : " + endPointv2Register);
        requestSpecification = requestSpecification.basePath(endPointv2Register).body(bodyRegister.toJSONString()).when();
        response             = requestSpecification.post();

        utilAPI.RegisterAnalyseResponse(response, 201, "happypath");
    }

    @When("^Register user with invalid data$")
    public void Register_user_with_invalid_data(){
        myLog.info("Register_user_with_invalid_data");
    }

    @Then("^Registration should be successful$")
    public void Registration_should_be_successful(){
        myLog.info("Registration_should_be_successful");

        myLog.info("Response header    : " + response.getHeader("Content-Type"));
        myLog.info("Response http code : " + response.getStatusCode());
        String responseAsString= response.asString();
        myLog.info("Response body      : " + responseAsString.replace("\n",""));

        // analyse HTTP response code
        if(response.getStatusCode() != 201){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("register").toString().equals("Registration success")){
            myLog.info("Received message: " + jsonPath.get("register").toString());
        }else{
            Assert.fail("Registration success message not received");
        }

        if(jsonPath.get("userid").toString().startsWith("userid_")){
            myLog.info("Received userid : " + jsonPath.get("userid").toString());
        }else{
            Assert.fail("Registration userid message not received");
        }
    }

    @And("^Registration should NOT be successful$")
    public void Registration_should_NOT_be_successful(){
        myLog.info("Registration_should_NOT_be_successful");
    }

    @When("^Registration with valid user data should be successful$")
    public void Registration_with_valid_user_data_should_be_successful(){
        myLog.info("Registration_with_valid_user_data_should_be_successful");

        // prepare request body
        userEmail               = "jo" + Utility_General.generateRandomNum() + "@aa.com";
        userPassword            = "London" + Utility_General.generateRandomNum();
        captcha                 = Utility_General.getCurrentYear();
        JSONObject jsonRegister = new JSONObject();
        jsonRegister.put("user",userEmail);
        jsonRegister.put("password",userPassword);
        jsonRegister.put("firstname",firstname);
        jsonRegister.put("lastname",lastname);
        jsonRegister.put("captcha",captcha);
        myLog.info("Request body       : " + jsonRegister.toJSONString());

        // raise POST request
        myLog.info("Setting endpoint   : " + endPointv2Register);
        requestSpecification = requestSpecification.basePath(endPointv2Register).body(jsonRegister.toJSONString()).when();
        response     = requestSpecification.post();

        myLog.info("Response http code : " + response.getStatusCode());
        String responseAsString= response.asString();
        myLog.info("Response body      : " + responseAsString.replace("\n",""));

        // analyse HTTP response code
        if(response.getStatusCode() != 201){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("register").toString().equals("Registration success")){
            myLog.info("Received message: " + jsonPath.get("register").toString());
        }else{
            Assert.fail("Registration success message not received");
        }

        if(jsonPath.get("userid").toString().startsWith("userid_")){
            myLog.info("Received userid : " + jsonPath.get("userid").toString());
        }else{
            Assert.fail("Registration userid message not received");
        }
    }

    @Then("^Registration with invalid user data should be successful$")
    public void Registration_with_invalid_user_data_should_be_successful(){
        myLog.info("Registration_with_invalid_user_data_should_be_successful");
    }

    @When("^Login with invalid user data should NOT be successful$")
    public void Login_with_invalid_user_data_should_NOT_be_successful(){
        myLog.info("Login_with_invalid_user_data_should_NOT_be_successful");
    }

    @When("^Login with valid user data should be successful$")
    public void Login_with_valid_user_data_should_be_successful(){
        myLog.info("Login_with_valid_user_data_should_be_successful");
        // prepare request body
        JSONObject jsonLogin  = new JSONObject();
        jsonLogin.put("user",userEmail);
        jsonLogin.put("password",userPassword);
        myLog.info("Request body       : " + jsonLogin.toJSONString());

        // raise POST request
        myLog.info("Setting endpoint   : " + endPointv2Login);
        requestSpecification = requestSpecification.basePath(endPointv2Login).body(jsonLogin.toJSONString()).when();
        response     = requestSpecification.post();

        // analyse HTTP response code
        myLog.info("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        myLog.info("Response body      : " + responseAsString.replace("\n",""));

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("login").toString().equals("Login success")){
            myLog.info("Received message: " + jsonPath.get("login").toString());
        }else{
            Assert.fail("Login success message not received");
        }

        if(! jsonPath.get("token").toString().isEmpty()){
            myLog.info("Received token : " + jsonPath.get("token").toString());
        }else{
            Assert.fail("Login token not received");
        }
    }

    @When("^Update profile with valid data should be successful$")
    public void Update_profile_with_valid_data_should_be_successful(){
        myLog.info("Update_profile_with_valid_data_should_be_successful");
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
        myLog.info("Request body       : " + jsonUpdateProfile.toJSONString());

        // raise PUT request
        myLog.info("Setting endpoint   : " + endPointv2UpdateProfile);
        requestSpecification = requestSpecification.basePath(endPointv2UpdateProfile).body(jsonUpdateProfile.toJSONString()).when();
        response             = requestSpecification.put();

        // analyse HTTP response code
        myLog.info("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        myLog.info("Response body      : " + responseAsString.replace("\n",""));

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("updateprofile").toString().equals("Update profile success")){
            myLog.info("Received message: " + jsonPath.get("updateprofile").toString());
        }else{
            Assert.fail("Update profile success message not received");
        }
    }

    @When("^Update profile with invalid data should NOT be successful$")
    public void Update_profile_with_invalid_data_should_NOT_be_successful(){
        myLog.info("Update_profile_with_invalid_data_should_NOT_be_successful");
    }

    @Then("^Logout with valid user data should be successful$")
    public void Logout_with_valid_user_data_should_be_successful(){
        myLog.info("Logout_with_valid_user_data_should_be_successful");
        // prepare request body
        JSONObject jsonLogin  = new JSONObject();
        jsonLogin.put("user",userEmail);
        jsonLogin.put("password",userPassword);
        myLog.info("Request body       : " + jsonLogin.toJSONString());

        // raise POST request
        myLog.info("Setting endpoint   : " + endPointv2Logout);
        requestSpecification = requestSpecification.basePath(endPointv2Logout).body(jsonLogin.toJSONString()).when();
        response             = requestSpecification.post();

        // analyse HTTP response code
        myLog.info("Response http code : " + response.getStatusCode());
        if(response.getStatusCode() != 200){ Assert.fail("Invalid response");}

        // analyse HTTP response body
        String responseAsString= response.asString();
        myLog.info("Response body      : " + responseAsString.replace("\n",""));

        JsonPath jsonPath       = new JsonPath(responseAsString);
        // Response body validation
        if(jsonPath.get("logout").toString().equals("Logout success")){
            myLog.info("Received message: " + jsonPath.get("logout").toString());
        }else{
            Assert.fail("Logout success message not received");
        }
    }


}
