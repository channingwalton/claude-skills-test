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
    val library = Library(List(book1))

    val result = library.addBook(book2)

    assertEquals(result.map(_.books), Right(List(book1)))

  test("search by title returns matching books case insensitively"):
    val book1 = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
    val book2 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(List(book1, book2))

    val result = library.searchByTitle("pragmatic")

    assertEquals(result, Right(List(book1)))

  test("search with fewer than 3 non-whitespace characters returns error"):
    val library = Library.empty

    val result = library.searchByTitle("ab")

    assertEquals(result, Left(LibraryError.InvalidSearchQuery))

  test("search with no matches returns empty list"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(List(book))

    val result = library.searchByTitle("nonexistent")

    assertEquals(result, Right(List.empty))

  test("search by author returns matching books case insensitively"):
    val book1 = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
    val book2 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(List(book1, book2))

    val result = library.searchByAuthor("martin")

    assertEquals(result, Right(List(book2)))

  test("search by ISBN normalizes query and book ISBN to digits only"):
    val book = Book("Clean Code", "Robert Martin", "978-0-13-235088-4")
    val library = Library(List(book))

    val result = library.searchByISBN("978-013")

    assertEquals(result, Right(List(book)))

  test("search by ISBN strips ISBN prefix from query"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library(List(book))

    val result = library.searchByISBN("ISBN978")

    assertEquals(result, Right(List(book)))

  test("search by ISBN with fewer than 3 digits returns error"):
    val library = Library.empty

    val result = library.searchByISBN("ISBN-97")

    assertEquals(result, Left(LibraryError.InvalidSearchQuery))

  test("adding a member to empty library results in library containing that member"):
    val member = Member("Alice")
    val library = Library.empty

    val result = library.addMember(member)

    assertEquals(result.members, List(member))

  test("adding a member with same name is ignored"):
    val member = Member("Alice")
    val library = Library(List.empty, List(member))

    val result = library.addMember(member)

    assertEquals(result.members, List(member))

  test("find member by name returns matching members case insensitively"):
    val alice = Member("Alice Smith")
    val bob = Member("Bob Jones")
    val library = Library(List.empty, List(alice, bob))

    val result = library.findMemberByName("alice")

    assertEquals(result, Right(List(alice)))

  test("removing a member results in library without that member"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library(List.empty, List(alice, bob))

    val result = library.removeMember(alice)

    assertEquals(result.map(_.members), Right(List(bob)))

  test("removing a non-existent member returns error"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library(List.empty, List(alice))

    val result = library.removeMember(bob)

    assertEquals(result, Left(LibraryError.NoSuchMember))

  test("member can withdraw an available book"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val library = Library(List(book), List(alice))

    val result = library.withdraw(alice, book)

    assert(result.isRight)

  test("cannot withdraw book already withdrawn by someone else"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library(List(book), List(alice, bob), Map(book -> alice))

    val result = library.withdraw(bob, book)

    assertEquals(result, Left(LibraryError.BookUnavailable))

  test("member cannot withdraw the same book twice"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val library = Library(List(book), List(alice), Map(book -> alice))

    val result = library.withdraw(alice, book)

    assertEquals(result, Left(LibraryError.BookAlreadyHeld))

  test("member's withdrawn books can be queried"):
    val book1 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val book2 = Book("The Pragmatic Programmer", "David Thomas", "978-0135957059")
    val alice = Member("Alice")
    val library = Library(List(book1, book2), List(alice), Map(book1 -> alice))

    val result = library.booksFor(alice)

    assertEquals(result, List(book1))

  test("member can return a book they withdrew"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val library = Library(List(book), List(alice), Map(book -> alice))

    val result = library.returnBook(alice, book)

    assertEquals(result.map(_.booksFor(alice)), Right(List.empty))
    assertEquals(result.map(_.withdrawals.contains(book)), Right(false))

  test("cannot return book held by another member"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library(List(book), List(alice, bob), Map(book -> alice))

    val result = library.returnBook(bob, book)

    assertEquals(result, Left(LibraryError.BookNotHeld))
