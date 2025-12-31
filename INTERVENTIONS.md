# Intervention Log

## Summary

| Category | Count |
|----------|-------|
| Correction | |
| Clarification | 1 |
| Redirection | |
| Reminder | |
| Approval | |
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

## Observations

- Claude did not write tests unless I told it to.
- Claude used mutable collections by default - this is Scala!
