package library

import munit.FunSuite

class MultipleCopiesTest extends FunSuite:

  test("Add multiple copies of a book to the library"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val library = Library.empty
      .addBook(book)
      .addBook(book)
      .addBook(book)

    assertEquals(library.getCopiesCount(book), 3)

  test("The library knows how many copies of a book it has"):
    val book1 = Book("Clean Code", "Robert Martin", "978-0132350884")
    val book2 = Book("Refactoring", "Martin Fowler", "978-0134757599")
    val library = Library.empty
      .addBook(book1)
      .addBook(book1)
      .addBook(book2)

    assertEquals(library.getCopiesCount(book1), 2)
    assertEquals(library.getCopiesCount(book2), 1)

  test("Members can only withdraw a book if there is a copy available"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)

    val bob = Member("Bob")
    val libraryWithBob = library.addMember(bob).getOrElse(library)
    val result = libraryWithBob.withdrawBook(bob, book)

    assertEquals(result, Left(WithdrawalError.BookAlreadyWithdrawn))

  test("Multiple members can withdraw the same book if multiple copies exist"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library.empty
      .addBook(book)
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)

    val result = library.withdrawBook(bob, book)

    assertEquals(result.isRight, true)

  test("Available copies decreases when books are withdrawn"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addBook(book)
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)

    assertEquals(library.getAvailableCopiesCount(book), 2)

  test("Available copies increases when books are returned"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)
      .returnBook(alice, book).getOrElse(Library.empty)

    assertEquals(library.getAvailableCopiesCount(book), 2)

  test("Cannot withdraw more copies than available"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")
    val library = Library.empty
      .addBook(book)
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)
      .addMember(charlie).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)
      .withdrawBook(bob, book).getOrElse(Library.empty)

    val result = library.withdrawBook(charlie, book)

    assertEquals(result, Left(WithdrawalError.BookAlreadyWithdrawn))
