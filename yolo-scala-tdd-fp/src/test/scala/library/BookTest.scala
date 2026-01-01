package library

import munit.FunSuite

class BookTest extends FunSuite:

  test("Book has title, author, and ISBN"):
    val book = Book(
      title = "The Pragmatic Programmer",
      author = "David Thomas",
      isbn = "978-0135957059"
    )

    assertEquals(book.title, "The Pragmatic Programmer")
    assertEquals(book.author, "David Thomas")
    assertEquals(book.isbn, "978-0135957059")
