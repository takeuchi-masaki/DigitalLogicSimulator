# Design Manual

## Architecture Overview

The application is structured around the main `MainPanel` class, which extends `JPanel`. It orchestrates the overall layout and interactions between the `PalettePanel` and `GridPanel`.

- `App`: Serves as the main entry point for the application.
- `MenuBar`: Contains menu items for `Save` and `Open` operations
- `MainPanel`: Handles mouse events and interactions between the PalettePanel and GridPanel
- `PalettePanel`: Displays useable LogicGates and information.
- `PaletteComponent`: Represents a draggable component in the `PalettePanel` with properties such as `logicGate` and `bounds`. It includes a `draw()` method for rendering.
- `GridPanel`: Handles grid components and wires using relative coordinate system
- `GridLogicHandler`: Handles the logic related to the grid, including interactions with logic gates and wires.
- `LogicGate`: An abstract class extended by specific gate types like `ANDGate`. It provides essential methods like `draw()`, `contains()`, and `output()` to handle logic gate functionalities.
- `WireComponent`: Has `start` and `end` points and can be drawn to the grid.

Each of these components works together to provide a comprehensive digital logic simulation environment.

## Design Patterns

### Singleton

The `PalettePanel`, `GridPanel`, `GridImporter`, and `GridExporter` all utilize the Singleton pattern.
There are no cases with the application that there would need to be more than a single version of these classes. For example when the `GridExporter` needs to export the current grid layout to an XML file, export is called on the single instance of `GridPanel`. The Singleton allows for easy interfacing and synchronization between the classes.

### Strategy

The `MouseModes` for the project made it possible to swap between the different ways to interact using the mouse quickly and easily. This is similar to the `Strategy` design pattern since the program can switch between different algorithms at runtime to interface with the program.

## Component Descriptions

### App

At the top of the architectural hierarchy is the App class. This initializes the window with MainPanel and MenuBar objects.

### MenuBar

The MenuBar contains commands for saving and loading circuits. There are also menu options for exiting the application, and menu options for an `About` page and `Documentation` page with a hyperlink.

### MainPanel

The MainPanel contains the logic for handling mouse operations (may be refactored out to a MouseHandler class). This class manages the interaction between the `PalettePanel` and `GridPanel`, such as drag and dropping new logic gate items from the `PalettePanel` into the `GridPanel`.

### PalettePanel

The `PalettePanel` displays `PaletteComponent`s which each house a `LogicGate` component. Each of the default gates are shown on the lefthand size of the application. When a component is hovered, the component colors the background of the component gray to make it clear to the user.

### GridPanel

The GridPanel displays a grid and the current view of the logic gates and wires. In future updates, a `GridLogicHandler` class will be added that calculates outcomes based on the circuit on the grid.
When dragging a component around the GridPanel, components snap to the grid.

### GridLogicHandler

Whenever some component or wire is added or removed from the grid, the `checkLogic()` function is called to check the validity of the circuit.

First, the components are checked for overlaps in which case the component is highlighted in red and the check ends early.

When no components are overlapping in an illegal way, the grid is searched greedily starting from the `InputComponents` like a Depth-First-Search (DFS) which is implemented using a Stack data structure. For circuits such as Flip-Flops, the input for part of a gate is dependent with another gate, creating a circular dependency. To tackle this issue, the logicHandler lazily determines the output of some gate with just a single input if it possible to do so. One example would be an `AndGate`. If one input of an `AndGate` is False, it is possible to determine that the `AndGate` must output False.

### LogicGate

Logic Gates are implemented as an abstract class which is extended by each gate such as ANDGate, ORGate, and XORGate. Each logic gate have associated image files in `resources/gates`.

### GateComponent

Instead of having the `LogicGate` interface directly with the `GridPanel`, the functions and parameters only necessary with the GridPanel were extended using this GateComponent class.

### WireComponent

When a `WireComponent` is initialized, the points are sorted by lowest x value then lowest y value to make it easier to check for duplicate wires.

## Diagrams

![UML Diagram](/Report/UML%20diagram.png)

## Standards and Conventions

The project adheres to Java's camelCase naming convention, with classes beginning with uppercase letters, methods as verbs, and objects as nouns.
