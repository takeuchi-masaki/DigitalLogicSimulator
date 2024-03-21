package logicsim.importexport;

import logicsim.grid.GridPanel;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        assert(xmlFile.getAbsolutePath().endsWith(".xml"));
        GridWrapper gridWrapper = gridPanel.exportWrapper();
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlFile)))) {
            encoder.writeObject(gridWrapper);
            System.out.println("Exported: " + xmlFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
