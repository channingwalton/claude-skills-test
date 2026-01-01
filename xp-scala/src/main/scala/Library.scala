case class Library(books: List[Book]):
  def addBook(book: Book): Either[LibraryError, Library] =
    if books.exists(_.isbn == book.isbn) then
      Left(LibraryError.DuplicateIsbn(book.isbn))
    else
      Right(Library(books :+ book))

object Library:
  def empty: Library = Library(List.empty)

enum LibraryError:
  case DuplicateIsbn(isbn: String)
