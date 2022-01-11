package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseUrl;

	private SignupPage signupPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testUserRegistration() {
		String firstname = "Dr.";
		String lastname = "Who";
		String username = "drwho";
		String password = "tardis";

		driver.get(baseUrl + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.createNewUser(firstname, lastname, username, password);
		assertTrue(signupPage.getSuccessMsg().startsWith("You successfully signed up!"));

		driver.get(baseUrl + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.createNewUser(firstname, lastname, username, password);
		assertEquals("Username is not available. Please choose another one.", signupPage.getErrorMsg());
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
