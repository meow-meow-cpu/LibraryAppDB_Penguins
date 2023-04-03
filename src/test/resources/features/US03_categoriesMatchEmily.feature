Feature: As a data consumer, I want UI and DB book categories to match.

  @emily @ui @db
  Scenario: verify book categories with DB
    Given the "librarian" is on the home page
    When the user navigates to "Books" page Emily
    And the user clicks book categories EY
    Then verify book categories must match book_categories table from DB