import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class DropDownListTest {
    private static final String WINDOWS_MAIN_PAGE = "http://the-internet.herokuapp.com/dropdown";
    WebDriver driver;

    @BeforeSuite
    public void testSuiteSetup(){
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

//    @AfterSuite
//    public void tearDown(){
//        driver.manage().deleteAllCookies();
//        driver.quit();
//    }

    //1. Open the browser
    //2. Go to "windows" page
    //3. Find DropDown box
    //4. Click on Dropdown Box
    //5. Select One of dropdown box Values
    //6. Assert Box Value with predefined


    @Test
    public void test0001() {
        String dDListName = "dropdown";
        String expectedDDListValue = "Option 1"; //Option 1


        openDDListPage();
//        findDDListElement(dDListName);
        clickOnDDList(dDListName);
        selectDDListValue(expectedDDListValue);
        assertElements(expectedDDListValue);



    }

    private void assertElements(String expectedDDListValue) {
        Assert.assertEquals(expectedDDListValue, "Option 1");
    }

    private void selectDDListValue(String expectedDDListValue) {
        WebElement currentDDListValue = driver.findElement(By.name(expectedDDListValue));
        currentDDListValue.click();
    }

    private void clickOnDDList(String dDListName) {
        WebElement dDListElement = driver.findElement(By.id(dDListName));
        dDListElement.click();
    }

//    private void findDDListElement(String dDListName) {
//        WebElement dDListElement = driver.findElement(By.id(dDListName));
//    }

    private void openDDListPage() {
        driver.get(WINDOWS_MAIN_PAGE);

    }
}
