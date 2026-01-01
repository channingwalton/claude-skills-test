package library

class LibraryTest extends munit.FunSuite:
  test("Library can be created empty"):
    val library = Library()

    assertEquals(library.books, List.empty[Book])

  test("Book can be added to library"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library()

    val updatedLibrary = library.addBook(book)

    assertEquals(updatedLibrary.books, List(book))

  test("Multiple books can be added to library"):
    val book1 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val book2 = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
    val library = Library()

    val updatedLibrary = library.addBook(book1).addBook(book2)

    assertEquals(updatedLibrary.books, List(book1, book2))
