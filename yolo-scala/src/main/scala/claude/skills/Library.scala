package claude.skills

import scala.collection.mutable

class Library:
  private val bookCopies: mutable.Map[Book, Int] = mutable.Map.empty
  private val availableCopies: mutable.Map[Book, Int] = mutable.Map.empty
  private val _members: mutable.Set[Member] = mutable.Set.empty
  private val memberBorrowedBooks: mutable.Map[Member, mutable.Set[Book]] = mutable.Map.empty

  // Book management
  def addBook(book: Book, copies: Int = 1): Unit =
    require(copies > 0, "Must add at least one copy")
    bookCopies(book) = bookCopies.getOrElse(book, 0) + copies
    availableCopies(book) = availableCopies.getOrElse(book, 0) + copies

  def books: Set[Book] = bookCopies.keySet.toSet

  def totalCopies(book: Book): Int = bookCopies.getOrElse(book, 0)

  def availableCopiesCount(book: Book): Int = availableCopies.getOrElse(book, 0)

  // Search functionality
  private val MinSearchLength = 3

  private def isValidSearchTerm(term: String): Boolean =
    term.filterNot(_.isWhitespace).length >= MinSearchLength

  private def normalizeIsbn(isbn: String): String =
    isbn.filter(_.isDigit)

  def searchByTitle(query: String): Set[Book] =
    if !isValidSearchTerm(query) then Set.empty
    else
      val lowerQuery = query.toLowerCase
      books.filter(_.title.toLowerCase.contains(lowerQuery))

  def searchByAuthor(query: String): Set[Book] =
    if !isValidSearchTerm(query) then Set.empty
    else
      val lowerQuery = query.toLowerCase
      books.filter(_.author.toLowerCase.contains(lowerQuery))

  def searchByIsbn(query: String): Set[Book] =
    val normalizedQuery = normalizeIsbn(query)
    if normalizedQuery.length < MinSearchLength then Set.empty
    else
      books.filter { book =>
        normalizeIsbn(book.isbn).contains(normalizedQuery)
      }

  // Member management
  def members: Set[Member] = _members.toSet

  def addMember(member: Member): Boolean =
    if _members.contains(member) then false
    else
      _members.add(member)
      memberBorrowedBooks(member) = mutable.Set.empty
      true

  def removeMember(member: Member): Boolean =
    if !_members.contains(member) then false
    else
      _members.remove(member)
      memberBorrowedBooks.remove(member)
      true

  def findMemberByName(name: String): Option[Member] =
    _members.find(_.name == name)

  // Borrowing functionality
  def withdrawBook(member: Member, book: Book): Boolean =
    if !_members.contains(member) then false
    else if !books.contains(book) then false
    else if availableCopiesCount(book) <= 0 then false
    else if memberBorrowedBooks.get(member).exists(_.contains(book)) then false
    else
      availableCopies(book) = availableCopies(book) - 1
      memberBorrowedBooks(member).add(book)
      member.addBorrowedBook(book)
      true

  def returnBook(member: Member, book: Book): Boolean =
    if !_members.contains(member) then false
    else if !memberBorrowedBooks.get(member).exists(_.contains(book)) then false
    else
      availableCopies(book) = availableCopies(book) + 1
      memberBorrowedBooks(member).remove(book)
      member.removeBorrowedBook(book)
      true
