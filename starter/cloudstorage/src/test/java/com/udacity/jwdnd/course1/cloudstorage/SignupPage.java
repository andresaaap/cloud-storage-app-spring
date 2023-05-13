package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    // variable of web element to find inputUsername by id
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    // variable web element inputLastName
    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    // variable web element inputFirstName
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    // variable web element inputPassword
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    // variable web element buttonSignUp
    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // method to signup
    public void signup(String username, String password, String firstName, String lastName) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        buttonSignUp.click();
    }


}
