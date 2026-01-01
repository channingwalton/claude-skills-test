package claude.skills

import munit.FunSuite

class LibraryTest extends FunSuite:

  // Sample data
  val book1 = Book("The Great Gatsby", "F. Scott Fitzgerald", "ISBN 978-0-7432-7356-5")
  val book2 = Book("To Kill a Mockingbird", "Harper Lee", "ISBN 978-0-06-112008-4")
  val book3 = Book("1984", "George Orwell", "ISBN 978-0-452-28423-4")
  val book4 = Book("Pride and Prejudice", "Jane Austen", "ISBN 978-0-14-143951-8")
  val book5 = Book("The Catcher in the Rye", "J.D. Salinger", "ISBN 978-0-316-76948-0")

  val member1 = Member("Alice")
  val member2 = Member("Bob")
  val member3 = Member("Charlie")

  // =============================================================================
  // Add books to the library
  // =============================================================================

  test("Library contains a collection of Books") {
    val library = Library(book1, book2, book3)
    assertEquals(library.books, Set(book1, book2, book3))
  }

  test("Book has title, author, and ISBN") {
    assertEquals(book1.title, "The Great Gatsby")
    assertEquals(book1.author, "F. Scott Fitzgerald")
    assertEquals(book1.isbn, "ISBN 978-0-7432-7356-5")
  }

  test("Can add books to empty library") {
    val library = Library.empty.addBook(book1).addBook(book2)
    assertEquals(library.books, Set(book1, book2))
  }

  // =============================================================================
  // Search for books by title substring
  // =============================================================================

  test("Search by title substring returns matching books") {
    val library = Library(book1, book2, book3, book4, book5)
    val result = library.searchByTitle("Great")
    assertEquals(result, Right(Set(book1)))
  }

  test("Search by title is case insensitive") {
    val library = Library(book1, book2, book3)
    val result1 = library.searchByTitle("great")
    val result2 = library.searchByTitle("GREAT")
    val result3 = library.searchByTitle("GrEaT")
    assertEquals(result1, Right(Set(book1)))
    assertEquals(result2, Right(Set(book1)))
    assertEquals(result3, Right(Set(book1)))
  }

  test("Search by title with less than 3 non-whitespace chars fails") {
    val library = Library(book1, book2, book3)
    val result1 = library.searchByTitle("ab")
    val result2 = library.searchByTitle("  ab  ")
    val result3 = library.searchByTitle("a b")
    assertEquals(result1, Left(LibraryError.SearchTermTooShort(3)))
    assertEquals(result2, Left(LibraryError.SearchTermTooShort(3)))
    assertEquals(result3, Left(LibraryError.SearchTermTooShort(3)))
  }

  test("Search by title with 3 or more non-whitespace chars succeeds") {
    val library = Library(book1, book2, book3, book4, book5)
    val result = library.searchByTitle("the")
    assertEquals(result, Right(Set(book1, book5)))
  }

  // =============================================================================
  // Search for books by author
  // =============================================================================

  test("Search by author substring returns matching books") {
    val library = Library(book1, book2, book3, book4, book5)
    val result = library.searchByAuthor("Fitzgerald")
    assertEquals(result, Right(Set(book1)))
  }

  test("Search by author is case insensitive") {
    val library = Library(book1, book2, book3)
    val result1 = library.searchByAuthor("orwell")
    val result2 = library.searchByAuthor("ORWELL")
    val result3 = library.searchByAuthor("OrWeLl")
    assertEquals(result1, Right(Set(book3)))
    assertEquals(result2, Right(Set(book3)))
    assertEquals(result3, Right(Set(book3)))
  }

  test("Search by author with less than 3 non-whitespace chars fails") {
    val library = Library(book1, book2, book3)
    val result1 = library.searchByAuthor("or")
    val result2 = library.searchByAuthor("  or  ")
    assertEquals(result1, Left(LibraryError.SearchTermTooShort(3)))
    assertEquals(result2, Left(LibraryError.SearchTermTooShort(3)))
  }

  test("Search by author with 3 or more non-whitespace chars succeeds") {
    val library = Library(book1, book2, book3)
    val result = library.searchByAuthor("Lee")
    assertEquals(result, Right(Set(book2)))
  }

  // =============================================================================
  // Search for books by ISBN substring
  // =============================================================================

  test("Search by ISBN substring returns matching books") {
    val library = Library(book1, book2, book3, book4, book5)
    val result = library.searchByIsbn("7432")
    assertEquals(result, Right(Set(book1)))
  }

  test("Search by ISBN ignores ISBN prefix") {
    val library = Library(book1, book2, book3)
    val result = library.searchByIsbn("ISBN 978")
    assertEquals(result, Right(Set(book1, book2, book3)))
  }

  test("Search by ISBN ignores non-digit characters") {
    val library = Library(book1, book2, book3)
    val result1 = library.searchByIsbn("978-0-7432")
    val result2 = library.searchByIsbn("978 0 7432")
    assertEquals(result1, Right(Set(book1)))
    assertEquals(result2, Right(Set(book1)))
  }

  test("Search by ISBN with less than 3 digits fails") {
    val library = Library(book1, book2, book3)
    val result1 = library.searchByIsbn("97")
    val result2 = library.searchByIsbn("ISBN 97")
    assertEquals(result1, Left(LibraryError.SearchTermTooShort(3)))
    assertEquals(result2, Left(LibraryError.SearchTermTooShort(3)))
  }

  test("Search by ISBN with 3 or more digits succeeds") {
    val library = Library(book1, book2, book3)
    val result = library.searchByIsbn("978")
    assertEquals(result, Right(Set(book1, book2, book3)))
  }

  // =============================================================================
  // Add Members
  // =============================================================================

  test("Members can be added to the library") {
    val result = for
      lib1 <- Library.empty.addMember(member1)
      lib2 <- lib1.addMember(member2)
    yield lib2
    assertEquals(result.map(_.allMembers), Right(Set(member1, member2)))
  }

  test("Members can only be added once") {
    val result = for
      lib1 <- Library.empty.addMember(member1)
      lib2 <- lib1.addMember(member1)
    yield lib2
    assertEquals(result, Left(LibraryError.MemberAlreadyExists(member1.name)))
  }

  test("Members can be found by name") {
    val library = Library.empty
      .addMember(member1).toOption.get
      .addMember(member2).toOption.get
    assertEquals(library.findMemberByName("Alice"), Some(member1))
    assertEquals(library.findMemberByName("Bob"), Some(member2))
    assertEquals(library.findMemberByName("Charlie"), None)
  }

  test("Members can be removed") {
    val result = for
      lib1 <- Library.empty.addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.removeMember(member1)
    yield lib3
    assertEquals(result.map(_.allMembers), Right(Set(member2)))
  }

  test("Removing non-existent member fails") {
    val library = Library.empty.addMember(member1).toOption.get
    val result = library.removeMember(member2)
    assertEquals(result, Left(LibraryError.MemberNotFound(member2.name)))
  }

  test("Sum of members added and removed equals number of members") {
    val result = for
      lib1 <- Library.empty.addMember(member1)         // added: 1
      lib2 <- lib1.addMember(member2)                   // added: 2
      lib3 <- lib2.addMember(member3)                   // added: 3
      lib4 <- lib3.removeMember(member1)                // removed: 1
    yield lib4
    // 3 added - 1 removed = 2 members
    assertEquals(result.map(_.allMembers.size), Right(2))
  }

  // =============================================================================
  // Withdrawing books
  // =============================================================================

  test("A member can withdraw a book") {
    val result = for
      lib1 <- Library(book1, book2).addMember(member1)
      lib2 <- lib1.withdrawBook(member1, book1)
    yield lib2
    assertEquals(result.map(_.booksWithdrawnBy(member1)), Right(Set(book1)))
  }

  test("A member can only withdraw a book that hasn't already been withdrawn") {
    val result = for
      lib1 <- Library(book1).addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.withdrawBook(member1, book1)
      lib4 <- lib3.withdrawBook(member2, book1)
    yield lib4
    assertEquals(result, Left(LibraryError.BookNotAvailable(book1.isbn)))
  }

  test("A member can only withdraw a book once") {
    val result = for
      lib1 <- Library(book1, book2).addMember(member1)
      lib2 <- lib1.addBookCopies(book1, 1).addMember(member2) // 2 copies now
      lib3 <- lib2.withdrawBook(member1, book1)
      lib4 <- lib3.withdrawBook(member1, book1)
    yield lib4
    assertEquals(result, Left(LibraryError.BookAlreadyWithdrawnByMember(book1.isbn, member1.name)))
  }

  test("A member's book list contains the book they withdrew") {
    val result = for
      lib1 <- Library(book1, book2, book3).addMember(member1)
      lib2 <- lib1.withdrawBook(member1, book1)
      lib3 <- lib2.withdrawBook(member1, book2)
    yield lib3
    assertEquals(result.map(_.booksWithdrawnBy(member1)), Right(Set(book1, book2)))
  }

  test("Non-member cannot withdraw a book") {
    val library = Library(book1, book2)
    val result = library.withdrawBook(member1, book1)
    assertEquals(result, Left(LibraryError.MemberNotFound(member1.name)))
  }

  test("Cannot withdraw a book not in the library") {
    val library = Library(book1).addMember(member1).toOption.get
    val result = library.withdrawBook(member1, book2)
    assertEquals(result, Left(LibraryError.BookNotFound(book2.isbn)))
  }

  // =============================================================================
  // Return books
  // =============================================================================

  test("A member can return a book") {
    val result = for
      lib1 <- Library(book1, book2).addMember(member1)
      lib2 <- lib1.withdrawBook(member1, book1)
      lib3 <- lib2.returnBook(member1, book1)
    yield lib3
    assertEquals(result.map(_.booksWithdrawnBy(member1)), Right(Set.empty[Book]))
  }

  test("A member can only return a book if they withdrew it") {
    val result = for
      lib1 <- Library(book1).addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.withdrawBook(member1, book1)
      lib4 <- lib3.returnBook(member2, book1)
    yield lib4
    assertEquals(result, Left(LibraryError.BookNotWithdrawnByMember(book1.isbn, member2.name)))
  }

  test("A member can only return a book once") {
    val result = for
      lib1 <- Library(book1).addMember(member1)
      lib2 <- lib1.withdrawBook(member1, book1)
      lib3 <- lib2.returnBook(member1, book1)
      lib4 <- lib3.returnBook(member1, book1)
    yield lib4
    assertEquals(result, Left(LibraryError.BookNotWithdrawnByMember(book1.isbn, member1.name)))
  }

  test("A member's book list should not contain the book they returned") {
    val result = for
      lib1 <- Library(book1, book2, book3).addMember(member1)
      lib2 <- lib1.withdrawBook(member1, book1)
      lib3 <- lib2.withdrawBook(member1, book2)
      lib4 <- lib3.returnBook(member1, book1)
    yield lib4
    assertEquals(result.map(_.booksWithdrawnBy(member1)), Right(Set(book2)))
  }

  // =============================================================================
  // Multiple copies
  // =============================================================================

  test("Can add multiple copies of a book to the library") {
    val library = Library.empty.addBookCopies(book1, 3)
    assertEquals(library.totalCopies(book1), 3)
  }

  test("Library knows how many copies of a book it has") {
    val library = Library.empty
      .addBook(book1)
      .addBookCopies(book1, 2)
      .addBookCopies(book2, 5)
    assertEquals(library.totalCopies(book1), 3)
    assertEquals(library.totalCopies(book2), 5)
    assertEquals(library.totalCopies(book3), 0)
  }

  test("Members can only withdraw a book if there is a copy available") {
    val result = for
      lib1 <- Library(book1).addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.withdrawBook(member1, book1)
      lib4 <- lib3.withdrawBook(member2, book1)
    yield lib4
    assertEquals(result, Left(LibraryError.BookNotAvailable(book1.isbn)))
  }

  test("Available copies decrease when books are withdrawn") {
    val result = for
      lib1 <- Library.empty.addBookCopies(book1, 3).addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.addMember(member3)
      lib4 <- lib3.withdrawBook(member1, book1)
      lib5 <- lib4.withdrawBook(member2, book1)
    yield lib5

    result match
      case Right(library) =>
        assertEquals(library.totalCopies(book1), 3)
        assertEquals(library.withdrawnCopies(book1), 2)
        assertEquals(library.availableCopies(book1), 1)
      case Left(error) =>
        fail(s"Unexpected error: $error")
  }

  test("Multiple members can withdraw copies of the same book if copies are available") {
    val result = for
      lib1 <- Library.empty.addBookCopies(book1, 3).addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.addMember(member3)
      lib4 <- lib3.withdrawBook(member1, book1)
      lib5 <- lib4.withdrawBook(member2, book1)
      lib6 <- lib5.withdrawBook(member3, book1)
    yield lib6

    result match
      case Right(library) =>
        assertEquals(library.availableCopies(book1), 0)
        assertEquals(library.booksWithdrawnBy(member1), Set(book1))
        assertEquals(library.booksWithdrawnBy(member2), Set(book1))
        assertEquals(library.booksWithdrawnBy(member3), Set(book1))
      case Left(error) =>
        fail(s"Unexpected error: $error")
  }

  test("Returned books increase available copies") {
    val result = for
      lib1 <- Library.empty.addBookCopies(book1, 2).addMember(member1)
      lib2 <- lib1.addMember(member2)
      lib3 <- lib2.withdrawBook(member1, book1)
      lib4 <- lib3.withdrawBook(member2, book1)
      lib5 <- lib4.returnBook(member1, book1)
    yield lib5

    result match
      case Right(library) =>
        assertEquals(library.totalCopies(book1), 2)
        assertEquals(library.withdrawnCopies(book1), 1)
        assertEquals(library.availableCopies(book1), 1)
      case Left(error) =>
        fail(s"Unexpected error: $error")
  }
