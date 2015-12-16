package org.turing.app.importexport;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.turing.app.common.Symbol;
import org.turing.app.model.DataModel;

import java.util.List;
import java.util.Map;

public class TapeExporter {

    private final DataModel dataModel;

    public TapeExporter(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public JSONObject getTapeDataAsJson() {
        Map<String, Object> data = Maps.newHashMap();
        data.put(ImportExportConstants.CURRENT_STATE_PARAM, dataModel.getState());
        data.put(ImportExportConstants.TAPE_PARAM, getTapeContent());
        data.put(ImportExportConstants.OFFSET_PARAM, dataModel.getLeftTape().size());

        return new JSONObject(data);
    }

    private JSONArray getTapeContent() {
        JSONArray tapeContent = new JSONArray();
        List<Symbol> leftTape = Lists.newArrayList(dataModel.getLeftTape());
        List<Symbol> rightTape = Lists.newArrayList(dataModel.getRightTape());

        tapeContent.addAll(leftTape);
        tapeContent.add(dataModel.read());
        tapeContent.addAll(Lists.reverse(rightTape));

        return tapeContent;
    }

}
