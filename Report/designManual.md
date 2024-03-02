# Design Manual

## Architecture Overview

The application is structured around the main `App` class, which extends `JFrame`. It orchestrates the overall layout and interactions between the `PalettePanel`, `GridPanel`, and `TopMenuBarPanel`.

- `App`: Serves as the main entry point and container for the application. It initializes and arranges the primary panels.
- `MenuBar`: Contains menu items for `Save` and `Open` operations
- `MainPanel`: Handles mouse events and interactions between the PalettePanel and GridPanel
- `PalettePanel`: Displays useable LogicGates and information.
- `PaletteComponent`: Represents a draggable component in the `PalettePanel` with properties such as `logicGate` and `bounds`. It includes a `draw()` method for rendering.
- `GridPanel`: Handles grid components and wires using relative coordinate system
- `GridLogicHandler`: Handles the logic related to the grid, including interactions with logic gates and wires.
- `LogicGate`: An abstract class extended by specific gate types like `ANDGate`. It provides essential methods like `draw()`, `contains()`, and `output()` to handle logic gate functionalities.

Each of these components works together to provide a comprehensive digital logic simulation environment.

## Design Patterns

### State

The `GridLogicHandler` will potentially implement the State pattern to step through the circuit states, much like a debugger stepping through code.

## Component Descriptions

### App

At the top of the architectural hierarchy is the App class. This initializes the window with a MainPanel and MenuBar object.

### MenuBar

The MenuBar contains commands for saving and loading circuits.

### MainPanel

The MainPanel contains the logic for handling mouse operations (may be refactored out to a MouseHandler class). This class manages the interaction between the PalettePanel and GridPanel, such as drag and dropping new logic gate items from the PalettePanel into the GridPanel.

### PalettePanel

The PalettePanel displays PaletteComponents which each house a LogicGate component.

### GridPanel

The GridPanel displays a grid and the current view of the logic gates and wires. In future updates, a CircuitLogic class will be added that calculates outcomes based on the circuit on the grid.

### LogicGate

Logic Gates are currently implemented as an abstract class which is extended by each gate such as ANDGate.

## Diagrams

![UML Diagram](/Report/UML%20diagram.png)

## Standards and Conventions

The project adheres to Java's camelCase naming convention, with classes beginning with uppercase letters, methods as verbs, and objects as nouns.
