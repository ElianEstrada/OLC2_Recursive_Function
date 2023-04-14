package Instructions;

import Abstract.Instruction;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Tree;

public class Print extends Instruction {

    public Object expression;

    public Print(int row, int column, Object expression) {
        super(row, column);
        this.expression = expression;
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return null;
    }

    @Override
    public Object interpret(SymbolTable table) {

        Tree tree = Tree.getInstance();
        this.tableCompile = table;
        ReturnType expr;

        expr = ((Instruction) expression).getValue(table);

        if (expr.getType() == null) {
            // Semantic Error
            return expr.getValue();
        }

        tree.updateConsole(expr.getValue().toString());

        return null;

    }

    @Override
    public Object compile(SymbolTable table) {
        return null;
    }
}
