package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class US04_StepDefs {

    BookPage bookPage;
    String bookName;
    LoginPage loginPage = new LoginPage();

    @Given("the {string} on the home page FB")
    public void the_on_the_home_page_fb(String user) {
        loginPage.login(user);
        BrowserUtil.waitFor(4);
    }

    @And("the user navigates to {string} page FB")
    public void theUserNavigatesToPage(String moduleName) {
        bookPage = new BookPage();
        bookPage.navigateModule(moduleName);
        BrowserUtil.waitFor(1);
    }

    @When("the user searches for {string} book FB")
    public void theUserSearchesForBook(String name) {
        bookName = name;
        bookPage = new BookPage();
        bookPage.search.sendKeys(bookName);
    }

    @And("the user clicks edit book button FB")
    public void theUserClicksEditBookButton() {
        bookPage = new BookPage();
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName),5).click();
        BrowserUtil.waitFor(3);

    }

    @Then("book information must match the Database FB")
    public void book_information_must_match_the_database_fb() {
        bookPage = new BookPage();
        String UI_book_Name = bookPage.bookName.getAttribute("value");
        String UI_author_Name = bookPage.author.getAttribute("value");
        Select select = new Select(bookPage.categoryDropdown);
        String UI_book_Category = select.getFirstSelectedOption().getText();
        List<String> bookInfoFromUI = new ArrayList<>(Arrays.asList(UI_book_Name,UI_author_Name,UI_book_Category));

        String query = "select b.name as bookName,author, bc.name as bookCategoryName from books b inner join\n" +
                "    book_categories bc on b.book_category_id = bc.id\n" +
                "where b.name = '"+bookName+"'";

        DB_Util.runQuery(query); //executes query and stores data into result-set object


        List<String> bookInfoList = DB_Util.getRowDataAsList(1);
        System.out.println("bookInfoList = " + bookInfoList);

        String DB_book_Name = bookInfoList.get(0);
        String DB_author_Name = bookInfoList.get(1);
        String DB_category_Name = bookInfoList.get(2);

        Assert.assertEquals(DB_book_Name,UI_book_Name);
        Assert.assertEquals(bookInfoList,bookInfoFromUI);

    }
}
