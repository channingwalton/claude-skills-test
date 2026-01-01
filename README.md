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

`main` contains the template, add experiments to new directories.

To ensure no contamination, your own Claude configuration should be temporarily put to one side:

```shell
mv ~/.claude ~/.claude.bak
mv ~/.claude.json ~/.claude.json.bak
```

All [configuration](https://code.claude.com/docs/en/settings) for Claude should be added to your module only.

Add a [README.md](README.md) to your project to describe the experiment, record your experiments in an INTERVENTIONS.md doc, and submit a pull request.

Finally, add to the summary table below.

You are free to work through the requirements in any way you like, but I've ordered the features to force some
rework to see how Claude copes with that.

## Success criteria

- Fully tested
- All tests pass
- Conventional commits with meaningful messages

## Experimental summary

| Requirements version | Location | Description | Corrections | Language | Interventions log |
|----------------------|--------------|-------------------|-------------|----------|-------------------|
| 1                    | [vanilla-scala](/vanilla-scala/README.md) | No Claude config | 7 | Scala 3 | [Interventions](/vanilla-scala/INTERVENTIONS.md) |
