package library

import munit.FunSuite

class MemberTest extends FunSuite:

  test("Member has a name"):
    val member = Member(name = "Alice")

    assertEquals(member.name, "Alice")

  test("Library can have members"):
    val member = Member(name = "Alice")
    val result = Library.empty.addMember(member)

    val library = result.getOrElse(Library.empty)
    assertEquals(library.members.size, 1)
    assertEquals(library.members.contains(member), true)

  test("Members can only be added once"):
    val member = Member(name = "Alice")
    val library = Library.empty

    val result1 = library.addMember(member)
    val result2 = result1.flatMap(_.addMember(member))

    assertEquals(result2, Left(MemberError.MemberAlreadyExists))

  test("Members can be found by name"):
    val alice = Member(name = "Alice")
    val bob = Member(name = "Bob")
    val library = Library.empty
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)

    val result = library.findMemberByName("Alice")

    assertEquals(result, Some(alice))

  test("Find member by name returns None when not found"):
    val library = Library.empty

    val result = library.findMemberByName("Alice")

    assertEquals(result, None)

  test("Members can be removed"):
    val alice = Member(name = "Alice")
    val library = Library.empty
      .addMember(alice).getOrElse(Library.empty)

    val result = library.removeMember(alice)

    assertEquals(result.map(_.members.size), Right(0))

  test("Removing non-existent member fails"):
    val alice = Member(name = "Alice")
    val library = Library.empty

    val result = library.removeMember(alice)

    assertEquals(result, Left(MemberError.MemberNotFound))

  test("Members added minus members removed equals current members"):
    val alice = Member(name = "Alice")
    val bob = Member(name = "Bob")
    val charlie = Member(name = "Charlie")

    val library = Library.empty
      .addMember(alice).getOrElse(Library.empty)
      .addMember(bob).getOrElse(Library.empty)
      .addMember(charlie).getOrElse(Library.empty)
      .removeMember(bob).getOrElse(Library.empty)

    assertEquals(library.members.size, 2)
    assertEquals(library.members.contains(alice), true)
    assertEquals(library.members.contains(charlie), true)
    assertEquals(library.members.contains(bob), false)
