class LibraryTest extends munit.FunSuite:
  val book1 = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
  val book2 = Book("Clean Code", "Robert Martin", "978-0132350884")

  test("empty library has no books"):
    val library = Library.empty
    assertEquals(library.books, List.empty[Book])

  test("addBook adds a book to the library"):
    val result = Library.empty.addBook(book1)
    assertEquals(result, Right(Library(List(book1))))

  test("addBook rejects duplicate ISBN"):
    val library = Library(List(book1))
    val result = library.addBook(book1)
    assertEquals(result, Left(LibraryError.DuplicateIsbn("978-0135957059")))

  test("addBook allows different books"):
    val result = for
      lib1 <- Library.empty.addBook(book1)
      lib2 <- lib1.addBook(book2)
    yield lib2
    assertEquals(result, Right(Library(List(book1, book2))))

  test("searchByTitle finds books with matching substring"):
    val library = Library(List(book1, book2))
    val result = library.searchByTitle("Pragmatic")
    assertEquals(result, Right(List(book1)))

  test("searchByTitle returns empty list when no matches"):
    val library = Library(List(book1, book2))
    val result = library.searchByTitle("Nonexistent")
    assertEquals(result, Right(List.empty[Book]))

  test("searchByTitle is case-insensitive"):
    val library = Library(List(book1, book2))
    val result = library.searchByTitle("pragmatic")
    assertEquals(result, Right(List(book1)))

  test("searchByTitle rejects query with fewer than 3 non-whitespace chars"):
    val library = Library(List(book1, book2))
    val result = library.searchByTitle("ab")
    assertEquals(result, Left(LibraryError.QueryTooShort))

  test("searchByTitle counts only non-whitespace chars for minimum length"):
    val library = Library(List(book1, book2))
    val result = library.searchByTitle("  ab  ")
    assertEquals(result, Left(LibraryError.QueryTooShort))
