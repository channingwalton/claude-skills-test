package library

case class Library(books: List[Book]):
  def addBook(book: Book): Library =
    copy(books = books :+ book)

  def searchByTitle(query: String): Either[SearchError, List[Book]] =
    val nonWhitespaceCount = query.filterNot(_.isWhitespace).length
    if nonWhitespaceCount < 3 then
      Left(SearchError.QueryTooShort)
    else
      val lowerQuery = query.toLowerCase
      Right(books.filter(_.title.toLowerCase.contains(lowerQuery)))

object Library:
  val empty: Library = Library(books = List.empty)
