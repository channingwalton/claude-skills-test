package claude.skills

sealed trait WithdrawError
case class BookUnavailable(book: Book) extends WithdrawError
case class AlreadyWithdrawn(book: Book) extends WithdrawError

sealed trait ReturnError
case class NotWithdrawnByMember(book: Book, member: Member) extends ReturnError

case class Library(
    books: List[Book] = List.empty,
    members: Set[Member] = Set.empty,
    withdrawnBooks: Map[Book, Member] = Map.empty
):
  def addBook(book: Book): Library =
    copy(books = books :+ book)

  def addMember(member: Member): Library =
    copy(members = members + member)

  def removeMember(member: Member): Library =
    copy(members = members - member)

  def findMemberByName(name: String): Option[Member] =
    members.find(_.name == name)

  def withdraw(member: Member, book: Book): Either[WithdrawError, Library] =
    withdrawnBooks.get(book) match
      case Some(m) if m == member => Left(AlreadyWithdrawn(book))
      case Some(_)                => Left(BookUnavailable(book))
      case None                   => Right(copy(withdrawnBooks = withdrawnBooks + (book -> member)))

  def booksForMember(member: Member): List[Book] =
    withdrawnBooks.collect { case (book, m) if m == member => book }.toList

  def returnBook(member: Member, book: Book): Either[ReturnError, Library] =
    withdrawnBooks.get(book) match
      case Some(m) if m == member => Right(copy(withdrawnBooks = withdrawnBooks - book))
      case _                      => Left(NotWithdrawnByMember(book, member))
