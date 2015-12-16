package org.turing.app.importexport;

import com.google.common.collect.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.model.DataModel;

import java.util.List;

import static org.turing.app.importexport.ImportExportUtils.*;

public class TapeImporter {

    private final DataModel dataModel;

    public TapeImporter(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void readIntoModel(JSONObject tapeData) {
        readTape(tapeData);
        readState(tapeData);
    }

    private void readTape(JSONObject tapeData) {
        List<Symbol> symbols = readSymbolsIntoList(tapeData);
        dataModel.initialize(symbols);
        long offset = get(tapeData, ImportExportConstants.OFFSET_PARAM, Long.class);
        for (int i = 0; i < offset; i++) {
            dataModel.moveRight();
        }
    }

    private List<Symbol> readSymbolsIntoList(JSONObject tapeData) {
        List<Symbol> symbols = Lists.newArrayList();
        JSONArray tapeAsArray = get(tapeData, ImportExportConstants.TAPE_PARAM, JSONArray.class);
        for (Object symbolAsObject : tapeAsArray) {
            Symbol symbol = getSymbolFromObject(symbolAsObject);
            symbols.add(symbol);
        }
        return symbols;
    }

    private void readState(JSONObject tapeData) {
        String stateAsString = get(tapeData, ImportExportConstants.CURRENT_STATE_PARAM, String.class);
        State state = getStateFromObject(stateAsString);
        dataModel.setState(state);
    }
}
