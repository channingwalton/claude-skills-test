# Log

Corrections: 2

## Observations

- Planning phase initiated at the beginning of the task
  - First explored the codebase which had an empty scala 3 project ready
  - Asked good clarifying questions
    1. Adding multiple books at once: Should adding a book be one-at-a-time, or do you also need to add multiple books in a single operation?
    - One at a time
    2. Duplicate handling: What should happen if someone tries to add a book with an ISBN that already exists in the library? (Ignore it? Return an error? Replace it?)
    - Ignore duplicates (I know what's coming)
    3. Validation: Should we validate ISBN format, or is any non-empty string acceptable for now?
    - Validate that the ISBN is not blank
  - Tasks of first task look ok
    1. Add a book to an empty library — Given an empty library, when I add a valid book, then the library contains that book
    2. Reject book with blank ISBN — Given any library, when I add a book with blank ISBN, then it returns an error and library is unchanged
    3. Ignore duplicate ISBN — Given a library with a book, when I add another book with the same ISBN, then the library remains unchanged (no duplicate added)
  - Claude asked me to confirm the tasks
  - Start develop phase (TDD)
    - Wrote failing test
    - Confirmed test failed to compile
    - Wrote minimal code to make test pass
    - All tests pass
  - On to refactor but did not commit first!
    - After correction Claude committed
  - Iterating on next task
- Had to correct a test: `assert(result.isLeft)` instead of proper assertion
- Correctly remembered to commit after my first correction, but I don't think it would remember in a different session
- It sometimes forgot a step (like refactor)
