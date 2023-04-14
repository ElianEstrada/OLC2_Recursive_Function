package Instructions;

import Abstract.Instruction;
import Environment.ReturnType;
import Environment.SymbolTable;

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
