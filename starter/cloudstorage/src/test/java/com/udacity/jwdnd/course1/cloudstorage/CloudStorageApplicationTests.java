package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

// import class File with the name FileStorage
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UserService userService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private FileService fileService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}

		// get all users, loop through them and delete them
		for (User user : userService.getAllUsers()) {
			// get all notes for user, loop through them and delete them
			for (Note note : noteService.getNotesByUserId(user.getUserid())) {
				noteService.deleteNoteById(note.getNoteid());
			}
			// get all credentials for user, loop through them and delete them
			for (Credential credential : credentialService.getCredentialsByUserId(user.getUserid())) {
				credentialService.deleteCredential(credential.getCredentialId());
			}
			// get all files for user, loop through them and delete them
			for (File file : fileService.getFilesByUserId(user.getUserid())) {
				fileService.deleteFile(file.getFileId());
			}
			userService.deleteUser(user.getUserid());
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new java.io.File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testHomePageNotAccessibleWithoutLoggingIn(){
		LoginPage loginPage = new LoginPage(driver, port);
		HomePage homePage = loginPage.goToHomePage();
		Assertions.assertFalse(homePage.isOnHomePage());
	}

	@Test
	public void testSignupLoginFlow(){
		// signup user
		// login user
		// check if user is on home page
		// logout user
		// check if user can't access home page

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user1", "pass1", "firstname1", "lastname1");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user1", "pass1");
		homePage.waitForLogoutButton();
		Assertions.assertTrue(homePage.isOnHomePage());

		loginPage = homePage.logout();
		homePage = loginPage.goToHomePage();
		Assertions.assertFalse(homePage.isOnHomePage());
	}

	// test create a note
	@Test
	public void testCreateNote(){
		// signup user
		// login user
		// create note
		// check if note is displayed

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");
		String noteTitle = "grocery list";
		String noteDescription = "milk, eggs, bread";

		homePage.addNote(noteTitle, noteDescription);
		String realNoteText = homePage.findNoteByOrder(0);

		// combine the noteTitle and noteDescription into one string
		String noteText = noteTitle + " " + noteDescription;

		// assert if notetitle is grocery list
		Assertions.assertEquals(noteText, realNoteText);
	}

	@Test
	public void testEditNote(){
		// signup user
		// login user
		// create note
		// check if note is displayed

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");
		String noteTitle = "grocery list";
		String noteDescription = "milk, eggs, bread";
		homePage.addNote(noteTitle, noteDescription);

		String noteTitle2 = "grocery list fruits";
		String noteDescription2 = "bananas, apples, oranges";
		homePage.editNoteByOrder(0, noteTitle2, noteDescription2);
		String realNoteText = homePage.findNoteByOrder(0);

		// combine the noteTitle and noteDescription into one string
		String noteText2 = noteTitle2 + " " + noteDescription2;
		// assert if notetitle is grocery list
		Assertions.assertEquals(noteText2, realNoteText);
	}

	@Test
	public void testDeleteNote(){
		// signup user
		// login user
		// create note
		// check if note is displayed

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");
		String noteTitle = "grocery list";
		String noteDescription = "milk, eggs, bread";
		homePage.addNote(noteTitle, noteDescription);

		// delete note
		homePage.deleteNoteByOrder(0);
		// verify that the note is deleted
		Assertions.assertFalse(homePage.isNotePresentByTitle(noteTitle));
	}

	@Test
	public void testCreateCredential(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");

		// create credential
		String url = "www.google.com";
		String username = "user";
		String password = "pass";
		homePage.addCredential(url, username, password);

		// get the credential text
		String realCredentialText = homePage.findCredentialByOrder(0);

		// get all the credentials using the credential service
		List<Credential> credentials = credentialService.getCredentials();

		// get encrypted password
		String encryptedPassword = credentials.get(0).getPassword();

		// combine the url, username, and password into one string
		String credentialText = url + " " + username + " " + encryptedPassword;

		// assert if credential text is the same
		Assertions.assertEquals(credentialText, realCredentialText);
	}

	@Test
	public void testDeleteCredential(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");

		// create credential
		String url = "www.google.com";
		String username = "user";
		String password = "pass";
		homePage.addCredential(url, username, password);

		// combine the url, username, and password into one string
		String credentialText = url + " " + username + " " + password;

		// delete credential
		homePage.deleteCredentialByOrder(0);
		// verify that the credential is deleted
		Assertions.assertFalse(homePage.isCredentialPresentByContents(credentialText));
	}

	@Test
	public void testUpdateCredential(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");

		// create credential
		String url = "www.google.com";
		String username = "user";
		String password = "messi";
		homePage.addCredential(url, username, password);

		// update credential
		String url2 = "www.facebook.com";
		String username2 = "user2";
		String password2 = "ronaldo";

		homePage.editCredentialByOrder(0, url2, username2, password2);

		// get the credential text
		String realCredentialText = homePage.findCredentialByOrder(0);

		// get all the credentials using the credential service
		List<Credential> credentials = credentialService.getCredentials();

		// get encrypted password
		String encryptedPassword2 = credentials.get(0).getPassword();

		// combine the url, username, and password into one string
		String credentialText = url2 + " " + username2 + " " + encryptedPassword2;
		// assert if credential text is the same
		Assertions.assertEquals(credentialText, realCredentialText);

		// check the decrypted password
		String decryptedPassword = homePage.checkDecryptedPasswordByOrder(0);

		// assert if decrypted password is the same
		Assertions.assertEquals(password2, decryptedPassword);

	}

	@Test
	public void testDeleteUser(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver, port);
		signupPage = signupPage.signup("user2", "pass2", "firstname2", "lastname2");
		LoginPage loginPage = signupPage.goToLoginPage();
		HomePage homePage = loginPage.login("user2", "pass2");

		// logout user
		loginPage = homePage.logout();

		// delete user
		userService.deleteUser(1);
		// get user by id
		User user = userService.getUserById(1);

		// assert if user is null
		Assertions.assertNull(user);
	}


}
