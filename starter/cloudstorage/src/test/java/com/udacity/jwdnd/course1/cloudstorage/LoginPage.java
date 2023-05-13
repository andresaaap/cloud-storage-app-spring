package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    //constructor
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // method to login
    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        loginButton.click();
    }

}
