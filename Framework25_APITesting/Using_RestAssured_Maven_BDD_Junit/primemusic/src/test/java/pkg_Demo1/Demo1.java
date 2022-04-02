package pkg_Demo1;
import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

public class Demo1 {

    public final String baseUri  = "http://imademethink.pythonanywhere.com";
    public final String endPoint = "home";

    @Test
    public void Test_Demo1_GETapi(){
        System.out.println("Test_Demo1_Register");

        given()
               .baseUri(baseUri)
               .basePath(endPoint)
        .when()
               .get()
        .then()
               .statusCode(200)
               .body(containsString("Welcome to REST API webapp for user management"));
        System.out.println("\n");
    }




}
