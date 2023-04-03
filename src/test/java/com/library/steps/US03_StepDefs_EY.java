package com.library.steps;

import com.library.pages.BasePage_EY;
import com.library.pages.BookPage_EY;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class US03_StepDefs_EY extends BasePage_EY{

    BookPage_EY bookPage = new BookPage_EY();

    @Given("the {string} is on the home page")
    public void the_is_on_the_home_page(String string) {
        emailBox.sendKeys(ConfigurationReader.getProperty("librarian_username"));
        passwordBox.sendKeys(ConfigurationReader.getProperty("librarian_password"));
        signInButton.click();
    }

    @When("the user navigates to {string} page Emily")
    public void the_user_navigates_to_page(String string) {
        bookPage.books.click();

        String expectedHeader = "Book Management";
        String actualHeader = bookPage.pageHeader.getText();

        System.out.println("expectedHeader = " + expectedHeader);
        System.out.println("actualHeader = " + actualHeader);

        Assert.assertEquals(actualHeader, expectedHeader);
    }

    @When("the user clicks book categories EY")
    public void the_user_clicks_book_categories() {
        bookPage.bookCategoriesDropdown.click();
    }

    @Then("verify book categories must match book_categories table from DB")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        DB_Util.runQuery("select name from book_categories");

        List<String> columnDataDB = DB_Util.getColumnDataAsList(1);
        System.out.println("columnDataDB = " + columnDataDB);

        Select bookCategoriesDropdown = new Select(bookPage.bookCategoriesDropdown);
        List<WebElement> columnDataUI_WebElement = bookCategoriesDropdown.getOptions();

        List<String> columnDataUI = new ArrayList<>();

        for (WebElement eachElement : columnDataUI_WebElement) {
            String bookCategory = eachElement.getText();
            columnDataUI.add(bookCategory);
        }

        columnDataUI.remove(0);

        System.out.println("columnDataUI = " + columnDataUI);

        Assert.assertEquals(columnDataDB, columnDataUI);
    }


}
