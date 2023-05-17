package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    // variable find by id note edit submit button
    @FindBy(id = "note-edit-submit-primary")
    private WebElement noteEditSubmit;

    // variable find by id note-description-edit
    @FindBy(id = "note-description-edit")
    private WebElement noteDescriptionEdit;

    // navCredentialsTab
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    // btnAddCredential
    @FindBy(id = "btn-add-credential")
    private WebElement btnAddCredential;

    // credentialUrl
    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    // credentialUsername
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    // credentialPassword
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    // credentialSubmit
    @FindBy(id = "credential-submit-primary")
    private WebElement credentialSubmit;

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

    // variable for by note-title-edit
    private By noteTitleEditBy = By.id("note-title-edit");

    // variable for by note-description-edit
    private By noteDescriptionEditBy = By.id("note-description-edit");

    // variable for by credential-url
    private By credentialUrlBy = By.id("credential-url");

    // variable for by credential-username
    private By credentialUsernameBy = By.id("credential-username");

    // variable for by credential-password
    private By credentialPasswordBy = By.id("credential-password");



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

        // get web element of the note with id note-1
        // get the text of the th tag of the note
        WebElement noteTitle = note.findElements(By.tagName("th")).get(0);
        WebElement noteDescription = note.findElements(By.tagName("td")).get(1);
        // return the combined text of the note
        return noteTitle.getText() + " " + noteDescription.getText();
    }

    // method editNoteById
    public void editNoteById(Integer noteId, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        navNotesTab.click();

        // wait until the note with id noteId is visible
        WebElement note = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-"+noteId.toString())));

        // wait until the button of note is clickable
        WebElement editNoteButton = wait.until(ExpectedConditions.elementToBeClickable(note.findElement(By.tagName("button"))));
        editNoteButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(noteTitleEditBy));
        noteTitleEdit.clear();
        noteTitleEdit.sendKeys(title);
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDescriptionEditBy));
        noteDescriptionEdit.clear();
        noteDescriptionEdit.sendKeys(description);

        wait.until(ExpectedConditions.elementToBeClickable(noteEditSubmit));
        noteEditSubmit.click();
    }

    public void deleteNoteById(Integer noteId) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        navNotesTab.click();

        // wait until the note with id noteId is visible
        WebElement note = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-"+noteId.toString())));

        // wait until the button of note is clickable
        WebElement deleteNoteButton = wait.until(ExpectedConditions.elementToBeClickable(note.findElement(By.tagName("a"))));
        deleteNoteButton.click();
    }

    public boolean isNotePresentById(Integer noteId) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        navNotesTab.click();

        // wait until the note table is visible
        WebElement noteTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));

        // if there is a note with id noteId, return true
        // else return false
        try {
            noteTable.findElement(By.id("note-"+noteId.toString()));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void addCredential(String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        navCredentialsTab.click();

        // wait until the add note button is visible
        wait.until(ExpectedConditions.elementToBeClickable(btnAddCredential));
        btnAddCredential.click();

        // wait until the note title is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(credentialUrlBy));
        credentialUrl.sendKeys(url);

        // wait until the note description is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(credentialUsernameBy));
        credentialUsername.sendKeys(username);

        // wait until the note description is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(credentialPasswordBy));
        credentialPassword.sendKeys(password);

        // click save changes
        wait.until(ExpectedConditions.elementToBeClickable(credentialSubmit));
        credentialSubmit.click();
    }

    public String findCredentialById(Integer credentialId) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        navCredentialsTab.click();

        // wait until the note with id noteId is visible
        WebElement credential = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-"+credentialId.toString())));

        // get web element of the note with id note-1
        // get the text of the th tag of the note
        WebElement credentialUrl = credential.findElements(By.tagName("th")).get(0);
        WebElement credentialUsername = credential.findElements(By.tagName("td")).get(1);
        WebElement credentialPassword = credential.findElements(By.tagName("td")).get(2);
        // return the combined text of the note
        return credentialUrl.getText() + " " + credentialUsername.getText() + " " + credentialPassword.getText();
    }

    public void deleteCredentialById(Integer credentialId) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        navCredentialsTab.click();

        // wait until the note with id noteId is visible
        WebElement credential = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-"+credentialId.toString())));

        // wait until the button of note is clickable
        WebElement deleteCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(credential.findElement(By.tagName("a"))));
        deleteCredentialButton.click();
    }

    public boolean isCredentialPresentById(Integer credentialId) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // wait until the nav-notes-tab is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        navCredentialsTab.click();

        // wait until the note table is visible
        WebElement credentialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

        // if there is a note with id noteId, return true
        // else return false
        try {
            credentialTable.findElement(By.id("credential-"+credentialId.toString()));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
