case class Book(title: String, author: String, isbn: String)

enum LibraryError:
  case InvalidISBN
  case InvalidSearchQuery

case class Library(books: List[Book]):
  def addBook(book: Book): Either[LibraryError, Library] =
    if book.isbn.isBlank then Left(LibraryError.InvalidISBN)
    else if books.exists(_.isbn == book.isbn) then Right(this)
    else Right(Library(books :+ book))

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
