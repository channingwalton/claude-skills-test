package claude.skills

case class Library(books: List[Book] = List.empty, members: Set[Member] = Set.empty):
  def addBook(book: Book): Library =
    copy(books = books :+ book)

  def addMember(member: Member): Library =
    copy(members = members + member)

  def removeMember(member: Member): Library =
    copy(members = members - member)

  def findMemberByName(name: String): Option[Member] =
    members.find(_.name == name)
