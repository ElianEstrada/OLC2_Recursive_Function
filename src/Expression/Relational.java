package Expression;

import Abstract.Instruction;
import Environment.Error;
import Environment.RelationalOp;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Type;

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
        return null;
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
