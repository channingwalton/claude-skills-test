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

  // Search by title tests
  test("Search by title returns matching books (substring match)"):
    val book1 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val book2 = Book("Code Complete", "Steve McConnell", "978-0735619678")
    val book3 = Book("Refactoring", "Martin Fowler", "978-0134757599")
    val library = Library(books = List(book1, book2, book3))

    val result = library.searchByTitle("Code")

    assertEquals(result, Right(List(book1, book2)))

  test("Search by title is case insensitive"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(books = List(book))

    val result = library.searchByTitle("clean")

    assertEquals(result, Right(List(book)))

  test("Search by title with uppercase query"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(books = List(book))

    val result = library.searchByTitle("CLEAN")

    assertEquals(result, Right(List(book)))

  test("Search by title fails with less than 3 non-whitespace characters"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(books = List(book))

    val result = library.searchByTitle("ab")

    assertEquals(result, Left(SearchError.QueryTooShort))

  test("Search by title ignores whitespace when counting characters"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(books = List(book))

    val result = library.searchByTitle("a  b")

    assertEquals(result, Left(SearchError.QueryTooShort))

  test("Search by title with exactly 3 non-whitespace characters succeeds"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(books = List(book))

    val result = library.searchByTitle("Cle")

    assertEquals(result, Right(List(book)))

  test("Search by title returns empty list when no matches"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(books = List(book))

    val result = library.searchByTitle("xyz")

    assertEquals(result, Right(List.empty))
