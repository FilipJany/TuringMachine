package org.turing.app.common;

import com.google.common.base.Objects;
import org.json.simple.JSONAware;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * I wish this will stay immutable... ;)
 */
public class Symbol implements JSONAware {

    private final String value;

    public Symbol(String value) {
        this.value = checkNotNull(value);
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

    public static Symbol fromJsonString(String jsonString) {
        if(((Symbol)BlankSymbol.BLANK).value.equals(jsonString)) {
            return BlankSymbol.BLANK;
        }
        return new Symbol(jsonString);
    }
}
