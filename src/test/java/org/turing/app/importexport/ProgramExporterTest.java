package org.turing.app.importexport;

import org.junit.Test;
import org.turing.app.common.*;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.ProgramModel;

import static junit.framework.Assert.assertEquals;

public class ProgramExporterTest {

    @Test
    public void dupa() throws Exception {
        ProgramModel programModel = new ProgramModel();
        programModel.addNewSymbol(new Symbol("A"));
        programModel.addNewState(new State("doopa", false));
        programModel.addNewTransition(new State("doopa", false), new Symbol("A"), new ActionTriple(HaltState.HALT, BlankSymbol.BLANK, MoveDirection.LEFT));
        ProgramExporter programExporter = new ProgramExporter(programModel);

        String expectedJson = "{\"transitions\":{\"doopa\":{\"A\":[\"HALT|final\",\"\",\"LEFT\"]}},\"symbols\":[\"\",\"A\"],\"states\":[\"HALT|final\",\"doopa\"]}";
        assertEquals(expectedJson, programExporter.getProgramDataAsJson().toJSONString());
    }
}