package logicsim.logic;

import logicsim.GridPanel;

import java.util.ArrayList;
import java.awt.Point;

public class GridLogicHandler {
    GridPanel gridPanel = GridPanel.getInstance();

    boolean check() {
        /*
            first check positioning of gates
            if gate inputs are overlapping on same position
            or inputs/outputs overlapping on same position
            label red
         */

        ArrayList<ArrayList<Boolean>> visited = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            visited.add(new ArrayList<>(150));
        }

//        Deque<>
/*
        while(!dq.empty()) {
            state = dq.popFront();
            for each input output?
                if input:
                    fail?
                if output:
                    label output with result
            for each gate
                if gate output:
                    fail?
                if gate input:
                    label input
                    if possible greedy:
                        dq.pushFront
            for each wire
                if !visited other wire end:
                    dq.pushFront
        }
 */

        return false;
    }
}
