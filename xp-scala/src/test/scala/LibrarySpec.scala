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

  test("adding a book with duplicate ISBN is ignored"):
    val book1 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val book2 = Book("Different Title", "Different Author", "978-0132350884")
    val library = Library.empty.addBook(book1).getOrElse(Library.empty)

    val result = library.addBook(book2)

    assertEquals(result.map(_.books), Right(List(book1)))

  test("search by title returns matching books case insensitively"):
    val book1 = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
    val book2 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library.empty
      .addBook(book1).getOrElse(Library.empty)
      .addBook(book2).getOrElse(Library.empty)

    val result = library.searchByTitle("pragmatic")

    assertEquals(result, Right(List(book1)))
