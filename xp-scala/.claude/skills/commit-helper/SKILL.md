---
name: Commit Helper
description: Generate clear conventional commit messages from git diffs. Use for the COMMIT phase of XP workflow.
---

# Commit Helper

## Quick Reference

**Invoke:** `/commit-helper`

## Process

1. Check git status for staged changes
2. Read staged diff
3. Check recent commits for style consistency
4. Analyse change type and scope
5. Generate conventional commit message
6. Create the commit

## Conventional Commit Format

```
<type>: <summary>

[optional body]
```

### Types

| Type | Use For |
|------|---------|
| `feat` | New feature |
| `fix` | Bug fix |
| `refactor` | Code restructuring (no behaviour change) |
| `test` | Adding/updating tests |
| `docs` | Documentation only |
| `chore` | Build, tooling, dependencies |

### Summary Rules

- Under 50 characters
- Imperative mood ("add" not "added")
- No period at end
- Lowercase

## Examples

**Simple:**
```
feat: add book withdrawal functionality
```

**With body:**
```
refactor: extract validation logic to separate module

Moved input validation from UserController to ValidationService
to improve testability and reuse across endpoints.
```

**After TDD cycle:**
```
feat: add member registration

- Member case class with id, name, email
- MemberService with register and find methods
- Validation for duplicate emails
```

## Rules

- **NEVER** commit files containing secrets
- **ALWAYS** run tests before committing
- **ALWAYS** ensure all tests pass
