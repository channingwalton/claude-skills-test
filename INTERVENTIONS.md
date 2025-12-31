# Intervention Log

## Summary

| Category | Count |
|----------|-------|
| Correction | 6 |
| Clarification | 4 |
| Redirection | |
| Reminder | |
| Approval | 6 |
| **Total** | |

## Intervention Details

### First Task - mutable collections

Instruction:

- Create a Library that contains books.
- A book has a title, author, and ISBN.
- Use the package `claude.skills`

Intervention:

- **Category**: Clarification
- **Issue**: Claude used mutable collections in the Library

Clarification:

- Do not use mutable collections in scala FP projects

Outcome:

- Claude correctly replaced the mutable collection

### First task - no tests

Clarification:

- Please write tests

Outcome:

Five tests written, they seem reasonable.

### Second task - members

Correction: MemberSuite contains tests for Library, not Members.

Outcome: Instead of adding the tests into LibrarySuite, Claude renamed MemberSuite to LibraryMemberSuite!

Correction: The tests should have been added to LibrarySuite, not a new Suite

Outcome: Tests were moved into LibrarySuite and the LibraryMemberSuite deleted

### Third task - withdrawing books returning an error if a book cannot be withdrawn

This time tests were written, maybe because I haven't cleared the context.

Clarification: Instead of returning an Option[Library], `withdraw` should return `Either[BookUnavailble, Library]`

Outcome: Success

### Third task - withdrawing the same book twice is a different kind of error

Clarification: If a book is withdrawn twice by the same member, `withdraw` should return Left(AlreadyWithDrawn)

Outcome: Success

### Multi copies - non-members withdrawal

Correction:  a non-member can attempt to withdraw a book and get BookUnavailable rather than a MemberNotFound error

### Multi copies - non-members return

Correction:  a non-member can attempt to return a book

### Multi copies - non-members return error type

Correction:  ReturningMemberNotFound is unnecessary, just use MemberNotFound

### Multi copies - confused error types

Correction: MemberNotFound doesn't need to extend WithdrawError or ReturnError, its neither of those things.

## Observations

- Claude did not write tests unless I told it to.
- Claude used mutable collections by default - this is Scala!
- Claude put tests in a weird place.
- I am making clarifications which I'll add to the main project. Its tricky to write generic requirements for any language.
