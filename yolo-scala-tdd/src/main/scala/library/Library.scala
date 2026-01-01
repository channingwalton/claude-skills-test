package library

import scala.collection.mutable.ListBuffer

class Library:
  private val _books: ListBuffer[Book] = ListBuffer.empty

  def addBook(book: Book): Unit = _books += book

  def books: Seq[Book] = _books.toSeq

  def searchByTitle(query: String): Seq[Book] =
    val trimmedQuery = query.replaceAll("\\s", "")
    if trimmedQuery.length < 3 then Seq.empty
    else _books.filter(_.title.toLowerCase.contains(query.trim.toLowerCase)).toSeq
