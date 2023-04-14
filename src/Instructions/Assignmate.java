package Instructions;

import Abstract.Instruction;
import Abstract.Value;
import Environment.*;
import Environment.Error;
import Generator.Generator3D;

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
        table = this.tableCompile;

        Generator3D generator3D = Generator3D.getInstance();

        Symbol symbol = table.getSymbol(this.id);

        String position = generator3D.newTemporal();
        generator3D.addExp(position, "P", "+", String.valueOf(symbol.getPosition()));
        Value value = (Value) ((Instruction) this.expression).compile(table);

        if (value.getType() == Type.LOGICAL) {
            String label = generator3D.newLabel();

            generator3D.printLabel(((Instruction) this.expression).getTrueLabel());
            generator3D.setStack(position, "1");
            generator3D.addGoto(label);
            generator3D.printLabel(((Instruction) this.expression).getFalseLabel());
            generator3D.setStack(position, "0");
            generator3D.printLabel(label);
        } else {
            generator3D.setStack(position, value.getValue().toString());
        }

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
