package Expression;

import Abstract.Instruction;
import Abstract.Value;
import Environment.Error;
import Environment.ReturnType;
import Environment.SymbolTable;
import Environment.Type;
import Generator.Generator3D;
import Instructions.CallFunction;

import java.text.MessageFormat;

public class Arithmetic extends Instruction {

    private Object left;
    private Object right;
    private String op;
    private Type type;
    private Object value;

    private final Type[][] addDomain = {
            {Type.INTEGER, Type.STRING, null},
            {Type.STRING, Type.STRING, Type.STRING},
            {null, Type.STRING, null}
    };

    private final Type[][] domain = {
            {Type.INTEGER, null, null},
            {null, null, null},
            {null, null, null}
    };
    public Arithmetic(int row, int column, Object left, Object right, String op) {
        super(row, column);
        setLeft(left);
        setRight(right);
        setOp(op);
    }
    @Override
    public ReturnType getValue(SymbolTable table) {
        ReturnType left;
        ReturnType right;

        right = ((Instruction) this.right).getValue(table);

        if (right.getType() == null) {
            // Semantic Error
            return right;
        }

        if (this.left != null){

            left = ((Instruction) this.left).getValue(table);

            if (left.getType() == null) {
                // Semantic Error
                return left;
            }

            switch (this.op) {
                case "*":
                    this.type = this.domain[left.getType().ordinal()][right.getType().ordinal()];

                    if (this.type == null) {
                        // Semantic Error
                        return new ReturnType(null, new Error("Semantic", MessageFormat.format("The Type: {0} can''t be operator with Type: {1}", left.getType().name(), right.getType().name()), table.getName(), row, column));
                    }

                    return new ReturnType(this.type, String.valueOf(Integer.parseInt(left.getValue().toString()) * Integer.parseInt(right.getValue().toString())));
                case "/":
                    this.type = this.domain[left.getType().ordinal()][right.getType().ordinal()];

                    if (this.type == null) {
                        // Semantic Error
                        return new ReturnType(null, new Error("Semantic", MessageFormat.format("The Type: {0} can''t be operator with Type: {1}", left.getType().name(), right.getType().name()), table.getName(), row, column));
                    }

                    return new ReturnType(this.type, String.valueOf(Integer.parseInt(left.getValue().toString()) / Integer.parseInt(right.getValue().toString())));
                case "+":
                    this.type = this.addDomain[left.getType().ordinal()][right.getType().ordinal()];

                    if (this.type == null) {
                        // Semantic Error
                        return new ReturnType(null, new Error("Semantic", MessageFormat.format("The Type: {0} can''t be operator with Type: {1}", left.getType().name(), right.getType().name()), table.getName(), row, column));
                    }

                    if (this.type == Type.STRING) {
                        return new ReturnType(this.type, left.getValue().toString() + right.getValue().toString());
                    }

                    return new ReturnType(this.type, String.valueOf(Integer.parseInt(left.getValue().toString()) + Integer.parseInt(right.getValue().toString())));

                case "-":
                    this.type = this.domain[left.getType().ordinal()][right.getType().ordinal()];

                    if (this.type == null) {
                        // Semantic Error
                        return new ReturnType(null, new Error("Semantic", MessageFormat.format("The Type: {0} can''t be operator with Type: {1}", left.getType().name(), right.getType().name()), table.getName(), row, column));
                    }

                    return new ReturnType(this.type, String.valueOf(Integer.parseInt(left.getValue().toString()) - Integer.parseInt(right.getValue().toString())));
                default:
                    return new ReturnType(null, new Error("Semantic", MessageFormat.format("The operator: {0} doesn't arithmetic", this.op), table.getName(), row, column));
            }
        } else {
            if (right.getType() != Type.STRING) {
                return new ReturnType(right.getType(), String.valueOf(-Integer.parseInt(right.getValue().toString())));
            }

            // Semantic Error
            return new ReturnType(null, new Error("Semantic", MessageFormat.format("The Type: {0} can''t be operate with operator: {1}", right.getType().name(), this.getOp()), table.getName(), row, column));
        }
    }

    @Override
    public Object interpret(SymbolTable table) {
        return getValue(table);
    }

    @Override
    public Object compile(SymbolTable table) {
        Generator3D generator3D = Generator3D.getInstance();

        if (this.left != null) {

            Value vLeft;
            Value vRight;

            vLeft = (Value) ((Instruction) this.left).compile(table);

            if (this.right instanceof CallFunction) {
                String position = generator3D.newTemporal();
                generator3D.addExp(position, "P", "+", String.valueOf(table.getSize()));
                generator3D.setStack(position, vLeft.getValue().toString());
                table.setSize(table.getSize() + 1);
                vRight = (Value) ((Instruction) this.right).compile(table);
                table.setSize(table.getSize() - 1);
                position = generator3D.newTemporal();
                generator3D.addExp(position, "P", "+", String.valueOf(table.getSize()));
                generator3D.getStack(vLeft.getValue().toString(), position);
            } else {
                vRight = (Value) ((Instruction) this.right).compile(table);
            }


            String temp = generator3D.newTemporal();
            this.type = this.domain[vLeft.getType().ordinal()][vRight.getType().ordinal()];

            generator3D.addExp(temp, vLeft.getValue().toString(), this.op, vRight.getValue().toString());

            return new Value(this.type, temp, true);

        } else {
            Value value = (Value) ((Instruction) this.right).compile(table);
            String temp = generator3D.newTemporal();

            generator3D.addExp(temp, "0", "-", value.getValue().toString());

            return new Value(value.getType(), temp, true);
        }
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

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
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
}
