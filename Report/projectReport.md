# Project Report

## Introduction

The digital logic simulator project aims to create a user-friendly, interactive environment for building and testing digital circuits. The primary goal is to address usability issues encountered in existing simulators, like Logisim, and incorporate their successful features such as a grid layout, panning, and zooming.

## Background

Logisim, a key reference for this project, offers valuable lessons in user experience and functionality. However, its wiring system presented significant usability challenges, prompting the development of this simulator. By improving the user interface and streamlining the wiring process, this project seeks to enhance the digital circuit design experience.

## Methodology

The project adopted an agile development methodology, with iterative testing and refinement. Java Swing was used for the graphical user interface, ensuring cross-platform compatibility and ease of use.

## Implementation Details

The simulator is being implemented in phases, starting with a large-scale skeleton overview of the project. Each item is being added in incremental steps. For example, the grid is first being created statically without zooming and panning, and will be expanded to include these features.

## Testing and Evaluation

Testing was conducted informally through debugging sessions.

## Results and Discussion

The first week took a lot of trial and error learning about Java Swing, brainstorming the architecture of the project, and working with ChatGPT effectively.
In the second week, much of the project was rewritten to manually handle the transition of drag and drop objects from the PalettePanel into the GridPanel, instead of using the TransferHandler interface and multiple JPanels in a Border style layout.
In the third week, the gate display was updated to use images instead of drawing using Java Swing graphics. In addition, resizing functionality was added to the Grid.

## Conclusion

## References and Appendices

- Logisim software
- Java Swing documentation
- ChatGPT
- Logic Gate images taken from https://www.categories.acsl.org/wiki/index.php?title=Digital_Electronics
- Mouse button image taken from https://en.m.wikipedia.org/wiki/File:Mouse-cursor-hand-pointer.svg
- Add button image taken from https://www.cleanpng.com/png-plus-and-minus-signs-clip-art-3214188/