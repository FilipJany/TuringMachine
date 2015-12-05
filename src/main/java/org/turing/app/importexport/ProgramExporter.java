package org.turing.app.importexport;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.ProgramModel;

import java.util.Collection;
import java.util.Map;

public class ProgramExporter {

    private final ProgramModel programModel;

    public ProgramExporter(ProgramModel programModel) {
        this.programModel = programModel;
    }

    public JSONObject getProgramDataAsJson() {
        Map<String, Object> data = Maps.newHashMap();
        data.put(ImportExportConstants.SYMBOLS_PARAM, getAsJsonArray(programModel.getAvailableSymbols()));
        data.put(ImportExportConstants.STATES_PARAM, getAsJsonArray(programModel.getAvailableStates()));
        data.put(ImportExportConstants.TRANSITIONS_PARAM, getTransitions());

        return new JSONObject(data);
    }

    private JSONArray getAsJsonArray(Collection<?> collection) {
        JSONArray symbols = new JSONArray();
        symbols.addAll(collection);
        return symbols;
    }

    public JSONObject getTransitions() {
        Map<JSONAware, Object> transitions = Maps.newHashMap();
        Table<State, Symbol, ActionTriple> transitionTable = programModel.getTransitionTable();
        for (State state : transitionTable.rowKeySet()) {
            JSONObject symbolToActionsMapping = changeProperlyIntoJsonObject(transitionTable.row(state));
            transitions.put(state, symbolToActionsMapping);
        }
        return changeProperlyIntoJsonObject(transitions);
    }

    private JSONObject changeProperlyIntoJsonObject(Map<? extends JSONAware, ?> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<? extends JSONAware, ?> jsonAwareEntry : map.entrySet()) {
            JSONAware key = jsonAwareEntry.getKey();
            Object value = jsonAwareEntry.getValue();
            jsonObject.put(stripQuotationMarks(key.toJSONString()), value);
        }
        return jsonObject;
    }

    private String stripQuotationMarks(String string) {
        string = StringUtils.removeStart(string, "\"");
        string = StringUtils.removeEnd(string, "\"");
        return string;
    }
}
