package library

case class Library(books: List[Book], members: List[Member] = List.empty):
  def addBook(book: Book): Library =
    copy(books = books :+ book)

  def searchByTitle(query: String): Either[SearchError, List[Book]] =
    searchBy(query, _.title)

  def searchByAuthor(query: String): Either[SearchError, List[Book]] =
    searchBy(query, _.author)

  def searchByIsbn(query: String): Either[SearchError, List[Book]] =
    val normalizedQuery = query
      .toUpperCase
      .replace("ISBN", "")
      .filter(_.isDigit)
    if normalizedQuery.length < 3 then
      Left(SearchError.QueryTooShort)
    else
      Right(books.filter(book => book.isbn.filter(_.isDigit).contains(normalizedQuery)))

  private def searchBy(query: String, field: Book => String): Either[SearchError, List[Book]] =
    val nonWhitespaceCount = query.filterNot(_.isWhitespace).length
    if nonWhitespaceCount < 3 then
      Left(SearchError.QueryTooShort)
    else
      val lowerQuery = query.toLowerCase
      Right(books.filter(book => field(book).toLowerCase.contains(lowerQuery)))

  def addMember(member: Member): Either[MemberError, Library] =
    if members.contains(member) then
      Left(MemberError.MemberAlreadyExists)
    else
      Right(copy(members = members :+ member))

  def removeMember(member: Member): Either[MemberError, Library] =
    if !members.contains(member) then
      Left(MemberError.MemberNotFound)
    else
      Right(copy(members = members.filterNot(_ == member)))

  def findMemberByName(name: String): Option[Member] =
    members.find(_.name == name)

object Library:
  val empty: Library = Library(books = List.empty)
