package Expression;

import Abstract.Instruction;
import Abstract.Value;
import Environment.Error;
import Environment.ReturnType;
import Environment.Symbol;
import Environment.SymbolTable;
import Environment.Type;
import Generator.Generator3D;

import java.text.MessageFormat;

public class Identifier extends Instruction {

    private String id;
    private Type type;
    private Object value;

    public Identifier(int row, int column, String id) {
        super(row, column);
        setId(id);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        this.tableCompile = table;

        Symbol symbol = table.getSymbol(this.id);

        if (symbol == null) {
            return new ReturnType(null, new Error("Semantic", MessageFormat.format("The variable: {0} does not exist in current context", this.id), table.getName(), row, column));
        }

        this.type = symbol.getType();
        this.value = symbol.getValue();

        return new ReturnType(this.type, this.value);
    }

    @Override
    public Object interpret(SymbolTable table) {
        return getValue(table);
    }

    @Override
    public Object compile(SymbolTable table) {
        table = this.tableCompile;
        Generator3D generator3D = Generator3D.getInstance();

        Symbol symbol = table.getSymbol(this.id);

        String position = generator3D.newTemporal();
        String temp = generator3D.newTemporal();

        generator3D.addExp(position, "P", "+", String.valueOf(symbol.getPosition()));
        generator3D.getStack(temp, position);

        if (symbol.getType() != Type.LOGICAL) {
            return new Value(symbol.getType(), temp, true);
        }

        if (trueLabel == null) {
            trueLabel = generator3D.newLabel();
        }

        if (falseLabel == null) {
            falseLabel = generator3D.newLabel();
        }

        generator3D.addIf(temp, "==", "1", trueLabel);
        generator3D.addGoto(falseLabel);

        return new Value(Type.LOGICAL, "", false);
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
}
