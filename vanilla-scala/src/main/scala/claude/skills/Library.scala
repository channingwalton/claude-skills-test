package claude.skills

sealed trait LibraryError

case class MemberNotFound(member: Member) extends LibraryError

sealed trait WithdrawError extends LibraryError
case class BookUnavailable(book: Book) extends WithdrawError
case class AlreadyWithdrawn(book: Book) extends WithdrawError

sealed trait ReturnError extends LibraryError
case class NotWithdrawnByMember(book: Book, member: Member) extends ReturnError

sealed trait SearchError extends LibraryError
case class SearchQueryTooShort(query: String, minLength: Int) extends SearchError

case class Library(
    books: Map[Book, Int] = Map.empty,
    members: Set[Member] = Set.empty,
    withdrawnBooks: Map[Book, Set[Member]] = Map.empty
):
  def addBook(book: Book): Library =
    copy(books = books + (book -> (books.getOrElse(book, 0) + 1)))

  def addMember(member: Member): Library =
    copy(members = members + member)

  def removeMember(member: Member): Library =
    copy(members = members - member)

  def findMemberByName(name: String): Option[Member] =
    members.find(_.name == name)

  def copyCount(book: Book): Int =
    books.getOrElse(book, 0)

  def availableCopies(book: Book): Int =
    copyCount(book) - withdrawnBooks.getOrElse(book, Set.empty).size

  def withdraw(member: Member, book: Book): Either[LibraryError, Library] =
    val currentHolders = withdrawnBooks.getOrElse(book, Set.empty)
    if !members.contains(member) then Left(MemberNotFound(member))
    else if currentHolders.contains(member) then Left(AlreadyWithdrawn(book))
    else if availableCopies(book) <= 0 then Left(BookUnavailable(book))
    else Right(copy(withdrawnBooks = withdrawnBooks + (book -> (currentHolders + member))))

  def booksForMember(member: Member): List[Book] =
    withdrawnBooks.collect { case (book, members) if members.contains(member) => book }.toList

  def returnBook(member: Member, book: Book): Either[LibraryError, Library] =
    val currentHolders = withdrawnBooks.getOrElse(book, Set.empty)
    if !members.contains(member) then Left(MemberNotFound(member))
    else if currentHolders.contains(member) then
      val updatedHolders = currentHolders - member
      val updatedWithdrawn = if updatedHolders.isEmpty then withdrawnBooks - book else withdrawnBooks + (book -> updatedHolders)
      Right(copy(withdrawnBooks = updatedWithdrawn))
    else Left(NotWithdrawnByMember(book, member))

  private def searchBooks(query: String, field: Book => String): Either[SearchError, List[Book]] =
    val nonWhitespaceCount = query.filterNot(_.isWhitespace).length
    if nonWhitespaceCount < 3 then Left(SearchQueryTooShort(query, 3))
    else
      val lowerQuery = query.toLowerCase
      Right(books.keys.filter(book => field(book).toLowerCase.contains(lowerQuery)).toList)

  def searchByTitle(query: String): Either[SearchError, List[Book]] =
    searchBooks(query, _.title)

  def searchByAuthor(query: String): Either[SearchError, List[Book]] =
    searchBooks(query, _.author)

  def searchByIsbn(query: String): Either[SearchError, List[Book]] =
    val normalized = query.toLowerCase.stripPrefix("isbn").filter(_.isDigit)
    if normalized.length < 3 then Left(SearchQueryTooShort(query, 3))
    else Right(books.keys.filter(_.isbn.filter(_.isDigit).contains(normalized)).toList)
