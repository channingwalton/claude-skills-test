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

  def searchByAuthor(query: String): Seq[Book] =
    val trimmedQuery = query.replaceAll("\\s", "")
    if trimmedQuery.length < 3 then Seq.empty
    else _books.filter(_.author.toLowerCase.contains(query.trim.toLowerCase)).toSeq

  def searchByIsbn(query: String): Seq[Book] =
    val digitsOnly = query.toLowerCase.replaceFirst("^isbn", "").replaceAll("[^0-9]", "")
    if digitsOnly.length < 3 then Seq.empty
    else _books.filter(_.isbn.replaceAll("[^0-9]", "").contains(digitsOnly)).toSeq
