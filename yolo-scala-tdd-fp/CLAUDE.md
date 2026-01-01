# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Principles

- **ALWAYS** write a unit test before implementing production code
- **ALWAYS** see the test fail before writing production code to satisfy it
- **NEVER** write more code than is necessary to make the tests pass
- **ALWAYS** commit code after tests pass
- **ALWAYS** ensure that all tests are passing before committing

### Functional Programming

- **ALWAYS** use immutable data structures
- **ALWAYS** use Algebraic Data Types (ADTs)
- **ALWAYS** use referentially transparent functions
- **DO NOT** use exceptions
- **ALWAYS** use types like `Either` for returning errors
- **NEVER** use `null`
