package library

case class Library(books: List[Book]):
  def addBook(book: Book): Library =
    copy(books = books :+ book)

object Library:
  val empty: Library = Library(books = List.empty)
