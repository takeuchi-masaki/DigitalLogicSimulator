package logicsim.grid;

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
        // TODO: update export XML (after wires)
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.newDocument();
//            Element rootElement = doc.createElement("GridComponents");
//            doc.appendChild(rootElement);
//
//            for (GridComponent component : components) {
//                Element componentElement = doc.createElement("GridComponent");
//                componentElement.setAttribute("id", String.valueOf(component.id));
//                componentElement.setAttribute("x", String.valueOf(component.coordinatePosition.x));
//                componentElement.setAttribute("y", String.valueOf(component.coordinatePosition.y));
//                componentElement.setAttribute("gateType", component.gateType.name());
//                componentElement.setAttribute("orientation", String.valueOf(component.orientation));
//                componentElement.setAttribute("input1Not", String.valueOf(component.input1Not));
//                componentElement.setAttribute("input2Not", String.valueOf(component.input2Not));
//                componentElement.setAttribute("outputNot", String.valueOf(component.outputNot));
//                rootElement.appendChild(componentElement);
//            }
//
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(xmlFile);
//            transformer.transform(source, result);
//        } catch (Exception e) {
//            System.err.println("Export failed");
//            e.printStackTrace();
//        }
    }
}
