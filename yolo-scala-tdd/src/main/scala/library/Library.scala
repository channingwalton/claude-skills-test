package library

import scala.collection.mutable.ListBuffer

class Library:
  private val _books: ListBuffer[Book] = ListBuffer.empty

  def addBook(book: Book): Unit = _books += book

  def books: Seq[Book] = _books.toSeq
