# Intervention Log

## Summary

| Category | Count |
|----------|-------|
| Correction | 1 |
| Clarification | 2 |
| Redirection | |
| Reminder | |
| Approval | 2 |
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

## Observations

- Claude did not write tests unless I told it to.
- Claude used mutable collections by default - this is Scala!
