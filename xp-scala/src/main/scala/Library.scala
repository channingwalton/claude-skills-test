case class Book(title: String, author: String, isbn: String)

enum LibraryError:
  case InvalidISBN

case class Library(books: List[Book]):
  def addBook(book: Book): Either[LibraryError, Library] =
    if book.isbn.isBlank then Left(LibraryError.InvalidISBN)
    else if books.exists(_.isbn == book.isbn) then Right(this)
    else Right(Library(books :+ book))

object Library:
  def empty: Library = Library(List.empty)
