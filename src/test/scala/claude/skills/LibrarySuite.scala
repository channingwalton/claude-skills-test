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

  test("members can only be added once"):
    val member = Member("Alice")
    val library = Library()
      .addMember(member)
      .addMember(member)
    assertEquals(library.members.size, 1)

  test("members can be found by name"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library()
      .addMember(alice)
      .addMember(bob)
    assertEquals(library.findMemberByName("Alice"), Some(alice))
    assertEquals(library.findMemberByName("Charlie"), None)

  test("members can be removed"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library()
      .addMember(alice)
      .addMember(bob)
      .removeMember(alice)
    assertEquals(library.members, Set(bob))

  test("the sum of members added and removed should be equal to the number of members"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")
    val added = Set(alice, bob, charlie)
    val removed = Set(alice)
    val library = Library()
      .addMember(alice)
      .addMember(bob)
      .addMember(charlie)
      .removeMember(alice)
    assertEquals(library.members.size, added.size - removed.size)
    assertEquals(library.members, added -- removed)

  test("a member can only withdraw a book that hasn't already been withdrawn"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addMember(alice)
      .addMember(bob)
      .withdraw(alice, book)
      .toOption
      .get
    val result = library.withdraw(bob, book)
    assertEquals(result, Left(BookUnavailable(book)))

  test("a member can only withdraw a book once"):
    val alice = Member("Alice")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addMember(alice)
      .withdraw(alice, book)
      .toOption
      .get
    val result = library.withdraw(alice, book)
    assertEquals(result, Left(BookUnavailable(book)))

  test("a member's book list should contain the book they withdrew"):
    val alice = Member("Alice")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addMember(alice)
      .withdraw(alice, book)
      .toOption
      .get
    assertEquals(library.booksForMember(alice), List(book))
