---
name: XP
description: Extreme Programming workflow orchestrator. Use when implementing features. Coordinates planning, TDD, refactoring, and commits.
---

# Extreme Programming Workflow

## Overview

This skill orchestrates the full XP workflow for feature implementation. It coordinates sub-skills and ensures proper sequencing of phases.

## The XP Workflow

```
┌─────────────────────────────────────────────────────────────┐
│  📋 PLAN     → Discuss and break down the feature          │
│  🔴 DEVELOP  → TDD cycle (red-green)                       │
│  🔵 REFACTOR → Improve design (tests stay green)           │
│  💾 COMMIT   → Save working state                          │
│  🔁 ITERATE  → Next task or feature complete              │
└─────────────────────────────────────────────────────────────┘
```

## Phase 1: Planning (📋 PLAN)

**Goal:** Understand and decompose the feature before writing any code.

### Steps

1. **ALWAYS discuss requirements** with the user

- What problem does this feature solve?
- What is the expected behaviour?
- What are the acceptance criteria?

2. STOP

- [ ] Have you asked the user at least one clarifying question

3. **Break into vertical tasks**

- Each task delivers working functionality
- tasks are small enough to complete in one TDD cycle
- Order tasks by dependency and value

4. **STOP and Confirm understanding**

- Summarise back to the user
- Ask clarifying questions
- **ALWAYS** Agree on the first task to implement

---

## Phase 2: Development (🔴 DEVELOP)

**Goal:** Implement the task using strict TDD.

**Invoke:** Switch to `development` skill

---

## Phase 3: Refactoring (🔵 REFACTOR)

**Goal:** Improve code design while keeping tests green.

**Invoke:** Switch to `refactor` skill:

---

## Phase 4: Commit (💾 COMMIT)

**Goal:** Save working state with clear commit message.

**Invoke:** Switch to `commit-helper` skill

### Commit Points

- After each passing test
- After completing a task
- After refactoring session

---

## Phase 5: Iterate (🔁 ITERATE)

**Goal:** Continue until feature complete.

1. Mark task as done
2. Review remaining tasks
3. Adjust plan if needed (new learnings)
4. Return to Phase 2 for next task
5. When all tasks complete → feature done

---

## Workflow Diagram

```
          ┌──────────────────────────────────────────┐
          │                                          │
          ▼                                          │
     ┌────────┐                                      │
     │  PLAN  │ ← Discuss, break down, confirm       │
     └────┬───┘                                      │
          │                                          │
          ▼                                          │
     ┌────────┐                                      │
     │DEVELOP │ ← TDD: Red → Green → Verify          │
     └────┬───┘                                      │
          │                                          │
          ▼                                          │
    ┌──────────┐                                     │
    │ REFACTOR │ ← Improve design, tests green       │
    └────┬─────┘                                     │
          │                                          │
          ▼                                          │
     ┌────────┐                                      │
     │ COMMIT │ ← Save state                         │
     └────┬───┘                                      │
          │                                          │
          ▼                                          │
     ┌────────┐    More tasks?                      │
     │ITERATE │ ───────────────────────────────────►─┘
     └────┬───┘ 
          │ 
          ▼ Done
     ┌────────┐
     │  END   │
     └────────┘ 
```

## Announcing Phase Transitions

When switching phases, announce clearly:

```
📋 PLAN → Starting feature discussion
🔴 DEVELOP → Writing failing test for [task]
🟢 DEVELOP → Making test pass
🔵 REFACTOR → Improving [aspect]
💾 COMMIT → Saving [task] implementation
🔁 ITERATE → Moving to next task
✅ COMPLETE → Feature done
```

## Integration with Sub-Skills

| Phase | Skill | Agent |
|-------|-------|-------|
| PLAN | (inline) | — |
| DEVELOP | `development` | — |
| REFACTOR | `refactor` | — |
| COMMIT | `commit-helper` | `commit-helper` |

## Core Principles (Always Apply)

- **Communication first** — discuss before coding
- **Small steps** — one task, one test, one change at a time
- **Continuous feedback** — tests run constantly
- **Simplicity** — implement only what's needed now
- **Courage** — refactor fearlessly (tests protect you)
