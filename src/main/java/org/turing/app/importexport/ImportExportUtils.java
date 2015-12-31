package org.turing.app.importexport;

import org.json.simple.JSONObject;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.exceptions.ImportExportException;
import org.turing.app.exceptions.SymbolException;

public class ImportExportUtils {

    public static State getStateFromObject(Object stateAsObject) {
        String stateAsString = getAsString(stateAsObject);
        return State.fromJsonString(stateAsString);
    }

    public static Symbol getSymbolFromObject(Object symbolAsObject) throws SymbolException {
        String symbolAsString = getAsString(symbolAsObject);
        return Symbol.fromJsonString(symbolAsString);
    }

    public static String getAsString(Object object) {
        if(!(object instanceof String)) {
            throw new ImportExportException("expected a string, got " + object);
        }
        return (String) object;
    }

    public static <T> T get(JSONObject jsonObject, Object key, Class<T> clazz) {
        Object result = jsonObject.get(key);
        if (!(clazz.isInstance(result))) {
            throw new ImportExportException(clazz.getName() + " expected for key: " + key);
        }
        return (T) result;
    }
}
