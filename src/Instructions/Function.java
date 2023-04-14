package Instructions;

import Abstract.Instruction;
import Environment.Error;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Tree;
import Environment.Type;

import java.text.MessageFormat;
import java.util.ArrayList;

public class Function extends Instruction {

    private String name;
    private ArrayList<String> parameters;
    private ArrayList<Object> listInstructions;

    private Type type;

    public Function(int row, int column, Type type, String name, ArrayList<String> parameters, ArrayList<Object> listInstructions) {
        super(row, column);
        setType(type);
        setName(name);
        setParameters(parameters);
        setListInstructions(listInstructions);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return (ReturnType) interpret(table);
    }

    @Override
    public Object interpret(SymbolTable table) {

        SymbolTable newTable = new SymbolTable(table);
        newTable.setName(MessageFormat.format("Function-{0}-{1}-{1}", this.name, this.row, this.column));

        Tree tree = Tree.getInstance();
        Object result;
        Error error;

        for (String param : this.parameters) {
            // Increment size of symbol table for compile
        }

        for (Object item: this.listInstructions) {
            result = ((Instruction) item).interpret(newTable);

            if (result instanceof Error) {
               // Semantic Error
                tree.getErrors().add((Error) result);
                tree.updateConsole(result.toString());
            }

            if (result instanceof Return) {
                if (((Return) result).getResult().getType() != this.type) {
                    // Semantic Error
                    return new ReturnType(null, new Error("Semantic", MessageFormat.format("The type: {0} dont equals to type of function: {1}", ((Return) result).getResult().getType().name(), this.type.name()), table.getName(), row, column));
                    /*tree.getErrors().add(error);
                    tree.updateConsole(error.toString());*/
                }

                return ((Return) result).getResult();

            }

        }

        return null;
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

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<Object> getListInstructions() {
        return listInstructions;
    }

    public void setListInstructions(ArrayList<Object> listInstructions) {
        this.listInstructions = listInstructions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
