package Environment;

public enum RelationalOp {
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<="),
    EQ("=="),
    NEQ("!=");

    private final String defaultValue;

    RelationalOp(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
