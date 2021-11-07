Feature: Library have books

  # The first example has two steps
  Scenario Outline: Book added to library
    Given A API is running on "<url>"
    When we we add a book with id as "<id>"
    And category as "<category>"
    And bookanme as "<bookName>"
    And bookUrl as "<bookUrl>"
    And availableBooks as "<availableBooks>"
    And status as "<status>"
    Then the server should handle it and return a created status

    Examples:
      | id | category             | bookName              | bookUrl                  | availableBooks | status    |url|
      | 1  | Science & Technology | Simple Science Tricks | http://simplescience.com | 1              | available |http://loclahost:8080/books