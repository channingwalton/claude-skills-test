package claude.skills

case class Library(books: List[Book] = List.empty):
  def addBook(book: Book): Library =
    copy(books = books :+ book)
