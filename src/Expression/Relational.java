package Expression;

import Abstract.Instruction;
import Abstract.Value;
import Environment.Error;
import Environment.RelationalOp;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Type;
import Generator.Generator3D;

import java.text.MessageFormat;

public class Relational extends Instruction {

    private Object left;
    private Object right;
    private RelationalOp op;
    private Type type;
    private Object value;
    private ReturnType rightValue;

    public Relational(int row, int column, Object left, Object right, RelationalOp op) {
        super(row, column);
        setLeft(left);
        setRight(right);
        setOp(op);
        setType(Type.LOGICAL);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {

        ReturnType left = ((Instruction) this.left).getValue(table);
        ReturnType right = ((Instruction) this.right).getValue(table);
        this.rightValue = right;

        if (left.getType() == null) {
            // Semantic Error
            return left;
        }

        if (right.getType() == null) {
            // Semantic Error
            return right;
        }

        if (this.op == RelationalOp.EQ || this.op == RelationalOp.NEQ) {
            if (left.getType() != right.getType()) {
                //Semantic Error
                String message = MessageFormat.format("Can''t operate the Type: {0} with Type: {1}", left.getType(), right.getType());
                return new ReturnType(null, new Error("Semantic", message, table.getName(), row, column));
            }

            return new ReturnType(this.type, (this.op == RelationalOp.EQ) == (left.getValue().equals(right.getValue())));
        } else {
            if ( left.getType() == Type.INTEGER && right.getType() == Type.INTEGER) {

                if (left.getType() != right.getType()) {
                    //Semantic Error
                    return new ReturnType(null, new Error("Semantic", MessageFormat.format("Can''t operate the Type: {0} with Type: {1}", left.getType().name(), right.getType().name()), table.getName(), row, column));
                }

                if (this.op == RelationalOp.GT){
                    return new ReturnType(this.type, Integer.parseInt(left.getValue().toString()) > Integer.parseInt(right.getValue().toString()));
                } else if (this.op == RelationalOp.GTE) {
                    return new ReturnType(this.type, Integer.parseInt(left.getValue().toString()) >= Integer.parseInt(right.getValue().toString()));
                } else if (this.op == RelationalOp.LT) {
                    return new ReturnType(this.type, Integer.parseInt(left.getValue().toString()) < Integer.parseInt(right.getValue().toString()));
                } else if (this.op == RelationalOp.LTE) {
                    return new ReturnType(this.type, Integer.parseInt(left.getValue().toString()) <= Integer.parseInt(right.getValue().toString()));
                } else {
                    return new ReturnType(null, new Error("Semantic", MessageFormat.format("The operator: {0} does''t relational", this.op), table.getName(), row, column));
                }
            }

            return new ReturnType(null, new Error("Semantic", MessageFormat.format("Can''t operate the Types (STRING | LOGICAL) whit operator: {0}", this.op.getDefaultValue()), table.getName(), row, column));

        }
    }

    @Override
    public Object interpret(SymbolTable table) {
        return getValue(table);
    }

    @Override
    public Object compile(SymbolTable table) {
        Generator3D generator3D = Generator3D.getInstance();

        Value vLeft = (Value) ((Instruction) this.left).compile(table);
        Value vRight;

        if (trueLabel == null) {
            trueLabel = generator3D.newLabel();
        }

        if (falseLabel == null) {
            falseLabel = generator3D.newLabel();
        }

        if (vLeft.getType() == Type.LOGICAL) {
            String label = generator3D.newLabel();
            String temp = generator3D.newTemporal();

            generator3D.printLabel(((Instruction) this.left).getTrueLabel());
            generator3D.addExp(temp, "1", "", "");
            generator3D.addGoto(label);
            generator3D.printLabel(((Instruction) this.left).getFalseLabel());
            generator3D.addExp(temp, "0", "", "");
            generator3D.printLabel(label);

            vRight = (Value) ((Instruction) this.right).compile(table);
            String labelR = generator3D.newLabel();
            String tempR = generator3D.newTemporal();

            generator3D.printLabel(((Instruction) this.right).getTrueLabel());
            generator3D.addExp(tempR, "1", "", "");
            generator3D.addGoto(labelR);
            generator3D.printLabel(((Instruction) this.right).getFalseLabel());
            generator3D.addExp(tempR, "0", "", "");
            generator3D.printLabel(labelR);

            generator3D.addIf(temp, this.op.getDefaultValue(), tempR, trueLabel);
            generator3D.addGoto(falseLabel);

            return new Value(Type.LOGICAL, "", false);
        } else {
            vRight = (Value) ((Instruction) this.right).compile(table);
        }

        generator3D.addIf(vLeft.getValue().toString(), this.op.getDefaultValue(), vRight.getValue().toString(), trueLabel);
        generator3D.addGoto(falseLabel);

        return new Value(Type.LOGICAL, "", false);
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public RelationalOp getOp() {
        return op;
    }

    public void setOp(RelationalOp op) {
        this.op = op;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ReturnType getRightValue() {
        return rightValue;
    }

    public void setRightValue(ReturnType rightValue) {
        this.rightValue = rightValue;
    }
}
