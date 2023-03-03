# theScore QA Automation Challenge (Mobile)

## **Tech Stack**
1. Appium 1.22.3
2. Java 1.8
3. Junit
4. Android Mobile Capabilities
5. IntelliJ IDEA

## Procedure

This is a test script written in Java using JUnit and Appium libraries to automate tests on theScore app, a mobile app that provides sports news, scores, and standings for various leagues and teams.

The script initializes the required capabilities and starts the Appium server locally. Then, it launches theScore app on a Samsung S10 Android device with Android version 12. The test script performs various actions on the app UI, such as clicking on buttons and navigating through different tabs.

The test script verifies the correct display of UI elements by asserting their presence and text values. The test script also checks whether a particular sub-tab is selected or not. Finally, the script selects a team's stats tab to complete the test case.

The test script is parameterized to allow testing on multiple devices with different properties. It uses JUnit's Parameterized class to run the test case on a single device and then iterates over the list of devices to run the same test case on each device.

Note that this script does not cover all possible test scenarios of theScore app, and additional test cases may need to be written to ensure complete test coverage.

#### Current Device Capabilities (Need to update them according your requirements)
1. platformName = Android
2. platformVersion = 12
3. deviceName = Samsung
4. udid = R58MA0GS51V
5. automationName = UiAutomator2

## Note
This test is not set up for iOS device and can easily implement for iOS with above setup.

