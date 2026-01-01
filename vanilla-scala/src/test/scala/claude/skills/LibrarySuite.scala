package claude.skills

class LibrarySuite extends munit.FunSuite:

  test("Book has title, author, and ISBN"):
    val book = Book("1984", "George Orwell", "978-0451524935")
    assertEquals(book.title, "1984")
    assertEquals(book.author, "George Orwell")
    assertEquals(book.isbn, "978-0451524935")

  test("Library starts empty"):
    val library = Library()
    assertEquals(library.books, Map.empty)

  test("addBook returns a new Library with the book"):
    val library = Library()
    val book = Book("1984", "George Orwell", "978-0451524935")
    val updatedLibrary = library.addBook(book)
    assertEquals(updatedLibrary.books, Map(book -> 1))

  test("addBook does not mutate the original Library"):
    val library = Library()
    val book = Book("1984", "George Orwell", "978-0451524935")
    library.addBook(book)
    assertEquals(library.books, Map.empty)

  test("multiple books can be added"):
    val book1 = Book("1984", "George Orwell", "978-0451524935")
    val book2 = Book("Brave New World", "Aldous Huxley", "978-0060850524")
    val library = Library()
      .addBook(book1)
      .addBook(book2)
    assertEquals(library.books, Map(book1 -> 1, book2 -> 1))

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
    assertEquals(result, Left(AlreadyWithdrawn(book)))

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

  test("a member can only return a book if they withdrew it"):
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
    val result = library.returnBook(bob, book)
    assertEquals(result, Left(NotWithdrawnByMember(book, bob)))

  test("a member can only return a book once"):
    val alice = Member("Alice")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addMember(alice)
      .withdraw(alice, book)
      .toOption
      .get
      .returnBook(alice, book)
      .toOption
      .get
    val result = library.returnBook(alice, book)
    assertEquals(result, Left(NotWithdrawnByMember(book, alice)))

  test("a member's book list should not contain the book they returned"):
    val alice = Member("Alice")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addMember(alice)
      .withdraw(alice, book)
      .toOption
      .get
      .returnBook(alice, book)
      .toOption
      .get
    assertEquals(library.booksForMember(alice), List.empty)

  test("multiple copies can be added to the library"):
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addBook(book)
      .addBook(book)
    assertEquals(library.copyCount(book), 3)

  test("the library knows how many copies of a book it has"):
    val book1 = Book("1984", "George Orwell", "978-0451524935")
    val book2 = Book("Brave New World", "Aldous Huxley", "978-0060850524")
    val library = Library()
      .addBook(book1)
      .addBook(book1)
      .addBook(book2)
    assertEquals(library.copyCount(book1), 2)
    assertEquals(library.copyCount(book2), 1)

  test("members can only withdraw a book if there is a copy available"):
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

  test("the number of available copies for withdrawal should account for withdrawals"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
      .addBook(book)
      .addBook(book)
      .addMember(alice)
      .addMember(bob)
      .addMember(charlie)
    assertEquals(library.availableCopies(book), 3)
    val afterAlice = library.withdraw(alice, book).toOption.get
    assertEquals(afterAlice.availableCopies(book), 2)
    val afterBob = afterAlice.withdraw(bob, book).toOption.get
    assertEquals(afterBob.availableCopies(book), 1)
    val afterCharlie = afterBob.withdraw(charlie, book).toOption.get
    assertEquals(afterCharlie.availableCopies(book), 0)

  test("a non-member attempting to withdraw gets MemberNotFound"):
    val nonMember = Member("NonMember")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
    val result = library.withdraw(nonMember, book)
    assertEquals(result, Left(MemberNotFound(nonMember)))

  test("a non-member attempting to return gets MemberNotFound"):
    val nonMember = Member("NonMember")
    val book = Book("1984", "George Orwell", "978-0451524935")
    val library = Library()
      .addBook(book)
    val result = library.returnBook(nonMember, book)
    assertEquals(result, Left(MemberNotFound(nonMember)))
