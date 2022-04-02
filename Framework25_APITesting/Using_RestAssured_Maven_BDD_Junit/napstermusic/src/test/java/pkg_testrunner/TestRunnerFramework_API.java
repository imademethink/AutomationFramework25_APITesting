package pkg_testrunner;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features 	= {"."},
    glue 	 	= {"pkg_stepdefinition","pkg_hooks"},
    plugin 		= {
            "html:target/cucumber",
            "json:target/cucumber/cucumber.json",
            "com.cucumber.listener.ExtentCucumberFormatter:target/html/report.html",
            "rerun:target/rerun.txt"
    },
    tags        = {"@registration"},
//    tags        = {"@ene2end"},
//    tags        = {"@registration_invalid"},
//    tags        = {"@login_invalid"},
//    tags        = {"@update_profile_invalid"},
//    tags        = {"~@none"},
    dryRun 	 	= false,
    strict 	 	= false,
    monochrome  = false
)

public class TestRunnerFramework_API {

    @Before
    public void setupBeforeTest(){
        // empty
    }
    @After
    public void tearDownBeforeTest(){
        // empty
    }

    @BeforeClass
    public static void setupBeforeClass() {
        //empty
    }

    @AfterClass
    public static void teardownAfterClass() {

        // Send report via email

//        // Extra step for JVM shutdown
//        Runtime runtime = Runtime.getRuntime();
//        // Java shutdown hook are handy to run some code when program exit
//        // e.g. clean up resources, send reports etc
//        runtime.addShutdownHook(new Thread(){
//              public void run(){
//                  try {new Utility_General().SendReportViaEmail();}
//                  catch (Exception e) { e.printStackTrace();}
//              }
//          }
//        );
//        try{Thread.sleep(5000);}catch (Exception e){}
    }

}


//	How to send CRUD Request
//	Validating Response Headers
//	Authorization parameters
//  Validations on JSON Response
//  Advanced JSON concepts
//  Validations on XML Response
//  Types Parameters and working with Parameters
//  How to use Request Specification Builder & Response Specification Builder
//  RestAssured Automation Framework
//  Test scenario writing using cucumber
//  Tag controlled scenario filtering
//  Junit based test execution
//  Command line execution
//  Maven for jar file dependency management
//  Reporting - HTML (Extent), Excel, PDF
//  Report emailing
//  Rerun failed tests n times
//  Extent reporting with failed tests logs as well
//  Cucumber hooks implementation so each scenario can focus on actual task
//  Junit fixtures usage
//  Core Java concepts Inheritance, Collection framework
//  Logging using Log4j
