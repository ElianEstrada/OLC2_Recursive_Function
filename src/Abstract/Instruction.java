package Abstract;

import Environment.ReturnType;
import Environment.SymbolTable;

public abstract class Instruction {

    protected int row;
    protected int column;
    protected String trueLabel;
    protected String falseLabel;
    protected SymbolTable tableCompile;

    public Instruction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public abstract ReturnType getValue(SymbolTable table);
    public abstract Object interpret(SymbolTable table);
    public abstract Object compile(SymbolTable table);

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

    public SymbolTable getTableCompile() {
        return tableCompile;
    }

    public void setTableCompile(SymbolTable tableCompile) {
        this.tableCompile = tableCompile;
    }
}
