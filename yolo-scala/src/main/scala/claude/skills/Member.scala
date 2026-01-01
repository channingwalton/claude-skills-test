package claude.skills

case class Member(name: String):
  private var _borrowedBooks: Set[Book] = Set.empty

  def borrowedBooks: Set[Book] = _borrowedBooks

  private[skills] def addBorrowedBook(book: Book): Unit =
    _borrowedBooks = _borrowedBooks + book

  private[skills] def removeBorrowedBook(book: Book): Unit =
    _borrowedBooks = _borrowedBooks - book

  def hasBorrowed(book: Book): Boolean =
    _borrowedBooks.contains(book)
