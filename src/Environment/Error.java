package Environment;

import java.text.MessageFormat;

public class Error {

    private String typeError;
    private String description;
    private String environment;
    private int row;
    private int column;

    public Error(String typeError, String description, String environment, int row, int column) {
        setTypeError(typeError);
        setDescription(description);
        setEnvironment(environment);
        setRow(row);
        setColumn(column);
    }

    public String getTypeError() {
        return typeError;
    }

    public void setTypeError(String typeError) {
        this.typeError = typeError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString(){
        return MessageFormat.format("--> {0} - {1} in {2} on [{3}, {4}]", getTypeError(), getDescription(), getEnvironment(), getRow(), getColumn());
    }

}
