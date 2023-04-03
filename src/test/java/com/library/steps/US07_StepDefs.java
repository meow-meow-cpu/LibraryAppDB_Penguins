package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.BorrowedBooksPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class US07_StepDefs extends BookPage {
    String bookName1;
    LoginPage loginPage = new LoginPage();

    BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();


    @And("the user searches for {string} book AO")
    public void theUserSearchesForBook(String bookName1) {
        search.sendKeys(bookName1);
        this.bookName1=bookName1;
    }

    @When("the user clicks Borrow Book AO")
    public void theUserClicksBorrowBook() {
        BrowserUtil.waitFor(2);
        borrowBook().click();
    }

    @Then("verify that book is shown in {string} page AO")
    public void verifyThatBookIsShownInPage(String moduleName) {
        navigateModule(moduleName);
        boolean bookBorrowed = false;

        for (WebElement eachBook : borrowedBooksPage.allBorrowedBooksName) {
            if(eachBook.getText().equals(bookName1))
                bookBorrowed=true;
        }
        Assert.assertTrue(bookBorrowed);

    }

    @And("verify logged student has same book in database AO")
    public void verifyLoggedStudentHasSameBookInDatabase() {

        DB_Util.runQuery("select name,is_returned,email from book_borrow join books b on \n" +
                "    b.id = book_borrow.book_id join users u on u.id = book_borrow.user_id\n" +
                "where name='" + bookName1 + "'\n" +
                "and is_returned='0'" + // mandatory to show that book is not returned
                "and email='"+ConfigurationReader.getProperty("student_username") +"'");
        String email = DB_Util.getCellValue(1, "email");
        //System.out.println(email);
        Assert.assertEquals(email, ConfigurationReader.getProperty("student_username"));
    }

    @Then("user returns borrowed book AO")
    public void userReturnsBorrowedBook() {

        WebElement returnBookButton = Driver.getDriver().findElement(By.xpath("//tbody//td[6][contains(text(), 'NOT')]/../td[2][contains(text(), '" + bookName1 + "')]/../td/a"));
        returnBookButton.click();


    }

    @And("the user searches book name called {string} AO")
    public void theUserSearchesBookNameCalled(String bookName1) {
        search.sendKeys(bookName1);
        this.bookName1 = bookName1;
    }
}
