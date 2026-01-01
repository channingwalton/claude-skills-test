package library

import munit.FunSuite

class LibraryTest extends FunSuite:

  test("Library can contain books"):
    val book1 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val book2 = Book("Refactoring", "Martin Fowler", "978-0134757599")

    val library = Library(books = List(book1, book2))

    assertEquals(library.books.size, 2)
    assertEquals(library.books.contains(book1), true)
    assertEquals(library.books.contains(book2), true)

  test("Library can be created empty"):
    val library = Library.empty

    assertEquals(library.books.size, 0)

  test("Books can be added to the library"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library.empty

    val updatedLibrary = library.addBook(book)

    assertEquals(updatedLibrary.books.size, 1)
    assertEquals(updatedLibrary.books.contains(book), true)
