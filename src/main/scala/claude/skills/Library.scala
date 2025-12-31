package claude.skills

import scala.collection.mutable.ListBuffer

class Library:
  private val books: ListBuffer[Book] = ListBuffer.empty

  def addBook(book: Book): Unit =
    books += book

  def getBooks: List[Book] =
    books.toList
