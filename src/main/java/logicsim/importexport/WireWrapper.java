package logicsim.importexport;

import java.io.Serializable;

public class WireWrapper implements Serializable {
    public int startX, startY, endX, endY;

    public WireWrapper() {}

    public WireWrapper(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}
