package Environment;

import Expression.Primitive;

public enum Type {
    INTEGER(new Primitive(-1, -1, null, 0)),
    STRING(new Primitive(-1, -1, null, "")),
    LOGICAL(new Primitive(-1, -1, null, false));

    private final Primitive defaultValue;

    Type(Primitive defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Primitive getDefaultValue() {
        return this.defaultValue;
    }
}
