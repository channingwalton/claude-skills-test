case class Library(books: List[Book]):
  def addBook(book: Book): Either[LibraryError, Library] =
    if books.exists(_.isbn == book.isbn) then
      Left(LibraryError.DuplicateIsbn(book.isbn))
    else
      Right(Library(books :+ book))

  def searchByTitle(query: String): Either[LibraryError, List[Book]] =
    val nonWhitespaceCount = query.filterNot(_.isWhitespace).length
    if nonWhitespaceCount < 3 then
      Left(LibraryError.QueryTooShort)
    else
      val lowerQuery = query.toLowerCase
      Right(books.filter(_.title.toLowerCase.contains(lowerQuery)))

object Library:
  def empty: Library = Library(List.empty)

enum LibraryError:
  case DuplicateIsbn(isbn: String)
  case QueryTooShort
