package pkg_utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utility_Sid {
    public static void main(String[] args) {

        String inputFilePath    = System.getProperty("user.dir") + "\\src\\test\\resources\\externalData\\input_post_request.json";

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(inputFilePath))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject fullRequestBody = (JSONObject) obj;
            System.out.println(fullRequestBody);
            System.out.println("=========================");

            JSONObject proposalsBody = (JSONObject) fullRequestBody.get("proposals");
            System.out.println(proposalsBody);
            System.out.println("=========================");

            String proposalDescriptionBody = (String) proposalsBody.get("proposal_description");
            System.out.println(proposalDescriptionBody);
            System.out.println("=========================");

            proposalsBody.put("proposal_description",proposalDescriptionBody + generateRandomNum());
            String proposalDescriptionBody2 = (String) proposalsBody.get("proposal_description");
            System.out.println(proposalDescriptionBody2);
            System.out.println("=========================");


            JSONArray proposals_proposalsBody_Body = (JSONArray) proposalsBody.get("proposal_sponsors");
            JSONObject oneProposal                 = (JSONObject) proposals_proposalsBody_Body.get(0);
            System.out.println(oneProposal);
            System.out.println("=========================");
            System.out.println(oneProposal.get("sponsor_party_id"));
            oneProposal.put("sponsor_party_id",555);
            System.out.println(proposals_proposalsBody_Body);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static String generateRandomNum() {
        Random r = new Random();
        return String.valueOf(r.nextInt((9999 - 1000) + 1) + 1000);
    }

}
