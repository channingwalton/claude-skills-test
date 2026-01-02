case class Book(title: String, author: String, isbn: String)

case class Member(name: String)

enum LibraryError:
  case InvalidISBN
  case InvalidSearchQuery
  case NoSuchMember
  case BookUnavailable
  case BookAlreadyHeld

case class Library(
    books: List[Book],
    members: List[Member] = List.empty,
    withdrawals: Map[Book, Member] = Map.empty
):
  def addBook(book: Book): Either[LibraryError, Library] =
    if book.isbn.isBlank then Left(LibraryError.InvalidISBN)
    else if books.exists(_.isbn == book.isbn) then Right(this)
    else Right(copy(books = books :+ book))

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
    withdrawals.get(book) match
      case Some(holder) if holder == member => Left(LibraryError.BookAlreadyHeld)
      case Some(_)                          => Left(LibraryError.BookUnavailable)
      case None                             => Right(copy(withdrawals = withdrawals + (book -> member)))

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
  def empty: Library = Library(List.empty)
