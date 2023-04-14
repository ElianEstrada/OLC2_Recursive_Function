package Instructions;

import Abstract.Instruction;
import Environment.*;
import Environment.Error;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CallFunction extends Instruction {

    private String name;
    private ArrayList<Object> parameters;
    private ReturnType result;

    public CallFunction(int row, int column, String name, ArrayList<Object> parameters) {
        super(row, column);
        setName(name);
        setParameters(parameters);
        setResult(new ReturnType(null, null));
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return (ReturnType) interpret(table);
    }

    @Override
    public Object interpret(SymbolTable table) {

        Tree tree = Tree.getInstance();
        Function function = (Function) tree.getFunctions(this.name);

        if (function == null) {
            // Semantic Error
            return new ReturnType(null, new Error("Semantic", MessageFormat.format("The function with id: {0} does not exist", this.name), table.getName(), row, column));
        }

        if (function.getParameters().size() != this.parameters.size()) {
            // Semantic Error
            return new ReturnType(null, new Error("Semantic", MessageFormat.format("This function expect {0} parameters and got {1}", function.getParameters().size(), this.parameters.size()), table.getName(), row, column));
        }

        SymbolTable newTable = new SymbolTable(tree.getGlobalTable());
        newTable.setName(MessageFormat.format("Function-{0}-{1}-{2}", this.name, row, column));

        Object result;
        ReturnType value;
        String param;
        Symbol newSymbol;
        int count = 0;

        for (Object item : this.parameters) {

            value = ((Instruction) item).getValue(table);

            if (value.getType() == null) {
                // Semantic Error
                return value.getValue();
            }

            param = function.getParameters().get(count);
            newSymbol = new Symbol(param, value.getType(), value.getValue(), newTable.getName(), row, column);
            result = newTable.addSymbol(newSymbol);

            if (result != null) {
                // Semantic Error
                return result;
            }

            count++;
        }

        value = function.getValue(newTable);

        if (value.getType() == null) {
            // Semantic Error
            return value.getValue();
        }

        this.result.setType(value.getType());
        this.result.setValue(value.getValue());

        return this.result;
    }

    @Override
    public Object compile(SymbolTable table) {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Object> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Object> parameters) {
        this.parameters = parameters;
    }

    public ReturnType getResult() {
        return result;
    }

    public void setResult(ReturnType result) {
        this.result = result;
    }
}
