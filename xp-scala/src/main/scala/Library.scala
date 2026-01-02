case class Book(title: String, author: String, isbn: String)

case class Library(books: List[Book]):
  def addBook(book: Book): Library = Library(books :+ book)

object Library:
  def empty: Library = Library(List.empty)
