# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Workflow

**For feature implementation, use the `/xp` skill.**

This invokes the Extreme Programming workflow: Plan → Develop → Refactor → Commit → Iterate.

## Functional Programming Principles

- **ALWAYS** use immutable data structures
- **ALWAYS** use Algebraic Data Types (ADTs)
- **ALWAYS** use referentially transparent functions
- **DO NOT** use exceptions
- **ALWAYS** use types like `Either` for returning errors
- **NEVER** use `null`
