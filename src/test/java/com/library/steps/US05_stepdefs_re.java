package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US05_stepdefs_re {

    String expectedBookName;
    @When("I execute query to find most popular book genre RE")
    public void iExecuteQueryToFindMostPopularBookGenre() {

        String query = "select bc.name, count(*) from book_borrow bb inner join books b on b.id = bb.book_id\n" +
                "inner join book_categories bc on b.book_category_id = bc.id\n" +
                "group by bc.name\n" +
                "order by count(*) desc";

        DB_Util.runQuery(query);

        String expectedBookName= DB_Util.getFirstRowFirstColumn();

        System.out.println("expectedBookName = " + expectedBookName);

        this.expectedBookName = expectedBookName;

    }

    @Then("verify {string} is the most popular book genre. RE")
    public void verifyIsTheMostPopularBookGenre(String actualBookName) {

        System.out.println("actualBookName = " + actualBookName);

        Assert.assertEquals(expectedBookName, actualBookName);

        }



}
