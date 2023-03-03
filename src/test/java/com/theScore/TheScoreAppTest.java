package com.theScore;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class TheScoreAppTest {
    public static AndroidDriver<MobileElement> androidDriver;
    public String platformName;
    public String platformVersion;
    public String deviceName;
    public String udid;
    public String automationName;

    @Parameters(name = "{0}App")
    public static Collection<Object[]> deviceData() {
        Object[][] deviceData = new Object[][]{
                { //Android Mobile Device Properties
                    "Android",
                    "12",
                    "Samsung S10",
                    "R58MA0GS51B",
                    "UiAutomator2"}
        };
        return Arrays.asList(deviceData);
    }

    public TheScoreAppTest(String platformName, String platformVersion, String deviceName, String udid, String automationName) {
        super();
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.deviceName = deviceName;
        this.udid = udid;
        this.automationName = automationName;
    }

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        caps.setCapability(MobileCapabilityType.UDID, udid);
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        caps.setCapability(MobileCapabilityType.APP,"src/test/resources/theScore.apk");
        caps.setCapability("appPackage", "com.fivemobile.thescore");
        caps.setCapability("appActivity", "com.fivemobile.thescore.ui.MainActivity");

        try {
            System.out.println("Appium Server launched!");
            AppiumDriverLocalService.buildDefaultService().start();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Appium server local server launching failed!!!");
        }
        if(androidDriver == null) {
            androidDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        }else {
            System.out.println("Driver initialization aborted!!");
        }

    }

    @Test
    public void testTheScoreApp() throws InterruptedException {
        MobileElement subTab;
        MobileElement team;
        MobileElement statsTab;
        if (androidDriver != null) {
            androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            System.out.println("theScore app successfully Opened");
            //click on get started button
            androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/action_button_text")).click();

            //selecting a favorite league @NHL Hockey
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='NHL Hockey']")).click();
            Assert.assertTrue(androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='NHL']")).isDisplayed());
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='Continue']")).click();
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='Maybe Later']")).click();
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='Continue']")).click();
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='Done']")).click();
            androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/dismiss_modal")).click();

            //Select Leagues bottom navigation tab
            String bottomTab = "Leagues";
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='"+bottomTab+"']")).click();
            // close the alert
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='Edit']")).click();
            //Verify User is on Leagues Tab
            Assert.assertTrue(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/titleTextView")).isDisplayed());



            String LeagueTeam = "NHL"; //Select a League team
            androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='"+ LeagueTeam +"']")).click();
            Assert.assertTrue(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/titleTextView")).isDisplayed());
            Assert.assertEquals(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/titleTextView")).getText(),LeagueTeam);


            String tab ="STANDINGS"; //Select a Sub-tab
            subTab = androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='"+tab+"']"));
            subTab.click();
            Assert.assertTrue(subTab.isSelected());


            String teamName = "BOS Bruins";
            String teamFullName = "Boston Bruins";//Select a Team Name
            team = androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='"+teamName+"']"));
            team.click();
            Assert.assertTrue(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/team_name")).isDisplayed());
            Assert.assertTrue(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/team_name")).getText().contains(teamFullName));


            String stats ="TEAM STATS";  //Select a Team Stats
            statsTab = androidDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text='"+stats+"']"));
            statsTab.click();
            Assert.assertTrue(statsTab.isSelected());

            //Navigate to back to Team Tab
            Thread.sleep(500);
            androidDriver.findElement(MobileBy.AccessibilityId("Navigate up")).click();
            Assert.assertEquals(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/titleTextView")).getText(),LeagueTeam);

            //Navigate to back
            androidDriver.findElement(MobileBy.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
            Assert.assertEquals(androidDriver.findElement(MobileBy.id("com.fivemobile.thescore:id/titleTextView")).getText(), bottomTab);

        }
    }

    @After
    public void tearDown() {
        System.out.println("app test is completed");
        if (androidDriver != null) {
            androidDriver.quit();
        }
    }
}
