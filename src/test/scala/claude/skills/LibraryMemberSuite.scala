package claude.skills

class LibraryMemberSuite extends munit.FunSuite:

  test("members can only be added once"):
    val member = Member("Alice")
    val library = Library()
      .addMember(member)
      .addMember(member)
    assertEquals(library.members.size, 1)

  test("members can be found by name"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library()
      .addMember(alice)
      .addMember(bob)
    assertEquals(library.findMemberByName("Alice"), Some(alice))
    assertEquals(library.findMemberByName("Charlie"), None)

  test("members can be removed"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val library = Library()
      .addMember(alice)
      .addMember(bob)
      .removeMember(alice)
    assertEquals(library.members, Set(bob))

  test("the sum of members added and removed should be equal to the number of members"):
    val alice = Member("Alice")
    val bob = Member("Bob")
    val charlie = Member("Charlie")
    val added = Set(alice, bob, charlie)
    val removed = Set(alice)
    val library = Library()
      .addMember(alice)
      .addMember(bob)
      .addMember(charlie)
      .removeMember(alice)
    assertEquals(library.members.size, added.size - removed.size)
    assertEquals(library.members, added -- removed)
