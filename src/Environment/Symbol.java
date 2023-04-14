package Environment;

public class Symbol {

    /*---------------Interpret---------------*/
    private String id;
    private Type type;
    private Object value;
    private String environment;
    private int row;
    private int column;

    /*---------------Translate---------------*/
    private int position;
    private int size;
    private boolean inHeap;
    private String labelTrue;
    private String labelFalse;

    public Symbol(String id, Type type, Object value, String environment, int row, int column) {
        setId(id);
        setType(type);
        setValue(value);
        setEnvironment(environment);
        setRow(row);
        setColumn(column);
        setSize(1);
    }

    public Symbol(String id, Type type, Object value, String environment, int row, int column, boolean inHeap, String labelTrue, String labelFalse) {
        setId(id);
        setType(type);
        setValue(value);
        setEnvironment(environment);
        setRow(row);
        setColumn(column);
        setInHeap(inHeap);
        setLabelFalse(labelFalse);
        setLabelTrue(labelTrue);
        setSize(1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isInHeap() {
        return inHeap;
    }

    public void setInHeap(boolean inHeap) {
        this.inHeap = inHeap;
    }

    public String getLabelTrue() {
        return labelTrue;
    }

    public void setLabelTrue(String labelTrue) {
        this.labelTrue = labelTrue;
    }

    public String getLabelFalse() {
        return labelFalse;
    }

    public void setLabelFalse(String labelFalse) {
        this.labelFalse = labelFalse;
    }
}
