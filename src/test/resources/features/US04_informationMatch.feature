
Feature: As a data consumer, I want UI and DB book information are match.
   @ui @db
  Scenario: Verify book information with DB
    Given the "librarian" on the home page FB
    And the user navigates to "Books" page FB
    When the user searches for "Clean Code" book FB
    And  the user clicks edit book button FB
    Then book information must match the Database FB
