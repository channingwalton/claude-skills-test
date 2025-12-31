# Requirements: A Library

## Overview

The goal is to create a model of a library that supports:

- stocking books and videos
- searching
- limiting the number of books and videos that members can check out at once
- find members with overdue books or videos

## Features

### Add books to the library

A book has a title, author, and ISBN.

### Members

- A member has a name
- The library has members
- Members can be removed

Tests:
- members can only be added once
- members can be found by name
- members can be removed
- the sum of members added and removed should be equal to the number of members

### Checkout books

- A member can check out a book.

Tests:
- a member can only check out a book once.
- A member's book list should contain the book they checked out.

### Return books

- A member can return a book.

Tests:
- A member can only return a book if they checked it out
- A member can only return a book once
- A member's book list should not contain the book they returned
