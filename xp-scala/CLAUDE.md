# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Extreme Programming Principles

### Planning & Discussion

- **ALWAYS** discuss the feature requirements before writing any code
- **ALWAYS** clarify acceptance criteria and expected behaviour upfront
- **ALWAYS** break features into small, incremental steps (user stories)
- **ALWAYS** implement one small step at a time before moving to the next
- **ALWAYS** confirm understanding of each step before implementation

### Test-Driven Development (TDD)

- **ALWAYS** write a unit test before implementing production code
- **ALWAYS** see the test fail before writing production code to satisfy it
- **NEVER** write more code than is necessary to make the tests pass
- **ALWAYS** follow the Red-Green-Refactor cycle strictly

### Simple Design & Refactoring

- **ALWAYS** implement the simplest solution that makes the test pass
- **ALWAYS** refactor after tests pass to improve code quality
- **NEVER** add functionality "just in case" — follow YAGNI (You Aren't Gonna Need It)
- **ALWAYS** remove duplication during refactoring

### Continuous Integration

- **ALWAYS** commit code after tests pass
- **ALWAYS** ensure that all tests are passing before committing
- **ALWAYS** keep commits small and focused on a single change

### Functional Programming Principles

- **ALWAYS** use immutable data structures
- **ALWAYS** use Algebraic Data Types (ADTs)
- **ALWAYS** use referentially transparent functions
- **DO NOT** use exceptions
- **ALWAYS** use types like `Either` for returning errors
- **NEVER** use `null`
