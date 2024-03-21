package logicsim.importexport;

import logicsim.GridPanel;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
        assert(xmlFile.getAbsolutePath().endsWith(".xml"));
        GridWrapper wrapper = null;
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlFile)))) {
            wrapper = (GridWrapper) decoder.readObject();
            System.out.println("Importing: " + xmlFile.getAbsolutePath());
            if (wrapper != null) {
                gridPanel.importWrapper(wrapper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
