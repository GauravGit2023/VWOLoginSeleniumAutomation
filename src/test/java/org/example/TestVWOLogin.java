package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestVWOLogin {
    //ChromeOptions options;
    WebDriver driver;

    @BeforeSuite
    public void setup(){
//        options = new ChromeOptions();
//        options.addArguments("--start-maximised");
//        driver = new ChromeDriver(options);
        //System.setProperty("webdriver.edge.driver", "C:\\Users\\gaura\\IdeaProjects\\VWOLoginAutomationSelenium\\Edgedriver\\msedgedriver.exe");
        driver = new EdgeDriver();
        // Implicit wait
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    // id, name, classNAame remain same for all browsers
    // xpath, css selector - chrome ones will not work for firefox

    // Write negative testcase before positive test case so that we do not have to logout
    @Test(priority = 1,groups = {"negative","sanity"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("TC#1 - Verify that with invalid username and valid password, Login is not successful")
    public void testInvalidLogin() throws InterruptedException {
        driver.get("https://app.vwo.com");

        //<input type="email" class="text-input W(100%)" name="username" id="login-username" data-qa="hocewoqisi">
        driver.findElement(By.id("login-username")).sendKeys("93npu2yyb0@esiix.co");

        //<input type="password" class="text-input W(100%)" name="password" id="login-password" data-qa="jobodapuxe">
        driver.findElement(By.name("password")).sendKeys("Wingify@123");

        //<button type="submit" id="js-login-btn" class="btn btn--positive " +
        //  "btn--inverted W(100%) H(48px) Fz(16px)" onclick="login.login(event)" data-qa="sibequkica">
        driver.findElement(By.id("js-login-btn")).click();

        //wait
        //Thread.sleep(2000);
        WebElement errorMessage = driver.findElement(By.className("notification-box-description"));
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(errorMessage));


        // Assertion - expected result = actual result
        Assert.assertEquals(errorMessage.getText(),"Your email, password, IP address or location did not match");
    }

    @Test(priority = 2,groups = {"positive","sanity","stage"})
    @Description("TC#2 - Verify that with Valid username and valid password, Login is successful")
    public void testValidLogin() throws InterruptedException {
        driver.get("https://app.vwo.com");

        // previous ones password not working
        // 93npu2yyb0@esiix.com
        // Wingify@123
        //<input type="email" class="text-input W(100%)" name="username" id="login-username" data-qa="hocewoqisi">
        driver.findElement(By.id("login-username")).sendKeys("vnzbmvd894@moderngate.pw");


        //<input type="password" class="text-input W(100%)" name="password" id="login-password" data-qa="jobodapuxe">
        driver.findElement(By.name("password")).sendKeys("testEmail@123");

        //<button type="submit" id="js-login-btn" class="btn btn--positive " +
        //  "btn--inverted W(100%) H(48px) Fz(16px)" onclick="login.login(event)" data-qa="sibequkica">
        driver.findElement(By.id("js-login-btn")).click();

        //wait
        //Thread.sleep(4000);
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.page-heading")));

        Assert.assertEquals(driver.getTitle(),"Dashboard");
        Assert.assertEquals(driver.getCurrentUrl(),"https://app.vwo.com/#/dashboard");

        driver.get("https://app.vwo.com/#/logout");
    }

    @AfterSuite
    public void tearDown(){
        driver.quit();
    }
}

// get status
// git add .
// SKIP=gitleaks git commit -m "11th August, Fixed report"

// At last remove below code from execute shell to replace install in invoke top level maven
// export JAVA_HOME=$(C:\Program Files\Java\jdk-11)
//C:\Program Files\Maven\apache-maven-3.9.4\bin\mvn clean test -DsuiteXmlFile=testng.xml