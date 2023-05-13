package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    // variable find by Logout button inside the div with id logoutDiv
    @FindBy(xpath = "//div[@id='logoutDiv']/button")
    private WebElement logoutButton;

    // variable find btn-add-note button
    @FindBy(id = "btn-add-note")
    private WebElement btnAddNote;



    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // method to logout
    public void logout() {
        logoutButton.click();
    }

}
