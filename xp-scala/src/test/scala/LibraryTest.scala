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
