package logicsim;

import java.io.File;

public class GridExporter {
    private static GridExporter INSTANCE;
    GridPanel gridPanel = GridPanel.getInstance();

    private GridExporter() {}
    public static GridExporter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GridExporter();
        }
        return INSTANCE;
    }

    public void exportFile(File xmlFile) {
        System.out.println("Export: " + xmlFile.getAbsolutePath());
    }
}
