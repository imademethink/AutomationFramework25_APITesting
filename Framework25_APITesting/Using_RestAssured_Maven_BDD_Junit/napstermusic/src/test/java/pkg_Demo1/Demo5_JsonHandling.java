package pkg_Demo1;

import com.jayway.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sk.antons.json.util.JsonFormat;

import java.io.FileReader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Demo5_JsonHandling {

    @Test
    public void Test_Demo51_JsonBuilder(){

/*

{
  "empid": 221221,
  "emergencycontact": "9876543210",
  "email": "sherlock@holmes.com",
  "name": {
    "firstname": "sherlock",
    "lastname": "holmes",
    "gender": "m",
    "dateofbirth": "2000-01-01"
  },
  "address": {
    "housenum": "221b",
    "street": "baker street",
    "city": "london",
    "country": "united kingdom"
  },
  "job": {
    "designation": "detective",
    "department": "secret service",
    "location": "england"
  }
}

*/

        System.out.println("Test_Demo51_JsonRequest=========================");
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("empid", 221221);
        jsonRequest.put("emergencycontact", "9876543210");
        jsonRequest.put("email","sherlock@holmes.com");

            JSONObject jsonName = new JSONObject();
            jsonName.put("firstname", "sherlock");
            jsonName.put("lastname", "holmes");
            jsonName.put("gender", "m");
            jsonName.put("dateofbirth", "2000-01-01");
        jsonRequest.put("name", jsonName);

            JSONObject jsonAddress = new JSONObject();
            jsonAddress.put("housenum", "221b");
            jsonAddress.put("street", "baker street");
            jsonAddress.put("city", "london");
            jsonAddress.put("country", "england");
        jsonRequest.put("address", jsonAddress);

            JSONObject jsonJob = new JSONObject();
            jsonJob.put("designation", "detective");
            jsonJob.put("department", "secret service");
            jsonJob.put("location", "london");
        jsonRequest.put("job", jsonJob);

        System.out.println(jsonRequest.toJSONString());
        System.out.println("\n\n");
        System.out.println(JsonFormat.from(jsonRequest.toJSONString()).indent(2, ' ').toText());
        System.out.println("Test_Demo51_JsonRequest=========================");
    }

    @Test
    public void Test_Demo52_JsonParsing(){
        System.out.println("Test_Demo52_JsonParsing=========================");
        String inputJsonString = "{\"empid\":221221,\"emergencycontact\":\"9876543210\",\"email\":\"sherlock@holmes.com\",\"name\":{\"firstname\":\"sherlock\",\"lastname\":\"holmes\",\"gender\":\"m\",\"dateofbirth\":\"2000-01-01\"},\"address\":{\"housenum\":\"221b\",\"street\":\"baker street\",\"city\":\"london\",\"country\":\"united kingdom\"},\"job\":{\"designation\":\"detective\",\"department\":\"secret service\",\"location\":\"england\"}}";

        JsonPath jsonPath       = new JsonPath(inputJsonString);

        System.out.println("empid : " + jsonPath.get("empid"));

        System.out.println("name  : " + jsonPath.get("name"));

        System.out.println("name.firstname : " + jsonPath.get("name.firstname"));

        // non existing least child node
        System.out.println("address.zip    : " + jsonPath.get("address.zip"));

        System.out.println("Test_Demo52_JsonParsing=========================");
    }

    @Test
    public void Test_Demo53_JsonParsing() throws Exception{
        System.out.println("Test_Demo53_JsonParsing from a file=========================");

        JSONParser jsonParser = new JSONParser();
        Object obj            = jsonParser.parse(new FileReader(
                                System.getProperty("user.dir") + "\\src\\test\\resources\\dummy.json"));
        JSONObject jsonObject = (JSONObject) obj;
        JsonPath jsonPath     = new JsonPath(jsonObject.toJSONString());

        System.out.println("empid : " + jsonPath.get("empid"));

        System.out.println("name  : " + jsonPath.get("name"));

        System.out.println("name.firstname : " + jsonPath.get("name.firstname"));

        // non existing least child node
        System.out.println("address.zip    : " + jsonPath.get("address.zip"));

        System.out.println("Test_Demo52_JsonParsing=========================");
    }

}
