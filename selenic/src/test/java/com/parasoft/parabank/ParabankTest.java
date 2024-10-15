/**
 * 
 */
package com.parasoft.parabank;

import com.parasoft.parabank.page.ActivityPage;
import com.parasoft.parabank.page.ParaBankAccountActivityPage;
import com.parasoft.parabank.page.ParaBankAccountsOverviewPage;
import com.parasoft.parabank.page.ParaBankWelcomeOnlineBankingPage;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.net.MalformedURLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ParabankTest {

	/**
	 * Parasoft auto generated base URL
	 * Use -DPARABANK_BASE_URL=http://localhost:8080 from command line
	 * or use System.setProperty("PARABANK_BASE_URL", "http://localhost:8080") to change base URL at run time.
	 */
	private static final String PARABANK_BASE_URL = "http://parabank-baseline:8080";
	private static final String CHROME_DRIVER = "/opt/selenium/chromedriver-128.0.6613.86";
	private static final String GRID_URL = "http://selenium-chrome:4444/wd/hub";
	
	private WebDriver driver;

	@BeforeEach
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
		ChromeOptions opts = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.managed_default_content_settings.geolocation", 2);
		prefs.put("profile.default_content_setting_values.notifications", 2);
		opts.setExperimentalOption("prefs", prefs);
		opts.addArguments("--remote-allow-origins=*");
		opts.addArguments("--start-maximized");
		opts.addArguments("--incognito");
		opts.addArguments("--enable-strict-powerful-feature-restrictions");
		opts.addArguments("--ignore-ssl-errors=yes");
		opts.addArguments("--ignore-certificate-errors");
        //      opts.setBrowserVersion("118.0.5993.70");
		opts.addArguments("--headless"); // Run in headless mode
                opts.addArguments("--disable-gpu"); // Disable GPU for headless
                opts.addArguments("--no-sandbox");
		try {
			driver = new RemoteWebDriver(new URL(GRID_URL), opts);
	        } catch (MalformedURLException e) {
			e.printStackTrace();
	        }
	}

	@AfterEach
	public void afterTest() {
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * Name: localhost-2023-09-01-13-05-15
	 * Recording file: parabank_new.json
	 *
	 * Parasoft recorded Selenium test on Fri Sep 01 2023 13:05:15 GMT+0200 (czas środkowoeuropejski letni)
	 */
	@Test
	public void test1() throws Throwable {
		driver.get(System.getProperty("PARABANK_BASE_URL", PARABANK_BASE_URL) + "/parabank/index.htm");

		ParaBankWelcomeOnlineBankingPage paraBankWelcomeOnlineBankingPage = new ParaBankWelcomeOnlineBankingPage(
				driver);
		paraBankWelcomeOnlineBankingPage.setUsernameField("john");
		paraBankWelcomeOnlineBankingPage.setPasswordField("demo");
		paraBankWelcomeOnlineBankingPage.clickLogInButton();

		ParaBankAccountsOverviewPage paraBankAccountsOverviewPage = new ParaBankAccountsOverviewPage(driver);
		paraBankAccountsOverviewPage.clickLink();

		ParaBankAccountActivityPage paraBankAccountActivityPage = new ParaBankAccountActivityPage(driver);
		paraBankAccountActivityPage.clickAccountsOverviewLink();
		paraBankAccountsOverviewPage.clickLink2();

		ActivityPage activityPage = new ActivityPage(driver);
		activityPage.clickAccountsOverviewLink();
		paraBankAccountsOverviewPage.clickLogOutLink();
	}

}
