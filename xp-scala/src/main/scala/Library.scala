case class Book(title: String, author: String, isbn: String)

case class Member(name: String)

enum LibraryError:
  case InvalidISBN
  case InvalidSearchQuery
  case NoSuchMember
  case BookUnavailable
  case BookAlreadyHeld
  case BookNotHeld

case class Library(
    copies: Map[Book, Int] = Map.empty,
    members: List[Member] = List.empty,
    withdrawals: Map[Book, List[Member]] = Map.empty
):
  def books: List[Book] = copies.keys.toList

  def addBook(book: Book): Either[LibraryError, Library] =
    if book.isbn.isBlank then Left(LibraryError.InvalidISBN)
    else
      val existingBook = copies.keys.find(_.isbn == book.isbn)
      existingBook match
        case Some(existing) =>
          val currentCount = copies(existing)
          Right(copy(copies = copies + (existing -> (currentCount + 1))))
        case None =>
          Right(copy(copies = copies + (book -> 1)))

  def copiesOf(book: Book): Int =
    copies.getOrElse(book, 0)

  def addMember(member: Member): Library =
    if members.exists(_.name == member.name) then this
    else copy(members = members :+ member)

  def findMemberByName(query: String): Either[LibraryError, List[Member]] =
    if query.filterNot(_.isWhitespace).length < 3 then Left(LibraryError.InvalidSearchQuery)
    else Right(members.filter(_.name.toLowerCase.contains(query.toLowerCase)))

  def removeMember(member: Member): Either[LibraryError, Library] =
    if !members.exists(_.name == member.name) then Left(LibraryError.NoSuchMember)
    else Right(copy(members = members.filterNot(_.name == member.name)))

  def withdraw(member: Member, book: Book): Either[LibraryError, Library] =
    val holders = withdrawals.getOrElse(book, List.empty)
    val availableCopies = copiesOf(book) - holders.length
    if holders.contains(member) then Left(LibraryError.BookAlreadyHeld)
    else if availableCopies <= 0 then Left(LibraryError.BookUnavailable)
    else Right(copy(withdrawals = withdrawals + (book -> (holders :+ member))))

  def booksFor(member: Member): List[Book] =
    withdrawals.collect { case (book, holders) if holders.contains(member) => book }.toList

  def returnBook(member: Member, book: Book): Either[LibraryError, Library] =
    val holders = withdrawals.getOrElse(book, List.empty)
    if holders.contains(member) then
      val remaining = holders.filterNot(_ == member)
      if remaining.isEmpty then Right(copy(withdrawals = withdrawals - book))
      else Right(copy(withdrawals = withdrawals + (book -> remaining)))
    else Left(LibraryError.BookNotHeld)

  def searchByTitle(query: String): Either[LibraryError, List[Book]] =
    search(query, _.title)

  def searchByAuthor(query: String): Either[LibraryError, List[Book]] =
    search(query, _.author)

  def searchByISBN(query: String): Either[LibraryError, List[Book]] =
    val normalizedQuery = query.filter(_.isDigit)
    if normalizedQuery.length < 3 then Left(LibraryError.InvalidSearchQuery)
    else Right(books.filter(_.isbn.filter(_.isDigit).contains(normalizedQuery)))

  private def search(query: String, field: Book => String): Either[LibraryError, List[Book]] =
    if query.filterNot(_.isWhitespace).length < 3 then Left(LibraryError.InvalidSearchQuery)
    else Right(books.filter(field(_).toLowerCase.contains(query.toLowerCase)))

object Library:
  def empty: Library = Library()
