package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    // variable find by xpath a button inside the div with id logoutDiv
    @FindBy(xpath = "//div[@id='logoutDiv']//button")
    private WebElement logoutButton;

    // variable find btn-add-note button
    @FindBy(id = "btn-add-note")
    private WebElement btnAddNote;

    // variable for WebDriver
    private WebDriver driver;

    private int port;



    public HomePage(WebDriver driver, int port) {

        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.port = port;
    }

    // method to logout
    public LoginPage logout() {
        logoutButton.click();
        return new LoginPage(driver, port);
    }

    // methid to verify that we are on the home page
    public boolean isOnHomePage() {
        return this.driver.getTitle().equals("Home");
    }

    // method to wait until the logout button is visible
    public void waitForLogoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("logoutDiv")));

    }

}
