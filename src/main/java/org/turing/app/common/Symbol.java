package org.turing.app.common;

import com.google.common.base.Objects;
import org.json.simple.JSONAware;
import org.turing.app.exceptions.SymbolException;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * I wish this will stay immutable... ;)
 */
public class Symbol implements JSONAware {

    private final String value;

    public Symbol(String value) throws SymbolException {
        if (!isValid(value))
            throw new SymbolException("Symbol value not allowed.");
        this.value = checkNotNull(value);
    }

    private boolean isValid(String value) {
        return value != null && value.length() <= 1 && !value.equals("\t") && !value.equals("\n");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equal(value, symbol.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public String toJSONString() {
        return "\"" + value + "\"";
    }

    public static Symbol fromJsonString(String jsonString) throws SymbolException {
        if(((Symbol)BlankSymbol.BLANK).value.equals(jsonString)) {
            return BlankSymbol.BLANK;
        }
        return new Symbol(jsonString);
    }
}
