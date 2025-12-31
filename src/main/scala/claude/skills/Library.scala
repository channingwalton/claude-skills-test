package claude.skills

case class BookUnavailable(book: Book)

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

  def withdraw(member: Member, book: Book): Either[BookUnavailable, Library] =
    if withdrawnBooks.contains(book) then Left(BookUnavailable(book))
    else Right(copy(withdrawnBooks = withdrawnBooks + (book -> member)))

  def booksForMember(member: Member): List[Book] =
    withdrawnBooks.collect { case (book, m) if m == member => book }.toList
