package Environment;

import Abstract.Instruction;
import Instructions.Function;

import java.util.ArrayList;

public class Tree {

    private ArrayList<Instruction> instructions;
    private ArrayList<Error> errors;
    private ArrayList<Instruction> functions;
    private final StringBuilder console;
    private SymbolTable globalTable;
    private static Tree myTree;

    private Tree() {
        this.console = new StringBuilder();
        this.errors = new ArrayList<Error>();
        this.functions = new ArrayList<Instruction>();
    }

    public static Tree getInstance() {
        if (myTree == null) {
            myTree = new Tree();
        }

        return myTree;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<Error> errors) {
        this.errors = errors;
    }

    public Instruction getFunctions(String id) {
        for (Instruction item: this.functions) {
            //Search the function and return
            if (((Function) item).getName().equalsIgnoreCase(id)) {
                return item;
            }
        }

        return null;
    }

    public void addFunction(Instruction function) {
        this.functions.add(function);
    }

    public StringBuilder getConsole() {
        return this.console;
    }

    public void updateConsole(String input) {
        this.console.append(input).append('\n');
    }

    public SymbolTable getGlobalTable() {
        return globalTable;
    }

    public void setGlobalTable(SymbolTable globalTable) {
        this.globalTable = globalTable;
    }
}
