Feature: Books module
  As a students, I should be able to borrow book
@ui @db
  Scenario: Student borrow new book
    Given the "student" on the home page
    And the user navigates to "Books" page
    And the user searches for "Self Confidence" book AO
    When the user clicks Borrow Book AO
    Then verify that book is shown in "Borrowing Books" page AO
    And  verify logged student has same book in database AO
    Then user returns borrowed book AO