package Instructions;

import Abstract.Instruction;
import Abstract.Value;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Type;
import Generator.Generator3D;

public class Return extends Instruction {

    private Object expression;
    private ReturnType result;

    public Return(int row, int column, Object expression) {
        super(row, column);
        setExpression(expression);
        setResult(new ReturnType(null, null));
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return null;
    }

    @Override
    public Object interpret(SymbolTable table) {

        ReturnType value = (ReturnType) ((Instruction) this.expression).interpret(table);

        if (value.getType() == null) {
            // Semantic Error
            return value.getValue();
        }

        result.setType(value.getType());
        result.setValue(value.getValue());

        return this;
    }

    @Override
    public Object compile(SymbolTable table) {
        table = this.tableCompile;

        Generator3D generator3D = Generator3D.getInstance();
        Value value = (Value) ((Instruction) this.expression).compile(table);

        if (value.getType() == Type.LOGICAL) {
            String label = generator3D.newLabel();

            generator3D.printLabel(((Instruction) this.expression).getTrueLabel());
            generator3D.setStack("P", "1");
            generator3D.addGoto(label);
            generator3D.printLabel(((Instruction) this.expression).getFalseLabel());
            generator3D.setStack("P", "0");
            generator3D.printLabel(label);
            table.setValueRet(value.getValue().toString());
        } else {
            generator3D.setStack("P", value.getValue().toString());
        }

        generator3D.addGoto(table.getExitLabel());

        return null;
    }

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public ReturnType getResult() {
        return result;
    }

    public void setResult(ReturnType result) {
        this.result = result;
    }
}
