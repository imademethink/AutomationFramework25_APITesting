package pkg_global;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import pkg_utility.Utility_API;
import pkg_utility.Utility_General;

import java.util.HashMap;

public class GlobalObjects {

    // Logger
    private static final Logger myLog = Logger.getLogger(GlobalObjects.class);

    // A hash-map of all data items
    public static HashMap<String, String> hmGlobalData    = new HashMap<>();

   // Utility handler objects
    public static Utility_API utilAPI                     = null;
    public static Utility_General utilGeneral             = null;

    public final String baseUri           = "http://imademethink.pythonanywhere.com";

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

    public static RequestSpecification requestSpecification = null;
    public static Response response                         = null;

}
