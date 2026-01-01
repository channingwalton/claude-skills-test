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
<type>: <summary under 50 characters>

[optional body]
```

## Rules

- **NEVER** commit files containing secrets
