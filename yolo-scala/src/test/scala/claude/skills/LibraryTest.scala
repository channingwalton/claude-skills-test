package claude.skills

class LibraryTest extends munit.FunSuite:

  // Test fixtures
  val book1 = Book("The Great Gatsby", "F. Scott Fitzgerald", "ISBN 978-0-7432-7356-5")
  val book2 = Book("To Kill a Mockingbird", "Harper Lee", "ISBN 978-0-06-112008-4")
  val book3 = Book("1984", "George Orwell", "ISBN 978-0-452-28423-4")
  val book4 = Book("Great Expectations", "Charles Dickens", "ISBN 978-0-14-143956-3")

  // ============================================
  // Add books to the library
  // ============================================

  test("library contains a collection of books") {
    val library = new Library
    library.addBook(book1)
    library.addBook(book2)
    assertEquals(library.books, Set(book1, book2))
  }

  test("book has title, author, and ISBN") {
    assertEquals(book1.title, "The Great Gatsby")
    assertEquals(book1.author, "F. Scott Fitzgerald")
    assertEquals(book1.isbn, "ISBN 978-0-7432-7356-5")
  }

  // ============================================
  // Search for books by title substring
  // ============================================

  test("search by title finds matching books") {
    val library = new Library
    library.addBook(book1)
    library.addBook(book2)
    library.addBook(book3)
    library.addBook(book4)

    val results = library.searchByTitle("Great")
    assertEquals(results, Set(book1, book4))
  }

  test("search by title is case insensitive") {
    val library = new Library
    library.addBook(book1)
    library.addBook(book4)

    val lowerResults = library.searchByTitle("great")
    val upperResults = library.searchByTitle("GREAT")
    val mixedResults = library.searchByTitle("GrEaT")

    assertEquals(lowerResults, Set(book1, book4))
    assertEquals(upperResults, Set(book1, book4))
    assertEquals(mixedResults, Set(book1, book4))
  }

  test("search by title requires minimum 3 non-whitespace characters") {
    val library = new Library
    library.addBook(book1)
    library.addBook(book3)

    assertEquals(library.searchByTitle("Th"), Set.empty)
    assertEquals(library.searchByTitle("The").size, 1)
    assertEquals(library.searchByTitle("  T  "), Set.empty) // only 1 non-whitespace
    assertEquals(library.searchByTitle(" Th "), Set.empty)  // only 2 non-whitespace
    assertEquals(library.searchByTitle("The"), Set(book1))  // exactly 3
  }

  // ============================================
  // Search for books by author
  // ============================================

  test("search by author finds matching books") {
    val library = new Library
    library.addBook(book1)
    library.addBook(book2)
    library.addBook(book3)

    val results = library.searchByAuthor("Orwell")
    assertEquals(results, Set(book3))
  }

  test("search by author is case insensitive") {
    val library = new Library
    library.addBook(book3)

    assertEquals(library.searchByAuthor("orwell"), Set(book3))
    assertEquals(library.searchByAuthor("ORWELL"), Set(book3))
    assertEquals(library.searchByAuthor("OrWeLl"), Set(book3))
  }

  test("search by author requires minimum 3 non-whitespace characters") {
    val library = new Library
    library.addBook(book2)

    assertEquals(library.searchByAuthor("Le"), Set.empty)
    assertEquals(library.searchByAuthor("Lee"), Set(book2))
    assertEquals(library.searchByAuthor("  L  "), Set.empty)
  }

  // ============================================
  // Search for books by ISBN substring
  // ============================================

  test("search by ISBN finds matching books") {
    val library = new Library
    library.addBook(book1)
    library.addBook(book2)

    // book1 ISBN digits: 9780743273565
    // book2 ISBN digits: 9780061120084
    val results = library.searchByIsbn("743")
    assertEquals(results, Set(book1))
  }

  test("search by ISBN ignores ISBN prefix") {
    val library = new Library
    library.addBook(book1)

    // Searching with "ISBN" prefix should still work
    val results = library.searchByIsbn("ISBN 743")
    assertEquals(results, Set(book1))
  }

  test("search by ISBN ignores non-digit characters") {
    val library = new Library
    library.addBook(book1)

    // These should all find the same book
    assertEquals(library.searchByIsbn("7-4-3"), Set(book1))
    assertEquals(library.searchByIsbn("7 4 3"), Set(book1))
    assertEquals(library.searchByIsbn("743"), Set(book1))
  }

  test("search by ISBN requires minimum 3 digits") {
    val library = new Library
    library.addBook(book1)

    assertEquals(library.searchByIsbn("74"), Set.empty)
    assertEquals(library.searchByIsbn("743"), Set(book1))
  }

  // ============================================
  // Add Members
  // ============================================

  test("members can only be added once") {
    val library = new Library
    val member = Member("Alice")

    assertEquals(library.addMember(member), true)
    assertEquals(library.addMember(member), false)
    assertEquals(library.members.size, 1)
  }

  test("members can be found by name") {
    val library = new Library
    val member = Member("Alice")
    library.addMember(member)

    assertEquals(library.findMemberByName("Alice"), Some(member))
    assertEquals(library.findMemberByName("Bob"), None)
  }

  test("members can be removed") {
    val library = new Library
    val member = Member("Alice")
    library.addMember(member)

    assertEquals(library.removeMember(member), true)
    assertEquals(library.members.contains(member), false)
    assertEquals(library.removeMember(member), false) // already removed
  }

  test("sum of members added and removed equals number of members") {
    val library = new Library
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")

    library.addMember(alice)
    library.addMember(bob)
    library.addMember(charlie)
    assertEquals(library.members.size, 3)

    library.removeMember(bob)
    assertEquals(library.members.size, 2) // 3 added - 1 removed = 2
  }

  // ============================================
  // Withdrawing books
  // ============================================

  test("member can only withdraw a book that has not already been withdrawn") {
    val library = new Library
    val alice = Member("Alice")
    val bob = Member("Bob")
    library.addMember(alice)
    library.addMember(bob)
    library.addBook(book1, 1) // only 1 copy

    assertEquals(library.withdrawBook(alice, book1), true)
    assertEquals(library.withdrawBook(bob, book1), false) // no available copies
  }

  test("member can only withdraw a book once") {
    val library = new Library
    val member = Member("Alice")
    library.addMember(member)
    library.addBook(book1, 2) // 2 copies

    assertEquals(library.withdrawBook(member, book1), true)
    assertEquals(library.withdrawBook(member, book1), false) // already has it
  }

  test("member's book list contains withdrawn book") {
    val library = new Library
    val member = Member("Alice")
    library.addMember(member)
    library.addBook(book1)

    library.withdrawBook(member, book1)
    assertEquals(member.borrowedBooks.contains(book1), true)
    assertEquals(member.hasBorrowed(book1), true)
  }

  // ============================================
  // Return books
  // ============================================

  test("member can only return a book if they withdrew it") {
    val library = new Library
    val alice = Member("Alice")
    val bob = Member("Bob")
    library.addMember(alice)
    library.addMember(bob)
    library.addBook(book1)

    library.withdrawBook(alice, book1)
    assertEquals(library.returnBook(bob, book1), false) // Bob didn't withdraw it
    assertEquals(library.returnBook(alice, book1), true)
  }

  test("member can only return a book once") {
    val library = new Library
    val member = Member("Alice")
    library.addMember(member)
    library.addBook(book1)

    library.withdrawBook(member, book1)
    assertEquals(library.returnBook(member, book1), true)
    assertEquals(library.returnBook(member, book1), false) // already returned
  }

  test("member's book list does not contain returned book") {
    val library = new Library
    val member = Member("Alice")
    library.addMember(member)
    library.addBook(book1)

    library.withdrawBook(member, book1)
    library.returnBook(member, book1)
    assertEquals(member.borrowedBooks.contains(book1), false)
    assertEquals(member.hasBorrowed(book1), false)
  }

  // ============================================
  // Multiple copies
  // ============================================

  test("add multiple copies to the library") {
    val library = new Library
    library.addBook(book1, 5)
    assertEquals(library.totalCopies(book1), 5)

    library.addBook(book1, 3) // add more copies
    assertEquals(library.totalCopies(book1), 8)
  }

  test("library knows how many copies of a book it has") {
    val library = new Library
    library.addBook(book1, 3)
    library.addBook(book2, 1)

    assertEquals(library.totalCopies(book1), 3)
    assertEquals(library.totalCopies(book2), 1)
    assertEquals(library.totalCopies(book3), 0) // not in library
  }

  test("members can only withdraw a book if there is a copy available") {
    val library = new Library
    val alice = Member("Alice")
    val bob = Member("Bob")
    library.addMember(alice)
    library.addMember(bob)
    library.addBook(book1, 1)

    assertEquals(library.availableCopiesCount(book1), 1)
    assertEquals(library.withdrawBook(alice, book1), true)
    assertEquals(library.availableCopiesCount(book1), 0)
    assertEquals(library.withdrawBook(bob, book1), false) // no copies available
  }

  test("available copies are accounted for when withdrawing") {
    val library = new Library
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")
    library.addMember(alice)
    library.addMember(bob)
    library.addMember(charlie)
    library.addBook(book1, 2)

    assertEquals(library.availableCopiesCount(book1), 2)

    library.withdrawBook(alice, book1)
    assertEquals(library.availableCopiesCount(book1), 1)

    library.withdrawBook(bob, book1)
    assertEquals(library.availableCopiesCount(book1), 0)

    assertEquals(library.withdrawBook(charlie, book1), false) // no copies left
    assertEquals(library.availableCopiesCount(book1), 0)

    // Return a book
    library.returnBook(alice, book1)
    assertEquals(library.availableCopiesCount(book1), 1)

    // Now charlie can borrow
    assertEquals(library.withdrawBook(charlie, book1), true)
    assertEquals(library.availableCopiesCount(book1), 0)
  }
