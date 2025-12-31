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

### First Task

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

## Observations

- Claude did not write tests unless I told it to.
- Claude used mutable collections by default - this is Scala!
