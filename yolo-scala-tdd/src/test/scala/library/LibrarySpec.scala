package library

import munit.FunSuite

class LibrarySpec extends FunSuite:

  test("a book has a title, author, and ISBN"):
    val book = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    assertEquals(book.title, "The Hobbit")
    assertEquals(book.author, "J.R.R. Tolkien")
    assertEquals(book.isbn, "978-0-261-10295-4")

  test("a library can contain books"):
    val library = Library()
    val book = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(book)
    assertEquals(library.books.size, 1)
    assertEquals(library.books.head, book)
