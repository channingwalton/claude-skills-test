package claude.skills

class LibrarySuite extends munit.FunSuite:

  test("Book has title, author, and ISBN"):
    val book = Book("1984", "George Orwell", "978-0451524935")
    assertEquals(book.title, "1984")
    assertEquals(book.author, "George Orwell")
    assertEquals(book.isbn, "978-0451524935")

  test("Library starts empty"):
    val library = Library()
    assertEquals(library.books, List.empty)

  test("addBook returns a new Library with the book"):
    val library = Library()
    val book = Book("1984", "George Orwell", "978-0451524935")
    val updatedLibrary = library.addBook(book)
    assertEquals(updatedLibrary.books, List(book))

  test("addBook does not mutate the original Library"):
    val library = Library()
    val book = Book("1984", "George Orwell", "978-0451524935")
    library.addBook(book)
    assertEquals(library.books, List.empty)

  test("multiple books can be added"):
    val book1 = Book("1984", "George Orwell", "978-0451524935")
    val book2 = Book("Brave New World", "Aldous Huxley", "978-0060850524")
    val library = Library()
      .addBook(book1)
      .addBook(book2)
    assertEquals(library.books, List(book1, book2))
