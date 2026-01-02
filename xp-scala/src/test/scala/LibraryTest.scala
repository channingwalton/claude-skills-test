import munit.FunSuite

class LibraryTest extends FunSuite:

  test("Library.empty creates a library with no books"):
    val library = Library.empty

    assertEquals(library.books, List.empty[Book])
