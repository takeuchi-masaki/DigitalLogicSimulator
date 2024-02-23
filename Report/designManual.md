# Design Manual

## Architecture Overview

The application is structured around the main `App` class, which extends `JFrame`. It orchestrates the overall layout and interactions between the `PalettePanel`, `GridPanel`, and `TopMenuBarPanel`.

- `App`: Serves as the main entry point and container for the application. It initializes and arranges the primary panels.
- `TopMenuBarPanel`: Extends `JPanel` and includes menu items for actions like saving, loading, undoing changes, adjusting settings, and accessing information about the application.
- `PalettePanel`: Extends `JPanel`, contains a collection of `PaletteComponents`, and handles mouse events for interactions with these components.
- `GridPanel`: Also extends `JPanel` and is responsible for displaying the grid, logic gates, and wires. It manages mouse interactions and holds references to `GridLogicHandler`.
- `GridLogicHandler`: Handles the logic related to the grid, including interactions with logic gates and wires.
- `PaletteComponent`: Represents a draggable component in the `PalettePanel` with properties such as `logicGate` and `bounds`. It includes a `draw()` method for rendering.
- `LogicGate`: An abstract class extended by specific gate types like `ANDGate`. It provides essential methods like `draw()`, `contains()`, and `output()` to handle logic gate functionalities.

Each of these components works together to provide a comprehensive digital logic simulation environment.

## Design Patterns

### Flyweight

The `LogicGate` instances are shared among `PaletteComponents` to minimize memory usage. Only the position and orientation are unique to each `PaletteComponent`.

### State

The `GridLogicHandler` will potentially implement the State pattern to step through the circuit states, much like a debugger stepping through code.

## Component Descriptions

### App

At the top of the architectural hierarchy is the App class. This launches and initializes the application and creates a layout for the TopMenuBarPanel, PalettePanel, and GridPanel.

### TopMenuBarPanel

The TopMenuBarPanel will contain commands for saving and loading circuits.

### PalettePanel

The PalettePanel displays PaletteComponents which each house a LogicGate component.

### GridPanel

The GridPanel displays a grid and the current view of the logic gates and wires. In future updates, a CircuitLogic class will be added that calculates outcomes based on the circuit on the grid.

### LogicGate

Logic Gates are currently implemented as an abstract class which is extended by each gate such as ANDGate.

## Diagrams

<!-- pagebreak -->

## Standards and Conventions

The project adheres to Java's camelCase naming convention, with classes beginning with uppercase letters, methods as verbs, and objects as nouns.
