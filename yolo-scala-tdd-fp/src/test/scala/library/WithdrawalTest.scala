package library

import munit.FunSuite

class WithdrawalTest extends FunSuite:

  test("A member can withdraw a book"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(member).getOrElse(Library.empty)

    val result = library.withdrawBook(member, book)

    assertEquals(result.isRight, true)

  test("A member can only withdraw a book that hasn't already been withdrawn"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library.empty
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)

    val result = library.withdrawBook(bob, book)

    assertEquals(result, Left(WithdrawalError.BookAlreadyWithdrawn))

  test("A member can only withdraw a book once"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(member).getOrElse(Library.empty)
      .withdrawBook(member, book).getOrElse(Library.empty)

    val result = library.withdrawBook(member, book)

    assertEquals(result, Left(WithdrawalError.BookAlreadyWithdrawn))

  test("A member's book list should contain the book they withdrew"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(member).getOrElse(Library.empty)
      .withdrawBook(member, book).getOrElse(Library.empty)

    val memberBooks = library.getBooksForMember(member)

    assertEquals(memberBooks, List(book))

  test("Cannot withdraw a book not in the library"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addMember(member).getOrElse(Library.empty)

    val result = library.withdrawBook(member, book)

    assertEquals(result, Left(WithdrawalError.BookNotInLibrary))

  test("Cannot withdraw if member is not registered"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty.addBook(book)

    val result = library.withdrawBook(member, book)

    assertEquals(result, Left(WithdrawalError.MemberNotRegistered))
