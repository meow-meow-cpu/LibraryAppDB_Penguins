package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US02diliStepDefs {

  String actualBorrowedBookNumbers;
  LoginPage loginPage = new LoginPage();

  DashBoardPage dashBoardPage = new DashBoardPage();

  @When("the librarian gets {string} number")
  public void theLibrarianGetsNumber(String moduleName) {
    BrowserUtil.waitFor(1);
    actualBorrowedBookNumbers=dashBoardPage.getModuleCount(moduleName);
    System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);
  }

  @Then("borrowed books number information must match with DB DY")
  public void borrowed_books_number_information_must_match_with_db() {
    String query = "select count(*) from book_borrow\n" +
            "where is_returned = 0";
    DB_Util.runQuery(query);
    String expectedBorrowedBookNumbers = DB_Util.getFirstRowFirstColumn();
    System.out.println("expectedBorrowedBookNumbers=" + expectedBorrowedBookNumbers);

    Assert.assertEquals(actualBorrowedBookNumbers, expectedBorrowedBookNumbers);
  }
}
