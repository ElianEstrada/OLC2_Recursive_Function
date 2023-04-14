package Abstract;

import Environment.Type;

import java.util.ArrayList;

public class Value {

    private Object value;
    private Type type;
    private boolean isTemp;
    private String trueLabel;
    private String falseLabel;
    private int size;

    public Value(Type type, Object value, boolean isTemp) {
        setType(type);
        setValue(value);
        setTemp(isTemp);
        setTrueLabel("");
        setFalseLabel("");
        setSize(1);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isTemp() {
        return isTemp;
    }

    public void setTemp(boolean temp) {
        isTemp = temp;
    }

    public String getTrueLabel() {
        return trueLabel;
    }

    public void setTrueLabel(String trueLabel) {
        this.trueLabel = trueLabel;
    }

    public String getFalseLabel() {
        return falseLabel;
    }

    public void setFalseLabel(String falseLabel) {
        this.falseLabel = falseLabel;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
