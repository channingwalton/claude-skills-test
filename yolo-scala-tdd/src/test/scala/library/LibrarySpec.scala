package library

import munit.FunSuite

class LibrarySpec extends FunSuite:

  test("a book has a title, author, and ISBN"):
    val book = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    assertEquals(book.title, "The Hobbit")
    assertEquals(book.author, "J.R.R. Tolkien")
    assertEquals(book.isbn, "978-0-261-10295-4")

  test("a library can contain books"):
    val library = Library()
    val book = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(book)
    assertEquals(library.books.size, 1)
    assertEquals(library.books.head, book)

  // Search by title tests
  test("search by title finds books with matching substring"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    val lotr = Book("The Lord of the Rings", "J.R.R. Tolkien", "978-0-261-10325-8")
    library.addBook(hobbit)
    library.addBook(lotr)
    val results = library.searchByTitle("Hobbit")
    assertEquals(results.size, 1)
    assertEquals(results.head, hobbit)

  test("search by title is case insensitive"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByTitle("hobbit").size, 1)
    assertEquals(library.searchByTitle("HOBBIT").size, 1)
    assertEquals(library.searchByTitle("HoBbIt").size, 1)

  test("search by title requires minimum 3 non-whitespace characters"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByTitle("Th").size, 0)
    assertEquals(library.searchByTitle("  Th  ").size, 0)
    assertEquals(library.searchByTitle("The").size, 1)

  // Search by author tests
  test("search by author finds books with matching substring"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    val dune = Book("Dune", "Frank Herbert", "978-0-441-17271-9")
    library.addBook(hobbit)
    library.addBook(dune)
    val results = library.searchByAuthor("Tolkien")
    assertEquals(results.size, 1)
    assertEquals(results.head, hobbit)

  test("search by author is case insensitive"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByAuthor("tolkien").size, 1)
    assertEquals(library.searchByAuthor("TOLKIEN").size, 1)
    assertEquals(library.searchByAuthor("ToLkIeN").size, 1)

  test("search by author requires minimum 3 non-whitespace characters"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByAuthor("To").size, 0)
    assertEquals(library.searchByAuthor("  To  ").size, 0)
    assertEquals(library.searchByAuthor("Tol").size, 1)

  // Search by ISBN tests
  test("search by ISBN finds books with matching digit substring"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    val dune = Book("Dune", "Frank Herbert", "978-0-441-17271-9")
    library.addBook(hobbit)
    library.addBook(dune)
    val results = library.searchByIsbn("102954")
    assertEquals(results.size, 1)
    assertEquals(results.head, hobbit)

  test("search by ISBN ignores ISBN prefix"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByIsbn("ISBN 978").size, 1)
    assertEquals(library.searchByIsbn("isbn978").size, 1)

  test("search by ISBN ignores non-digit characters in query"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByIsbn("978-0-261").size, 1)
    assertEquals(library.searchByIsbn("978 0 261").size, 1)

  test("search by ISBN requires minimum 3 digit characters"):
    val library = Library()
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addBook(hobbit)
    assertEquals(library.searchByIsbn("97").size, 0)
    assertEquals(library.searchByIsbn("978").size, 1)

  // Member tests
  test("a member has a name"):
    val member = Member("Alice")
    assertEquals(member.name, "Alice")

  test("members can be added to the library"):
    val library = Library()
    val member = Member("Alice")
    library.addMember(member)
    assertEquals(library.members.size, 1)

  test("members can only be added once"):
    val library = Library()
    val alice = Member("Alice")
    library.addMember(alice)
    library.addMember(alice)
    assertEquals(library.members.size, 1)

  test("members can be found by name"):
    val library = Library()
    val alice = Member("Alice")
    val bob = Member("Bob")
    library.addMember(alice)
    library.addMember(bob)
    assertEquals(library.findMemberByName("Alice"), Some(alice))
    assertEquals(library.findMemberByName("Charlie"), None)

  test("members can be removed"):
    val library = Library()
    val alice = Member("Alice")
    library.addMember(alice)
    library.removeMember(alice)
    assertEquals(library.members.size, 0)

  test("member count equals added minus removed"):
    val library = Library()
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")
    library.addMember(alice)
    library.addMember(bob)
    library.addMember(charlie)
    library.removeMember(bob)
    assertEquals(library.members.size, 2)

  // Withdrawal tests
  test("a member can withdraw a book"):
    val library = Library()
    val alice = Member("Alice")
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addMember(alice)
    library.addBook(hobbit)
    val result = library.withdrawBook(alice, hobbit)
    assertEquals(result, true)

  test("a member can only withdraw a book that hasn't already been withdrawn"):
    val library = Library()
    val alice = Member("Alice")
    val bob = Member("Bob")
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addMember(alice)
    library.addMember(bob)
    library.addBook(hobbit)
    library.withdrawBook(alice, hobbit)
    val result = library.withdrawBook(bob, hobbit)
    assertEquals(result, false)

  test("a member can only withdraw a book once"):
    val library = Library()
    val alice = Member("Alice")
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addMember(alice)
    library.addBook(hobbit)
    library.withdrawBook(alice, hobbit)
    val result = library.withdrawBook(alice, hobbit)
    assertEquals(result, false)

  test("a member's book list should contain the book they withdrew"):
    val library = Library()
    val alice = Member("Alice")
    val hobbit = Book("The Hobbit", "J.R.R. Tolkien", "978-0-261-10295-4")
    library.addMember(alice)
    library.addBook(hobbit)
    library.withdrawBook(alice, hobbit)
    assertEquals(library.getBooksForMember(alice).size, 1)
    assertEquals(library.getBooksForMember(alice).head, hobbit)
