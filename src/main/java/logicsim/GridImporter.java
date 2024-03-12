package logicsim;

import java.io.File;

public class GridImporter {
    private static GridImporter INSTANCE;
    GridPanel gridPanel = GridPanel.getInstance();

    private GridImporter() {}
    public static GridImporter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GridImporter();
        }
        return INSTANCE;
    }

    public void importFile(File xmlFile) {
        System.out.println("Import: " + xmlFile.getAbsolutePath());
    }
}
