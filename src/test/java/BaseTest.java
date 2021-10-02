import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import main.Config;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected Browser browser;

    @BeforeMethod
    public void setUp(){
        browser = AqualityServices.getBrowser();
        browser.maximize();
        browser.goTo(Config.get("test_url"));
        browser.waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }
}
