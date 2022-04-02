package pkg_utility;
import com.cucumber.listener.Reporter;
import com.jayway.restassured.http.ContentType;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import pkg_global.GlobalObjects;
import java.io.File;
import java.util.Calendar;
import java.util.Random;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;

public class Utility_General extends GlobalObjects {

    private static final Logger myLog = Logger.getLogger(Utility_General.class);

    public static void Sleep(int nMillisec){
        try{Thread.sleep(nMillisec);}catch(Exception time){}
    }

    public void GetCommandLineParam(){
        // e.g. // -Denvironment=sit
        hmGlobalData.put("baseUrl",      System.getProperty("baseUrl"));
    }

    public void InitAPIRequestBasic(){
        myLog.info("Setting base uri: "+baseUri);
        requestSpecification = given().baseUri(baseUri);
        // set data type for request and response
        myLog.info("Setting header ContentType : "+ContentType.JSON);
        myLog.info("Setting header AcceptType  : "+ContentType.JSON);
        requestSpecification = requestSpecification.contentType(ContentType.JSON).accept(ContentType.JSON);
        // add basic authentication details
        myLog.info("Setting basic authentication user : "+basicAuthuser);
        myLog.info("Setting basic authentication pwd  : "+basicAuthPassword);
        requestSpecification = requestSpecification.auth().basic(basicAuthuser,basicAuthPassword);
    }

    public void ResetAPIRequestBasic(){
        myLog.info("Resetting base Restassured");
        RestAssured.reset();
    }

    public static String generateRandomNum() {
        Random r = new Random();
        return String.valueOf(r.nextInt((9999 - 1000) + 1) + 1000);
    }

    public static String getCurrentYear() {
        return String.valueOf( Calendar.getInstance().get(Calendar.YEAR) );
    }

    public void GenerateReport(){
        try{
            Reporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/src/test/resources/extent-config.xml"));
            Reporter.setSystemInfo("Test User", "Tony Stark");
            Reporter.setSystemInfo("Operating System Type", "Windows");
            Reporter.setSystemInfo("Build version", "v 1.2.3");
            Reporter.setTestRunnerOutput("Extent Report for Regression");

            myLog.info("Log: Html report generated");
        }catch(Exception eReport){
            myLog.info("Log: Html report NOT generated");
        }
    }

    public void SendReportViaEmail(){
        if(hmGlobalData.get("param_email_report").contains("false")) return;

        myLog.info("\nLog: Please make sure to disable 2 step login for your GMAIL\n");
        myLog.info("\nLog: Please make sure to enable Less secure app login from your GMAIL\n");

        try{
            String sHost      = "smtp.gmail.com";
            int nPort         = 587;   //  465 is for SSL and 587 is for TLS
            String sGmailuser = hmGlobalData.get("param_email_report_from_email");
            String sGmailPwd  = hmGlobalData.get("param_email_report_gmail_pwd");

            String sEmailAddrFrom=hmGlobalData.get("param_email_report_from_email");
            String sEmailAddrTo  =hmGlobalData.get("param_email_report_to_email");
            String sEmailSubject = "Automation Test Report";
            String sEmailBody    = "Please find attached Automation Test Report";

            HtmlEmail email      = new HtmlEmail ();
            email.setHostName(sHost);
            email.setSmtpPort(nPort);
            email.setAuthenticator(new DefaultAuthenticator(sGmailuser, sGmailPwd));
            email.setSSLOnConnect(true);
            email.setFrom(sEmailAddrFrom);
            email.setSubject(sEmailSubject);
            email.setMsg(sEmailBody);
            email.addTo(sEmailAddrTo);

            if(hmGlobalData.get("param_email_attachment").contains("true") ){
                String sReportFilePath = System.getProperty("user.dir") + "\\target\\html\\report.html";
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(sReportFilePath);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setName("report.html");
                email.attach(attachment);
                // Remember to all attach all screenshots generated under "\\target\\html" folder
            }

            email.send();
            myLog.info("Log: Sending report via email success");

        }catch(Exception eReportEmail){
            eReportEmail.printStackTrace();
            myLog.info("Log: Sending report via email failed");
        }
    }

    public static final String sBootText = "\n  ____       _                 _                    _____       _                 \n" + " |  _ \\     | |               (_)                  |  __ \\     (_)                \n" + " | |_) | ___| |__   __ ___   ___  ___  _   _ _ __  | |  | |_ __ ___   _____ _ __  \n" + " |  _ < / _ \\ '_ \\ / _` \\ \\ / / |/ _ \\| | | | '__| | |  | | '__| \\ \\ / / _ \\ '_ \\ \n" + " | |_) |  __/ | | | (_| |\\ V /| | (_) | |_| | |    | |__| | |  | |\\ V /  __/ | | |\n" + " |____/ \\___|_| |_|\\__,_| \\_/ |_|\\___/ \\__,_|_|    |_____/|_|  |_| \\_/ \\___|_| |_|\n";
    static {myLog.fatal(sBootText);}

}
