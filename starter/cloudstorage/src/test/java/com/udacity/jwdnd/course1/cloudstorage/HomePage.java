package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    // variable find by xpath a button inside the div with id logoutDiv
    @FindBy(xpath = "//div[@id='logoutDiv']//button")
    private WebElement logoutButton;

    // variable find btn-add-note button
    @FindBy(id = "btn-add-note")
    private WebElement btnAddNote;

    // variable web element for nav-notes-tab
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    // variable find by id note-title
    @FindBy(id = "note-title")
    private WebElement noteTitle;

    // variable find by id note-description
    @FindBy(id = "note-description")
    private WebElement noteDescription;

    // variable find by id note-title-edit
    @FindBy(id = "note-title-edit")
    private WebElement noteTitleEdit;

    // variable find by id note submit button
    @FindBy(id = "note-submit-primary")
    private WebElement noteSubmit;

    // variable find by id note-description-edit
    @FindBy(id = "note-description-edit")
    private WebElement noteDescriptionEdit;

    // variable for WebDriver
    private WebDriver driver;

    private int port;

    // variable for by add note button
    private By addNoteButtonBy = By.id("btn-add-note");

    // variable for by nav-notes-tab
    private By navNotesTabBy = By.id("nav-notes-tab");

    // variable for by note-title
    private By noteTitleBy = By.id("note-title");

    // variable for by note-description
    private By noteDescriptionBy = By.id("note-description");



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

    // method to add a note
    public void addNote(String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        navNotesTab.click();

        // wait until the add note button is visible
        wait.until(ExpectedConditions.elementToBeClickable(btnAddNote));
        btnAddNote.click();

        // wait until the note title is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteTitleBy));
        noteTitle.sendKeys(title);

        // wait until the note description is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDescriptionBy));
        noteDescription.sendKeys(description);

        // click save changes
        wait.until(ExpectedConditions.elementToBeClickable(noteSubmit));
        noteSubmit.click();
    }

    // method to get note title
    public String findNoteById(Integer noteId) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        navNotesTab.click();

        // wait until the note with id noteId is visible
        WebElement note = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-"+noteId.toString())));
        //print the note
        System.out.println(note.getText());
        // get web element of the note with id note-1
        // get the text of the th tag of the note
        WebElement noteTitle = note.findElements(By.tagName("th")).get(0);
        // return the text of the noteTitle
        return noteTitle.getText();
    }

}
