package library

import munit.FunSuite

class ReturnTest extends FunSuite:

  test("A member can return a book they withdrew"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(member).getOrElse(Library.empty)
      .withdrawBook(member, book).getOrElse(Library.empty)

    val result = library.returnBook(member, book)

    assertEquals(result.isRight, true)

  test("A member can only return a book if they withdrew it"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library.empty
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)

    val result = library.returnBook(bob, book)

    assertEquals(result, Left(ReturnError.BookNotBorrowedByMember))

  test("A member can only return a book once"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(member).getOrElse(Library.empty)
      .withdrawBook(member, book).getOrElse(Library.empty)
      .returnBook(member, book).getOrElse(Library.empty)

    val result = library.returnBook(member, book)

    assertEquals(result, Left(ReturnError.BookNotBorrowedByMember))

  test("A member's book list should not contain the book they returned"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val member = Member("Alice")
    val library = Library.empty
      .addBook(book)
      .addMember(member).getOrElse(Library.empty)
      .withdrawBook(member, book).getOrElse(Library.empty)
      .returnBook(member, book).getOrElse(Library.empty)

    val memberBooks = library.getBooksForMember(member)

    assertEquals(memberBooks, List.empty[Book])

  test("A returned book can be withdrawn again"):
    val book = Book("Clean Code", "Robert Martin", "978-0132350884")
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library.empty
      .addBook(book)
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)
      .withdrawBook(alice, book).getOrElse(Library.empty)
      .returnBook(alice, book).getOrElse(Library.empty)

    val result = library.withdrawBook(bob, book)

    assertEquals(result.isRight, true)
