package Expression;

import Abstract.Instruction;
import Abstract.Value;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Type;
import Generator.Generator3D;

public class Primitive extends Instruction {

    public Type type;
    public Object value;

    public Primitive(int row, int column, Type type, Object value) {
        super(row, column);
        this.type = type;
        this.value = value;
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return new ReturnType(this.type, this.value);
    }

    @Override
    public Object interpret(SymbolTable table) {
        return getValue(table);
    }

    @Override
    public Object compile(SymbolTable table) {
        //table = this.tableCompile;
        Generator3D generator3D = Generator3D.getInstance();

        if (this.type == Type.INTEGER) {
            return new Value(this.type, this.value, false);
        } else if (this.type == Type.LOGICAL) {
            if (trueLabel == null) {
                trueLabel = generator3D.newLabel();
            }

            if (falseLabel == null) {
                falseLabel = generator3D.newLabel();
            }

            if (Boolean.parseBoolean(this.value.toString())){
                generator3D.addGoto(trueLabel);
                generator3D.addGoto(falseLabel);
            } else {
                generator3D.addGoto(falseLabel);
                generator3D.addGoto(trueLabel);
            }

            return new Value(Type.LOGICAL, this.value, false);
        } else if (this.type == Type.STRING) {
               String temp = generator3D.newTemporal();
               generator3D.addExp(temp, "H", "", "");
               String in = this.value.toString();

               for (int i = 0; i < in.length(); i++) {
                   generator3D.setHeap("H", String.valueOf((int) this.value.toString().charAt(i)));
                   generator3D.nextHeap();
               }

               generator3D.setHeap("H", "-1");
               generator3D.nextHeap();

               return new Value(this.type, temp, true);
        }

        return null;
    }
}
