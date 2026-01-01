package library

class BookTest extends munit.FunSuite:
  test("Book has title, author, and ISBN"):
    val book = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")

    assertEquals(book.title, "The Pragmatic Programmer")
    assertEquals(book.author, "David Thomas")
    assertEquals(book.isbn, "978-0135957059")
