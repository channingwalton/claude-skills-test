case class Library(books: List[Book])

object Library:
  def empty: Library = Library(List.empty)
