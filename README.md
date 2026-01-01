# Claude Code Skills and Agents Tests

Do your Claude Code skills and agents actually work?

## Purpose

This project provides a repeatable test bed for measuring the effectiveness of different Claude Code skill and agent
configurations. The goal is to count the corrections needed to complete the implementation.

The [REQUIREMENTS](./REQUIREMENTS.md) are from an interview pairing exercise I used to use. The intent
with a human developer was to have a conversation and see where things went, not to produce
my idea of a perfect solution.

So the features are fairly vague but that is kind of the point, lets see where your solution goes.

### Important

Let Claude Code do all the coding. Although that isn't realistic in normal development, the aim is to
see where you and Claude Code take it, how many corrections you had to make to achieve the vision,
and whether your SKILLS and Agents made any difference.

## The Task: A Book Library

Implement an in-memory model of a library that supports adding, removing,
searching, withdrawing, and returning books, in any language you like.

The features are described in [REQUIREMENTS.md](./REQUIREMENTS.md).

## Contributing

Add a module for your experiment.

To ensure no contamination, your own Claude configuration should be temporarily
put to one side:

```shell
mv ~/.claude ~/.claude.bak
mv ~/.claude.json ~/.claude.json.bak
```

All [configuration](https://code.claude.com/docs/en/settings) for Claude should be added to your module only.

Add a `README.md` to your project to describe the experiment, record your experiment in an `LOG.md` doc.
You can add any observations there too.

Add to the summary table below in this document.

You are free to work through the requirements in any way you like, but I've ordered the features to force some
rework to see how Claude copes with that.

## Experimental summary

| Requirements version | Location | Description | Corrections | Language | Log | Model |
|----------------------|--------------|-------------------|-------------|----------|-------------------|-----|
| 2 | [vanilla-scala](/vanilla-scala/README.md) | No Claude config, feature by feature | 9 | Scala 3 | [Log](/vanilla-scala/LOG.md) | Opus 4.5 |
| 2 | [yolo-scala](/yolo-scala/README.md) | No Claude config, one-shot | N/A | Scala 3 | [Log](/yolo-scala/LOG.md) | Opus 4.5 |
| 2 | [yolo-scala-fp](./yolo-scala-fp/README.md) | Basic [CLAUDE.md](./yolo-scala-fp/CLAUDE.md) for functional programming, one-shot | N/A | Scala 3 | [Log](./yolo-scala-fp/LOG.md) | Opus 4.5 |
| 2 | [yolo-scala-tdd](./yolo-scala-tdd/README.md) | Basic [CLAUDE.md](./yolo-scala-tdd/CLAUDE.md) for Test Driven Development, one-shot | N/A | Scala 3 | [Log](./yolo-scala-tdd/LOG.md) | Opus 4.5 |
| 2 | [yolo-scala-tdd-fp](./yolo-scala-tdd-fp/README.md) | Basic [CLAUDE.md](./yolo-scala-tdd-fp/CLAUDE.md) for Test Driven Development with Functional Programming, one-shot | N/A | Scala 3 | [Log](./yolo-scala-tdd-fp/LOG.md) | Opus 4.5 |
