package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class US06_StepDefs {

    BookPage bookPage = new BookPage();
    String bookName;
    String authorName;
    @When("the librarian click to add book RA")
    public void the_librarian_click_to_add_book() {
        bookPage.addBook.click();
    }
    @When("the librarian enter book name {string} RA")
    public void the_librarian_enter_book_name(String bookName) {
        bookPage.bookName.sendKeys(bookName);
    }
    @When("the librarian enter ISBN {string} RA")
    public void the_librarian_enter_isbn(String isbnInfo) {
        bookPage.isbn.sendKeys(isbnInfo);
    }
    @When("the librarian enter year {string} RA")
    public void the_librarian_enter_year(String year) {
        bookPage.year.sendKeys(year);
    }
    @When("the librarian enter author {string} RA")
    public void the_librarian_enter_author(String authorName) {
        bookPage.author.sendKeys(authorName);
    }
    @When("the librarian choose the book category {string} RA")
    public void the_librarian_choose_the_book_category(String category) {
//        Select select = new Select(bookPage.categoryDropdown);
//        select.selectByVisibleText(category);
        BrowserUtil.selectOptionDropdown(bookPage.categoryDropdown, category);
    }
    @When("the librarian click to save changes RA")
    public void the_librarian_click_to_save_changes() {
        bookPage.saveChanges.click();
    }
    @Then("verify {string} message is displayed RA")
    public void verify_message_is_displayed(String expectedMessage) {
        System.out.println("expectedMessage = " + expectedMessage);
        System.out.println("bookPage.toastMessage = " + bookPage.toastMessage.getText());
        BrowserUtil.waitFor(1);
        Assert.assertEquals(expectedMessage, bookPage.toastMessage.getText());
    }
    @Then("verify {string} information must match with DB RA")
    public void verify_information_must_match_with_db(String bookName) {

        //Search book in search box
        bookPage.search.sendKeys(bookName);
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();

        String UI_bookName = BrowserUtil.waitForVisibility(bookPage.bookName, 2).getAttribute("value");

        String UI_authorName = bookPage.author.getAttribute("value");

        List<String> bookInfoFromUI = new ArrayList<>(Arrays.asList(UI_bookName,UI_authorName));
        System.out.println("bookInfoFromUI = " + bookInfoFromUI);

        bookName = UI_bookName;
        authorName = UI_authorName;

        String query = "select name,author from books\n" +
                "where name = '"+bookName+"' and author='"+authorName+"'";

        DB_Util.runQuery(query);

        List<String> bookInfoFromDB = DB_Util.getRowDataAsList(1);
        System.out.println("bookInfoFromDB = " + bookInfoFromDB);

        Assert.assertEquals(bookInfoFromUI, bookInfoFromDB);


    }




}
