# Project Report

## Introduction

The digital logic simulator project aims to create a user-friendly, interactive environment for building and testing digital circuits.

## Background

Logisim, a key reference for this project, offered valuable lessons in user experience and functionality. The goal of the project is to create a simpler but more intuitive design compared to Logisim. Although Logisim is very feature-rich and has a great drag-and-drop interface, some features like the wiring can be hard to use. In addition, the overwhelming number of options on the lefthand side can make it less intuitive when trying to create a very simple digital logic circuit design.

In contrast, this project is very simple and intuitive, with almost every option available to the user visible and easy to understand at a glance at the moment of starting up the program.

## Methodology

The project adopted an agile development methodology, with iterative testing and refinement. Java Swing was used for the graphical user interface, ensuring cross-platform compatibility and ease of use.

First, goals were brainstormed and set for each week to align with each of the weekly ECS160 project submissions.

## Implementation Details

The simulator is being implemented in phases, starting with a large-scale skeleton overview of the project. Each item is being added in incremental steps. For example, the grid was first being created statically without zooming and was expanded to include this feature. Likewise for many of the components in the project like the `Logic Gates`, first one gate like the `AndGate` was worked on and then each of the remaining gates were implemented afterwards based on the first gate.

## Testing and Evaluation

Testing was done incrementally, parallel to the development of the project. As features were added one-by-one, the implementation of each part was tested by running the program or by using debug print statements.

## Results and Discussion

The first week took a lot of trial and error learning about Java Swing, brainstorming the architecture of the project, and working with ChatGPT effectively. Most of the work done was experimenting with drawing objects and the interface of the program.

In the second week, much of the project was rewritten to manually handle the transition of drag and drop objects from the `PalettePanel` into the `GridPanel` instead of using the `TransferHandler` interface and multiple JPanels in a Border style layout. This allowed for smoother transitions but required the creation of a `MainPanel` class that interfaced between the `PalettePanel` and `GridPanel`.

In the third week, the gate display was updated to use images instead of drawing using Java Swing graphics. In addition, resizing functionality was added to the Grid.

In the final week, it became clear that there needed to be multiple mouse modes to handle moving components versus creating wires and deleting components.

## Conclusion

Since this was the first GUI project I had ever undertaken, I had to undergo a lot of experimentation to see what worked in terms of user interface (UI) and user experience (UX).

If the project were to be developed further, the first large feature to be added would be panning the grid. In addition, it would be useful to have a `Select` mode and have the functionality to select multiple components and wires and move them all at once. There are a lot of buggy parts of the program such as when right-click dragging objects that could also be ironed out.

## References and Appendices

### ChatGPT

This was my first time working with ChatGPT on a larger project. At the beginning of the project, I relied more on ChatGPT to come up with broader ideas and code to set up the project. At the start of the project, I asked about typical Java Swing project structures and how to set up a GUI, as can be seen in `/ChatGPT/02-22.pdf` and `/ChatGPT/03-01.pdf`.

However, it soon became apparent that ChatGPT was best utilized to solve small-scale issues, like a more-specifically tailored search-engine. At the tail-end of the project, I asked more well-defined questions with clear answers, like whether or not a Java `HashSet` could hold `Point` objects and correcly do `.equals()` comparisons between objects when calling `HashSet.contains()`. This can be seen in `/ChatGPT/03-21.html`.

### Other references used in the project

- Logisim software
- Java Swing documentation
- Logic Gate images taken from https://www.categories.acsl.org/wiki/index.php?title=Digital_Electronics
- Mouse button image taken from https://en.m.wikipedia.org/wiki/File:Mouse-cursor-hand-pointer.svg
- Add button image taken from https://www.cleanpng.com/png-plus-and-minus-signs-clip-art-3214188/
- Images made transparent using https://www.remove.bg/
