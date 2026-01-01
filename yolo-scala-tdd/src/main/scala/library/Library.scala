package library

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Set
import scala.collection.mutable.Map

class Library:
  private val _books: ListBuffer[Book] = ListBuffer.empty
  private val _members: Set[Member] = Set.empty
  private val _withdrawals: ListBuffer[(Book, Member)] = ListBuffer.empty

  def addBook(book: Book): Unit = _books += book

  def books: Seq[Book] = _books.toSeq

  def searchByTitle(query: String): Seq[Book] =
    val trimmedQuery = query.replaceAll("\\s", "")
    if trimmedQuery.length < 3 then Seq.empty
    else _books.filter(_.title.toLowerCase.contains(query.trim.toLowerCase)).toSeq

  def searchByAuthor(query: String): Seq[Book] =
    val trimmedQuery = query.replaceAll("\\s", "")
    if trimmedQuery.length < 3 then Seq.empty
    else _books.filter(_.author.toLowerCase.contains(query.trim.toLowerCase)).toSeq

  def searchByIsbn(query: String): Seq[Book] =
    val digitsOnly = query.toLowerCase.replaceFirst("^isbn", "").replaceAll("[^0-9]", "")
    if digitsOnly.length < 3 then Seq.empty
    else _books.filter(_.isbn.replaceAll("[^0-9]", "").contains(digitsOnly)).toSeq

  def addMember(member: Member): Unit = _members += member

  def members: Seq[Member] = _members.toSeq

  def findMemberByName(name: String): Option[Member] =
    _members.find(_.name == name)

  def removeMember(member: Member): Unit = _members -= member

  def withdrawBook(member: Member, book: Book): Boolean =
    if getAvailableCopyCount(book) <= 0 then false
    else if _withdrawals.exists(_ == (book, member)) then false
    else
      _withdrawals += ((book, member))
      true

  def getBooksForMember(member: Member): Seq[Book] =
    _withdrawals.filter(_._2 == member).map(_._1).toSeq

  def returnBook(member: Member, book: Book): Boolean =
    val entry = (book, member)
    if _withdrawals.contains(entry) then
      _withdrawals -= entry
      true
    else false

  def getCopyCount(book: Book): Int =
    _books.count(_ == book)

  def getAvailableCopyCount(book: Book): Int =
    getCopyCount(book) - _withdrawals.count(_._1 == book)
