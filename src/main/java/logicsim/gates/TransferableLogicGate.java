package logicsim.gates;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class TransferableLogicGate implements Transferable {
    private LogicGate logicGate;
    private static final DataFlavor LOGIC_GATE_FLAVOR = new DataFlavor(LogicGate.class, "Logic Gate");

    public TransferableLogicGate(LogicGate logicGate) {
        this.logicGate = logicGate;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { LOGIC_GATE_FLAVOR };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return LOGIC_GATE_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return logicGate;
    }
}
