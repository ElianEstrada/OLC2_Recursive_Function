package Instructions;

import Abstract.Instruction;
import Environment.*;
import Environment.Error;

import java.text.MessageFormat;
import java.util.ArrayList;

public class If extends Instruction {

    private Object expression;
    private ArrayList<Object> ifInstructions;
    private ArrayList<Object> elseInstructions;
    private Instruction elseIf;

    public If(int row, int column, Object expression, ArrayList<Object> ifInstructions, Instruction elseIf, ArrayList<Object> elseInstructions) {
        super(row, column);
        setExpression(expression);
        setIfInstructions(ifInstructions);
        setElseIf(elseIf);
        setElseInstructions(elseInstructions);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return null;
    }

    @Override
    public Object interpret(SymbolTable table) {

        this.tableCompile = table;

        ReturnType condition = ((Instruction) this.expression).getValue(table);

        if (condition.getType() == null) {
            // Semantic Error
            return condition.getValue();
        }

        if (condition.getType() != Type.LOGICAL) {
            // Semantic Error
            return new Error("Semantic", MessageFormat.format("Execpt a LOGICAL type expression, not {0}", condition.getType().name()), table.getName(), row, column);
        }

        Object result;
        Tree tree = Tree.getInstance();
        SymbolTable newTable = new SymbolTable(table);

        if( Boolean.parseBoolean(condition.getValue().toString())) {
            newTable.setName(MessageFormat.format("If-{0}-{1}", row, column));

            for (Object item: this.ifInstructions) {
                result = getObject(tree, newTable, (Instruction) item);
                if (result != null) return result;
            }
        } else if (this.elseInstructions != null) {
            newTable.setName(MessageFormat.format("Else-{0}-{1}", row, column));

            for (Object item: this.elseInstructions) {
                result = getObject(tree, newTable, (Instruction) item);
                if (result != null) return result;
            }
        } else if (this.elseIf != null) {
            return this.elseIf.interpret(table);
        }

        return null;
    }

    @Override
    public Object compile(SymbolTable table) {
        return null;
    }

    private Object getObject(Tree tree, SymbolTable newTable, Instruction item) {
        Object result;
        result = item.interpret(newTable);

        if (result != null) {
            if (result instanceof ReturnType) {
                if (((ReturnType) result).getValue() == null) {
                    // Semantic Error
                    tree.getErrors().add((Error) ((ReturnType) result).getValue());
                    tree.updateConsole(((ReturnType) result).getValue().toString());
                }
            } else if (result instanceof Return) {
                return result;
            } else {
                tree.getErrors().add((Error) result);
                tree.updateConsole(result.toString());
            }
        }

        return null;
    }

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public ArrayList<Object> getIfInstructions() {
        return ifInstructions;
    }

    public void setIfInstructions(ArrayList<Object> ifInstructions) {
        this.ifInstructions = ifInstructions;
    }

    public ArrayList<Object> getElseInstructions() {
        return elseInstructions;
    }

    public void setElseInstructions(ArrayList<Object> elseInstructions) {
        this.elseInstructions = elseInstructions;
    }

    public Instruction getElseIf() {
        return elseIf;
    }

    public void setElseIf(Instruction elseIf) {
        this.elseIf = elseIf;
    }
}
