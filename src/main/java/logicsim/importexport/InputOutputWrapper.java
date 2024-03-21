package logicsim.importexport;

import logicsim.inout.InputOutputEnum;

import java.io.Serializable;

public class InputOutputWrapper implements Serializable {
    public InputOutputEnum type;
    public int id, positionX, positionY;
    public boolean enabled;

    public InputOutputWrapper(){}

    public InputOutputWrapper(InputOutputEnum type, int id, int positionX, int positionY,  boolean enabled) {
        this.type = type;
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.enabled = enabled;
    }
}
