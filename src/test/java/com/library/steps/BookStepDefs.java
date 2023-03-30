package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BookStepDefs {
    BookPage bookPage;

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        bookPage = new BookPage();
        bookPage.navigateModule(moduleName);
        BrowserUtil.waitFor(1);
    }
    List<String> actualCategoryList ;
    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
        actualCategoryList = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        System.out.println("actualCategoryList = " + actualCategoryList);
        actualCategoryList.remove(0);
        System.out.println("------- AFTER REMOVE FIRST ONE --------");
        System.out.println("actualCategoryList = " + actualCategoryList);

    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {

        DB_Util.runQuery("select name from book_categories");

        List<String> expectedCategoryList = DB_Util.getColumnDataAsList(1);

        System.out.println("expectedCategoryList = " + expectedCategoryList);

        Assert.assertEquals(expectedCategoryList,actualCategoryList);

    }
    String bookName;
    @When("the user searches for {string} book")
    public void theUserSearchesForBook(String name) {
        bookName = name;
        bookPage = new BookPage();
        bookPage.search.sendKeys(bookName);

    }

    @And("the user clicks edit book button")
    public void theUserClicksEditBookButton() {
        bookPage = new BookPage();
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();
        BrowserUtil.waitFor(3);
    }

    @Then("book information must match the Database")
    public void bookInformationMustMatchTheDatabase() {
         // bookName: global variable that can bu used in the query
        bookPage = new BookPage();
        String UI_book_Name = bookPage.bookName.getAttribute("value");
        String UI_author_Name = bookPage.author.getAttribute("value");
        Select select = new Select(bookPage.categoryDropdown);
        String UI_book_Category = select.getFirstSelectedOption().getText();
        List<String> bookInfoFromUI = new ArrayList<>(Arrays.asList(UI_book_Name,UI_author_Name,UI_book_Category));
        // Database Info
        String query = "select b.name as bookName, author, bc.name as bookCategoryName from books b inner join\n" +
                "    book_categories bc on b.book_category_id = bc.id\n" +
                "where b.name = '"+bookName+"'";

        DB_Util.runQuery(query);  // executes query and stores data into result-set object

        // Map<String,String> bookInfoMap = DB_Util.getRowMap(1);

        // System.out.println("bookInfoMap = " + bookInfoMap);
        
        List<String> bookInfoListFromDB = DB_Util.getRowDataAsList(1);
        System.out.println("bookInfoListFromDB = " + bookInfoListFromDB);
        
        String DB_book_Name = bookInfoListFromDB.get(0);
        String DB_author_Name = bookInfoListFromDB.get(1);
        String DB_category_Name = bookInfoListFromDB.get(2);
        
        Assert.assertEquals(DB_book_Name,UI_book_Name); // verify one by one
        Assert.assertEquals(bookInfoListFromDB,bookInfoFromUI); // verify as a List

    }
}
