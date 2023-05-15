package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    // variable web element inputUsername
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    // variable web element inputPassword
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    // variable web element login-button
    @FindBy(id = "login-button")
    private WebElement loginButton;


    // variable for by login button
    private By loginButtonBy = By.id("login-button");

    // variable for WebDriver
    private WebDriver driver;

    private int port;

    //constructor
    public LoginPage(WebDriver driver, int port) {

        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.port = port;
    }

    // method to login
    public HomePage login(String username, String password) {
        // wait until the login button is visible
        WebDriverWait wait = new WebDriverWait(driver, 5);
        loginButton = wait.until(webDriver -> webDriver.findElement(loginButtonBy));
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        loginButton.click();

        return new HomePage(driver, port);
    }

    // method go to home page without login return the user to login page
    public HomePage goToHomePage() {
        driver.get("http://localhost:" + port + "/home");
        return new HomePage(driver, port);
    }

}
