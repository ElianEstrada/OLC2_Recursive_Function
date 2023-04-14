package Environment;

public class ReturnType {

    private Type type;
    private Object value;

    public ReturnType(Type type, Object value) {
        setType(type);
        setValue(value);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
