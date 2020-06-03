import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.util.Set;

import static java.lang.Thread.sleep;

public class WindowsHandlesTest {

    private static final String WINDOWS_MAIN_PAGE = "https://the-internet.herokuapp.com/windows";
    WebDriver driver;
    WebDriverWait wait;
    private String originalWindowHandle;
    private String newWindowHandle;
    private Object[] arrayOfObjects;

    @BeforeSuite
    public void testSuiteSetup(){
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterSuite
    public void tearDown(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    //1. Open the browser
    //2. Go to "windows" page
    //3. Click on "Click Here" link
    //4. Verify the amount of windows is 2
    //5. Verify new window title
    //6. Verify new window text
    //7. Switch back to original window
    //8. Verify original window title
    @Test
    public void test0001() {
        String expectedNewWindowTitle = "New Window";
        String expectedNewWindowText = "New Window";
        String expectedOriginalWindowTitle = "The Internet";
        int expectedAmountOfWindows = 2;
        String linkName = "Click Here";
        int amountOfClicks = expectedAmountOfWindows - 1;

        openWindowsPage();
        clickOnLink(linkName, amountOfClicks);
        verifyAmountOfWindows(expectedAmountOfWindows);
        switchToNewWindow();
        verifyWindowTitle(expectedNewWindowTitle);
        verifyWindowText(expectedNewWindowText);
        switchToOriginalWindow();
        verifyWindowTitle(expectedOriginalWindowTitle);
    }

    //TODO: create a different scenario
    //1. Open the browser
    //2. Go to "windows" page
    //3. Click on "Click Here" link 2 times
    //4. Verify the amount of windows is 3
    //5. FOR each new window: Switch and verify Title and Text
    //6. Switch back to original window
    //7. Verify original window title

    @Test
    public void test0002() {
        String expectedNewWindowTitle = "New Window";
        String expectedNewWindowText = "New Window";
        String expectedOriginalWindowTitle = "The Internet";
        int expectedAmountOfWindows = 3;
        String linkName = "Click Here";
        int amountOfClicks = expectedAmountOfWindows - 1;

        openWindowsPage();
        clickOnLink(linkName, amountOfClicks);
        verifyAmountOfWindows(expectedAmountOfWindows);
        for (int i = 1; i < expectedAmountOfWindows; i++) {
            newWindowHandle = (String) arrayOfObjects[i];
            switchToNewWindow();
            verifyWindowTitle(expectedNewWindowTitle);
            verifyWindowText(expectedNewWindowText);
        }
        switchToOriginalWindow();
        verifyWindowTitle(expectedOriginalWindowTitle);

    }

    private void switchToNewWindow() {
        driver.switchTo().window(newWindowHandle);
        wait.until(ExpectedConditions.titleContains("New Window"));
    }

    private void switchToOriginalWindow() {
        driver.switchTo().window(originalWindowHandle);
    }

    private void verifyWindowText(String expectedText) {
        Assert.assertTrue(driver.getPageSource().contains(expectedText));
    }

    private void verifyWindowTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    private void verifyAmountOfWindows(int expectedAmount) {
        //TODO: change this to explicit wait
        wait.until(ExpectedConditions.numberOfWindowsToBe(expectedAmount));

        Set<String> windowHandles = driver.getWindowHandles();
        int actualAmountOfHandles = windowHandles.size();
        Assert.assertEquals(actualAmountOfHandles, expectedAmount);

        arrayOfObjects = windowHandles.toArray();
        originalWindowHandle = (String) arrayOfObjects[0];
        newWindowHandle = (String) arrayOfObjects[1];
        }


    private void clickOnLink(String linkName, int amountOfClicks) {
        //TODO: change this to class attribute
        for (int i = 0; i < amountOfClicks; i++){
        By expectedElement = By.linkText(linkName);
        WebElement webElement = waitForElement(expectedElement);
        webElement.click();
        }
    }

    private WebElement waitForElement(By expectedElement) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(expectedElement));
    }

    private void openWindowsPage() {
        driver.get(WINDOWS_MAIN_PAGE);
    }
}
