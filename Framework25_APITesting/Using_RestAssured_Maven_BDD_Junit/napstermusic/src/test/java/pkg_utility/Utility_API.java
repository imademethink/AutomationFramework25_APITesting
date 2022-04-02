package pkg_utility;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;
import pkg_global.GlobalObjects;
import java.io.*;
import java.util.Properties;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;

public class Utility_API extends GlobalObjects {

    private static final Logger myLog = Logger.getLogger(Utility_API.class);

    public void InitAPIRequestBasic(){
        myLog.info("Setting base uri: "+baseUri);
        requestSpecification = given().baseUri(baseUri);
        // set data type for request and response
        myLog.info("Setting header ContentType : "+ ContentType.JSON);
        myLog.info("Setting header AcceptType  : "+ContentType.JSON);
        requestSpecification = requestSpecification.contentType(ContentType.JSON).accept(ContentType.JSON);
        // add basic authentication details
        myLog.info("Setting basic authentication user : "+basicAuthuser);
        myLog.info("Setting basic authentication pwd  : "+basicAuthPassword);
        requestSpecification = requestSpecification.auth().basic(basicAuthuser,basicAuthPassword);
    }


    public JSONObject RegisterPrepareRequest(String endPointv2Register, String happypath) {
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
        System.out.println("Request body       : " + jsonRegister.toJSONString());
        return jsonRegister;
    }

    public void RegisterAnalyseResponse(Response response, int nExpectedStatusCode, String happypath) {
        // analyse HTTP response code
        if(response.getStatusCode() != nExpectedStatusCode){ Assert.fail("Status code expected and actual mismatched");}
    }
}
