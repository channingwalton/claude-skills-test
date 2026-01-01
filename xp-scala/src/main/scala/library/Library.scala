package library

case class Library(books: List[Book] = List.empty):
  def addBook(book: Book): Library = Library(books :+ book)
