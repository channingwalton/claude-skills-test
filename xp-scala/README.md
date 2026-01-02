# Claude Code Skills and Agents Tests

This experiment uses Extreme Programming Skills combined with basic FP skills, implemented in Scala.

## Typical Claude Code Interaction with this SKILL set

```
> Implement FOO with XP skill

⏺ /xp

⏺ 📋 PLAN → Starting feature discussion

⏺ /planning

⏺ 💬 DISCUSS → Understanding "FOO"

  Requirements understood:
    - X
    - Y

  ❓ CLARIFY → One question:

  What should happen when blah?
  - Return an error (like InvalidBar)?
  - Return an empty list?

> Do X

⏺ ✂️ SLICE → Breaking into tasks

  Tasks for "FOO"

  1. lorem ipsum
  2. lorem ipsum
  3. lorem ipsum

  ---
  📋 CONFIRM → Does this breakdown look right?

  First task: lorem ipsum

> yes proceed

⏺ 🔴 DEVELOP → Starting TDD for "Search returns matching books"

⏺ 🔴 RED → Writing failing test for search

⏺ Update(src/test/scala/LibrarySpec.scala)

[code]

⏺ Bash(sbt test 2>&1)
  ⎿  Error: Exit code 1

⏺ 🔴 RED confirmed — value foo is not a member of Library

  🟢 GREEN → Writing minimum code to pass

⏺ Read(src/main/scala/Library.scala)
  ⎿  Read 14 lines

⏺ Update(src/main/scala/Library.scala)

⏺ Bash(sbt test 2>&1)
  ⎿  [info] welcome to sbt 1.11.7 (Azul Systems, Inc. Java 25)
     [info] loading settings for project global-plugins from sbt-updates.sbt...
     [info] loading global plugins from /Users/channing/.sbt/1.0/plugins
     … +14 lines (ctrl+o to expand)

⏺ 🟢 GREEN — Test passes

  ✅ VERIFY → All tests green (4 passed)

  💾 COMMIT → Saving "Search returns matching books"

⏺ Bash(git ...)

  🔵 REFACTOR → Reviewing code after "Search returns matching books"

⏺ Read(src/main/scala/Library.scala)
  ⎿  Read 17 lines

⏺ 🔵 REFACTOR → Current code looks clean. Do you want to see any changes before we continue?

> No

⏺ 💾 COMMIT complete → 4503d45

  🔁 ITERATE → Moving to next task

```
