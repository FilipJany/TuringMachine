package org.turing.app.common;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONAware;
import org.turing.app.importexport.ImportExportConstants;

import static com.google.common.base.MoreObjects.toStringHelper;

public class State implements JSONAware {

    private final String name;
    private final boolean isFinal;

    public State(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equal(isFinal, state.isFinal) &&
                Objects.equal(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, isFinal);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("isFinal", isFinal)
                .toString();
    }

    @Override
    public String toJSONString() {
        return "\"" + name + (isFinal ? ImportExportConstants.FINAL_STATE_SUFFIX : "") + "\"";
    }

    public static State fromJsonString(String jsonString) {
        boolean isFinal = jsonString.endsWith(ImportExportConstants.FINAL_STATE_SUFFIX);
        String stateName = StringUtils.removeEnd(jsonString, ImportExportConstants.FINAL_STATE_SUFFIX);
        if(((State)HaltState.HALT).name.equals(stateName) && ((State)HaltState.HALT).isFinal == isFinal) {
            return HaltState.HALT;
        }
        return new State(stateName, isFinal);
    }
}
