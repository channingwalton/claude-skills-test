package claude.skills

// ADT for library errors
enum LibraryError:
  case SearchTermTooShort(minLength: Int)
  case MemberAlreadyExists(name: String)
  case MemberNotFound(name: String)
  case BookNotFound(isbn: String)
  case BookNotAvailable(isbn: String)
  case BookAlreadyWithdrawnByMember(isbn: String, memberName: String)
  case BookNotWithdrawnByMember(isbn: String, memberName: String)

// Book ADT - immutable case class
final case class Book(
  title: String,
  author: String,
  isbn: String
)

// Member ADT - immutable case class
final case class Member(name: String)

// Withdrawal record - tracks who withdrew which book
final case class Withdrawal(member: Member, book: Book)

// Library ADT - immutable case class
final case class Library private (
  bookCopies: Map[Book, Int],              // Book -> number of copies owned
  members: Set[Member],                     // Set of members
  withdrawals: Map[Book, List[Member]]      // Book -> list of members who have withdrawn a copy
):
  import LibraryError.*

  // Get all unique books in the library
  def books: Set[Book] = bookCopies.keySet

  // Add a book to the library (adds one copy)
  def addBook(book: Book): Library =
    copy(bookCopies = bookCopies.updated(book, bookCopies.getOrElse(book, 0) + 1))

  // Add multiple copies of a book
  def addBookCopies(book: Book, copies: Int): Library =
    copy(bookCopies = bookCopies.updated(book, bookCopies.getOrElse(book, 0) + copies))

  // Get total number of copies owned for a book
  def totalCopies(book: Book): Int =
    bookCopies.getOrElse(book, 0)

  // Get number of copies currently withdrawn for a book
  def withdrawnCopies(book: Book): Int =
    withdrawals.getOrElse(book, List.empty).size

  // Get number of available copies for withdrawal
  def availableCopies(book: Book): Int =
    totalCopies(book) - withdrawnCopies(book)

  // Search for books by title substring (case insensitive, min 3 non-whitespace chars)
  def searchByTitle(query: String): Either[LibraryError, Set[Book]] =
    validateSearchTerm(query).map { validQuery =>
      books.filter(_.title.toLowerCase.contains(validQuery.toLowerCase))
    }

  // Search for books by author substring (case insensitive, min 3 non-whitespace chars)
  def searchByAuthor(query: String): Either[LibraryError, Set[Book]] =
    validateSearchTerm(query).map { validQuery =>
      books.filter(_.author.toLowerCase.contains(validQuery.toLowerCase))
    }

  // Search for books by ISBN substring (ignores non-digits, min 3 digits)
  def searchByIsbn(query: String): Either[LibraryError, Set[Book]] =
    val queryDigits = query.filter(_.isDigit)
    if queryDigits.length < 3 then
      Left(SearchTermTooShort(3))
    else
      Right(books.filter { book =>
        val bookDigits = book.isbn.filter(_.isDigit)
        bookDigits.contains(queryDigits)
      })

  // Validate search term (min 3 non-whitespace chars)
  private def validateSearchTerm(query: String): Either[LibraryError, String] =
    val nonWhitespace = query.filterNot(_.isWhitespace)
    if nonWhitespace.length < 3 then
      Left(SearchTermTooShort(3))
    else
      Right(query)

  // Add a member to the library
  def addMember(member: Member): Either[LibraryError, Library] =
    if members.contains(member) then
      Left(MemberAlreadyExists(member.name))
    else
      Right(copy(members = members + member))

  // Remove a member from the library
  def removeMember(member: Member): Either[LibraryError, Library] =
    if !members.contains(member) then
      Left(MemberNotFound(member.name))
    else
      Right(copy(members = members - member))

  // Find a member by name
  def findMemberByName(name: String): Option[Member] =
    members.find(_.name == name)

  // Get all members
  def allMembers: Set[Member] = members

  // Withdraw a book for a member
  def withdrawBook(member: Member, book: Book): Either[LibraryError, Library] =
    if !members.contains(member) then
      Left(MemberNotFound(member.name))
    else if !bookCopies.contains(book) then
      Left(BookNotFound(book.isbn))
    else if availableCopies(book) <= 0 then
      Left(BookNotAvailable(book.isbn))
    else if withdrawals.getOrElse(book, List.empty).contains(member) then
      Left(BookAlreadyWithdrawnByMember(book.isbn, member.name))
    else
      val currentWithdrawals = withdrawals.getOrElse(book, List.empty)
      Right(copy(withdrawals = withdrawals.updated(book, member :: currentWithdrawals)))

  // Return a book from a member
  def returnBook(member: Member, book: Book): Either[LibraryError, Library] =
    if !members.contains(member) then
      Left(MemberNotFound(member.name))
    else
      val bookWithdrawals = withdrawals.getOrElse(book, List.empty)
      if !bookWithdrawals.contains(member) then
        Left(BookNotWithdrawnByMember(book.isbn, member.name))
      else
        val updatedWithdrawals = bookWithdrawals.filterNot(_ == member)
        Right(copy(withdrawals =
          if updatedWithdrawals.isEmpty then withdrawals - book
          else withdrawals.updated(book, updatedWithdrawals)
        ))

  // Get books withdrawn by a member
  def booksWithdrawnBy(member: Member): Set[Book] =
    withdrawals.collect {
      case (book, members) if members.contains(member) => book
    }.toSet

object Library:
  // Create an empty library
  def empty: Library = Library(Map.empty, Set.empty, Map.empty)

  // Create a library with initial books (one copy each)
  def apply(books: Book*): Library =
    books.foldLeft(empty)((lib, book) => lib.addBook(book))
