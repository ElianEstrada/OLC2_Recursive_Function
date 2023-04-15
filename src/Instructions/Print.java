package Instructions;

import Abstract.Instruction;
import Abstract.Value;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Tree;
import Environment.Type;
import Generator.Generator3D;

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
        Generator3D generator3D = Generator3D.getInstance();
        Value value = (Value) ((Instruction) this.expression).compile(table);

        if (value.getType() == Type.INTEGER) {
            generator3D.addPrint("d", "int", value.getValue());
        }

        if (value.getType() == Type.STRING) {
            generator3D.printStr();

            String temp = generator3D.newTemporal();

            generator3D.addExp(temp, "P", "+", String.valueOf(table.getSize()));
            generator3D.addExp(temp, temp, "+", "1");
            generator3D.setStack(temp, value.getValue().toString());
            generator3D.newEnvironment(String.valueOf(table.getSize()));
            generator3D.getFunc("printStr");
            generator3D.getEnvironment(String.valueOf(table.getSize()));
        }

        if (value.getType() == Type.LOGICAL) {
            String label = generator3D.newLabel();

            generator3D.printLabel(((Instruction) this.expression).getTrueLabel());
            generator3D.addPrint("d", "int", 1);
            generator3D.addGoto(label);
            generator3D.printLabel(((Instruction) this.expression).getFalseLabel());
            generator3D.addPrint("d", "int", 0);
            generator3D.printLabel(label);

        }

        generator3D.addPrint("c", "int", 10);

        return null;
    }
}
