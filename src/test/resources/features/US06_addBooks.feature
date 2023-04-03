@us06
Feature: Books module
  As a librarian, I should be able to add new book into library

  @ui @db
  Scenario Outline: Verify added book is matching with DB
    Given the "librarian" on the home page
    And the user navigates to "Books" page
    When the librarian click to add book RA
    And the librarian enter book name "<Book Name>" RA
    When the librarian enter ISBN "<ISBN>" RA
    And the librarian enter year "<Year>" RA
    When the librarian enter author "<Author>" RA
    And the librarian choose the book category "<Book Category>" RA
    And the librarian click to save changes RA
    Then verify "The book has been created." message is displayed RA
    And verify "<Book Name>" information must match with DB RA
    Examples:
      | Book Name           | ISBN     | Year | Author       | Book Category           |
      | Java Experience B28 | 24092022 | 2022 | Rauf Asgarov | Drama                   |
      | Test Automation B28 | 01012023 | 2023 | Rauf Asgarov | Crime and Detective     |
      | Experiences SDETB28 | 01052023 | 2023 | Rauf Asgarov | Biography/Autobiography |