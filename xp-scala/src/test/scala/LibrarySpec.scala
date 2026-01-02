class LibrarySpec extends munit.FunSuite:

  test("adding a book to empty library results in library containing that book"):
    val book = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
    val emptyLibrary = Library.empty

    val result = emptyLibrary.addBook(book)

    assertEquals(result.map(_.books), Right(List(book)))

  test("adding a book with blank ISBN returns an error"):
    val book = Book("Some Title", "Some Author", "")
    val library = Library.empty

    val result = library.addBook(book)

    assertEquals(result, Left(LibraryError.InvalidISBN))
