package org.turing.app.importexport;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import org.turing.app.common.*;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.ProgramModel;

import static org.junit.Assert.assertEquals;

public class ProgramImporterTest {

    @Test
    public void dupa() throws Exception {
        String json = "{\"transitions\":{\"doopa\":{\"A\":[\"HALT|final\",\"\",\"LEFT\"]}},\"symbols\":[\"\",\"A\"],\"states\":[\"doopa\",\"HALT|final\"]}";
        ProgramModel programModel = new ProgramModel();
        ProgramImporter programImporter = new ProgramImporter(programModel);

        programImporter.readIntoModel((JSONObject) JSONValue.parse(json));

        assertEquals(Sets.newHashSet(new Symbol("A"), BlankSymbol.BLANK), Sets.newHashSet(programModel.getAvailableSymbols()));
        assertEquals(Sets.newHashSet(new State("doopa", false), HaltState.HALT), Sets.newHashSet(programModel.getAvailableStates()));
        Table<State, Symbol, ActionTriple> expectedTransitionTable = HashBasedTable.create();
        expectedTransitionTable.put(new State("doopa", false), new Symbol("A"), new ActionTriple(HaltState.HALT, BlankSymbol.BLANK, MoveDirection.LEFT));
        assertEquals(expectedTransitionTable, programModel.getTransitionTable());
    }
}