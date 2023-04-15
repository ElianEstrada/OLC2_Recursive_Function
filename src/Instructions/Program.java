package Instructions;

import Abstract.Instruction;
import Environment.Error;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Tree;

import java.text.MessageFormat;
import java.util.ArrayList;

public class Program extends Instruction {

    private ArrayList<Object> listInstructions;

    public Program(int row, int column, ArrayList<Object> listInstructions) {
        super(row, column);
        setListInstructions(listInstructions);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return null;
    }

    @Override
    public Object interpret(SymbolTable table) {

        SymbolTable newTable = new SymbolTable(table);
        newTable.setName(MessageFormat.format("Main-{0}-{1}", row, column));

        this.tableCompile = newTable;

        Tree tree = Tree.getInstance();
        Object result;
        Error error;

        for (Object item: this.listInstructions) {
            result = ((Instruction) item).interpret(newTable);

            if (result != null) {
                if (result instanceof ReturnType) {
                    if (((ReturnType) result).getType() == null){
                        tree.getErrors().add((Error) ((ReturnType) result).getValue());
                        tree.updateConsole(((ReturnType) result).getValue().toString());
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object compile(SymbolTable table) {
        table = this.tableCompile;
        for (Object item: this.listInstructions) {
            ((Instruction) item).compile(table);
        }

        return null;
    }

    public ArrayList<Object> getListInstructions() {
        return listInstructions;
    }

    public void setListInstructions(ArrayList<Object> listInstructions) {
        this.listInstructions = listInstructions;
    }
}
