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

  // Search by title tests
  test("search by title finds books with matching substring"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    val lotr = Book("The Lord of the Rings", "J.R.R. Tolkien", "978-0-261-10325-8")
    library.addBook(hobbit)
    library.addBook(lotr)
    val results = library.searchByTitle("Hobbit")
    assertEquals(results.size, 1)
    assertEquals(results.head, hobbit)

  test("search by title is case insensitive"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByTitle("hobbit").size, 1)
    assertEquals(library.searchByTitle("HOBBIT").size, 1)
    assertEquals(library.searchByTitle("HoBbIt").size, 1)

  test("search by title requires minimum 3 non-whitespace characters"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByTitle("Th").size, 0)
    assertEquals(library.searchByTitle("  Th  ").size, 0)
    assertEquals(library.searchByTitle("The").size, 1)
