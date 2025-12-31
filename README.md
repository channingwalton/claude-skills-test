# Claude Skills and Agents Test Framework

A small project for evaluating Claude Code skills and agents.

## Purpose

This project provides a repeatable test bed for measuring the effectiveness of different Claude Code skill and agent
configurations. The goal is to count **interventions** (corrections, clarifications, or redirections) needed to complete
the implementation.

## The Task: A Library for Books and Movies

Implement an in-memory model of a library that supports adding, removing, and searching for books and movies in any
language you like.

The features are described in [REQUIREMENTS.md](REQUIREMENTS.md).

## Contributing

`main` contains the template, add branches for experiments.

To ensure no contamination, your own Claude configuration should be temporarily put to one side (mv ~/.claude ~/.claude.bak),
and all [configuration](https://code.claude.com/docs/en/settings) for Claude should be added to your branch only.

Modify the [README.md](README.md) to describe the experiment and record your experiments in INTERVENTIONS.md and
submit a pull request.

You are free to work through the requirements in any way you like, but I've ordered the features to force some
rework to see how Claude copes with that.

## Intervention Categories

| Category          | Description                            |
|-------------------|----------------------------------------|
| **Correction**    | Fixing incorrect code or approach      |
| **Clarification** | Answering questions about requirements |
| **Redirection**   | Getting back on track after tangent    |
| **Reminder**      | Prompting to follow skill guidelines   |
| **Approval**      | Confirming a proposed approach         |

## Success Criteria

- Fully tested
- All tests pass
- Conventional commits with meaningful messages
