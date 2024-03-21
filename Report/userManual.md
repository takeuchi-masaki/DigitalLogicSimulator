# User Manual

## Setup

Launch the Application by running the App.java file `/src/main/java/logicsim/App.java`.

## Functionality

### Opening and Saving Circuits

Click the top menu option `File` and then the `Save` or `Open` menu options. Alternatively, use the keyboard shortcuts `Ctrl/Cmd + S` and `Ctrl/Cmd + O` for `Save` or `Open`, respectively.

Only `.xml` files that were created using this program are supported for opening.

Example circuits can be found im the `exampleCircuits` folder at the root of the project.

### Adding and Moving Components

Clicking and dragging a component allows you to add the Logic Gate to the Grid on the right-hand side. Each component will snap to the grid when dragging, and a visual shows the current position of the dragged object.
Objects placed on the grid are able to be moved with another drag and drop operation.

### Adding Wires

Click the `+ Wire` button at the top left of the screen or press the keyboard shortcut `W` to switch to the wire-adding mode. When the mouse hovers over the grid panel, it will now show a preview of a wire that can be added by clicking the left mouse button.

### Remove Components and Wires

Click the `X Delete` button at the top left of the screen to switch to the delete mode. When the mouse hovers over any previously placed wire or component on the grid, the hovered item is highlighted in red. Clicking the left mouse button will remove the item from the grid.

When multiple items are highlighted at the same time, the deletion prioritizes `Wires` then `InputOutputComponents` then `Gates`.

### Toggle Input 0-1

The Input component can be switched between `0` and `1` by switching to the `Move` mode and then highlighting and clicking the right mouse button on the input component you want to toggle.

Currently, the right click functionality is slightly buggy, especially when accidentally performing a `right click and drag` operation.

### Zoom the Grid

Use the mouse wheel to zoom in and out on the grid.
