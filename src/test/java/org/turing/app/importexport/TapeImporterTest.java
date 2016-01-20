package org.turing.app.importexport;

import com.google.common.collect.Lists;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;

import static org.junit.Assert.assertEquals;

public class TapeImporterTest {

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
        String json = "{\"state\":\"dupa\",\"tape\":[\"a\",\"b\",\"c\",\"d\",\"e\"],\"offset\":2}";
        DataModel dataModel = new DataModel(programModel);
        TapeImporter tapeImporter = new TapeImporter(dataModel);

        tapeImporter.readIntoModel((JSONObject) JSONValue.parse(json));

        assertEquals(STATE, dataModel.getState());
        assertEquals(Lists.newArrayList(A, B), dataModel.getLeftTape());
        assertEquals(C, dataModel.read());
        assertEquals(Lists.newArrayList(E, D), dataModel.getRightTape());
    }
}