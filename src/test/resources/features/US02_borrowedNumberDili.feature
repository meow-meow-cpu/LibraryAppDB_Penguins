   Feature: As a librarian, I want to know borrowed books number

@wip @db @ui
   Scenario: verify the total amount of borrowed books
   Given the "librarian" on the home page
   When the librarian gets "Borrowed Books" number
   Then borrowed books number information must match with DB DY


