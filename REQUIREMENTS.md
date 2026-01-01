# Requirements

## Overview

The goal is to create a model of a book and video library that supports the features described
below. Since its just a model, no database or UI is required.

## Features version 1

### Project setup

Add anything here to you asked Claude Code to do to set up the project.

### Add books to the library

- Create a Library that contains a collection of Books.
- A book has a title, author, and ISBN.

Use the package `claude.skills`

### Add Members

Members have names, the library has members, members can be added and removed.

Tests:

- members can only be added once
- members can be found by name
- members can be removed
- the sum of members added and removed should be equal to the number of members

### Withdrawing books

A member can withdraw a book.

Tests:

- A member can only withdraw a book that hasn't already been withdrawn
- A member can only withdraw a book once
- A member's book list should contain the book they withdrew

### Return books

A member can return a book.

Tests:

- A member can only return a book if they withdrew it
- A member can only return a book once
- A member's book list should not contain the book they returned

### Multiple copies

Libraries contain multiple copies of a book

Tests:

- Add multiple copies to the library
- The library knows how many copies of a book it has
- Members can only withdraw a book if there is a copy available
- The number of available copies for withdrawal should account for withdrawals
