package Instructions;

import Abstract.Instruction;
import Environment.Error;
import Environment.ReturnType;
import Environment.Symbol;
import Environment.SymbolTable;

import java.text.MessageFormat;

public class Assignmate extends Instruction {

    private String id;
    private Object expression;

    public Assignmate(int row, int column, String id, Object expression) {
        super(row, column);
        setId(id);
        setExpression(expression);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return null;
    }

    @Override
    public Object interpret(SymbolTable table) {

        this.tableCompile = table;
        Symbol symbol = table.getSymbol(this.id);

        if (symbol == null) {
            // Semantic Error
            return new Error("Semantic", MessageFormat.format("The variable with id: {0} does not exist in current context", this.id), table.getName(), row, column);
        }

        ReturnType exp = ((Instruction) this.expression).getValue(table);

        if (exp.getType() == null) {
            // Semantic Error
            return exp.getValue();
        }

        if (symbol.getType() != exp.getType()) {
            // Semantic Error
            return new Error("Semantic", MessageFormat.format("Cannot assign a {0} value to a variable of type: {1}", exp.getType().name(), symbol.getType().name()), table.getName(), row, column);
        }

        symbol.setValue(exp.getValue());

        return null;
    }

    @Override
    public Object compile(SymbolTable table) {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }
}
