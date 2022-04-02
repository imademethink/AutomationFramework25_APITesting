package pkg_hooks;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;
import pkg_global.GlobalObjects;
import pkg_utility.Utility_API;
import pkg_utility.Utility_General;

public class HooksHelper extends GlobalObjects {

    private static final Logger myLog = Logger.getLogger(HooksHelper.class);

    @Before  // before each scenario
    public void beforeEachScenario(){
        myLog.info("Before every Scenario hook");
        new Utility_General().InitAPIRequestBasic();
        utilAPI =  new Utility_API();
    }

    @After  // after each scenario
    public void afterEachScenarioWithScreenShot(Scenario scenario){
        myLog.info("After the every Scenario hook");
        new Utility_General().ResetAPIRequestBasic();
    }


}
