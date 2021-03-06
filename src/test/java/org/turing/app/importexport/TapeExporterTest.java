package org.turing.app.importexport;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;

public class TapeExporterTest {

    public static final Symbol A = new Symbol("a");
    public static final Symbol B = new Symbol("b");
    public static final Symbol C = new Symbol("c");
    public static final Symbol D = new Symbol("d");
    public static final Symbol E = new Symbol("e");
    public static final State STATE = new State("dupa", false);

    public static final ProgramModel programModel = new ProgramModel();
    static {
        programModel.addNewSymbol(A);
        programModel.addNewSymbol(B);
        programModel.addNewSymbol(C);
        programModel.addNewSymbol(D);
        programModel.addNewSymbol(E);
        programModel.addNewState(STATE);
    }

    @Test
    public void dupa() throws Exception {
        DataModel dataModel = new DataModel(programModel);
        dataModel.initialize(Lists.newArrayList(A, B, C, D, E));
        dataModel.moveRight();
        dataModel.moveRight();

        dataModel.setState(STATE);

        TapeExporter tapeExporter = new TapeExporter(dataModel);


        System.out.println(tapeExporter.getTapeDataAsJson().toJSONString());
    }
}