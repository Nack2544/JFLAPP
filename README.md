# Finite Automaton Generator (JFLAPP)

A Java tool that reads string language definitions from input files, builds a Trie-based finite automaton, and generates JFLAP-compatible JFF output files for automata visualization.

## Overview

This project was developed as part of a Theory of Computation course at the University of North Florida (UNF). It demonstrates core concepts in formal language theory including finite automata, Trie data structures, and deterministic state machines.

## How It Works

1. Reads an input `.txt` file containing strings that define the language
2. Constructs a Trie data structure where each node represents a state
3. Marks final/accepting states based on valid strings in the language
4. Outputs a `.jff` file compatible with JFLAP for automata visualization

## Usage

```bash
java Project1 <input.txt> <output.jff>
```

Or run interactively:
```bash
java Project1
# Enter the .txt file name: L1
# Enter the .jff file name: output
```

## Example

Input file (`L1.txt`):
```
ab
abc
abcd
```

Output: A `.jff` file that can be opened in JFLAP to visualize the corresponding finite automaton.

## Technologies

- Java
- Trie data structure
- Formal Language Theory (DFA/NFA)
- JFLAP JFF XML format

## Project Structure

```
src/
  Project1.java   - Main class with Trie and automaton logic
ex.txt            - Example input file
exa.jff           - Example output JFF file
```
