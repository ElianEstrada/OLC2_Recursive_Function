package Instructions;

import Abstract.Instruction;
import Abstract.Value;
import Environment.*;
import Environment.Error;
import Generator.Generator3D;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Stack;

public class Declaration extends Instruction {

    private String id;
    private Type type;
    private ArrayList<Object> listDeclaration;

    public Declaration(int row, int column, Type type, String id, ArrayList<Object> listDeclaration) {
        super(row, column);
        setType(type);
        setId(id);
        setListDeclaration(listDeclaration);
    }

    @Override
    public ReturnType getValue(SymbolTable table) {
        return null;
    }

    @Override
    public Object interpret(SymbolTable table) {

        this.tableCompile = table;

        ReturnType value;
        Object result;

        if (this.listDeclaration.size() == 0) {
            // Possible Semantic Error
            value = this.type.getDefaultValue().getValue(table);
            return table.addSymbol(new Symbol(this.id, this.type, value.getValue(), table.getName(), row, column));
        }

        Stack<String> identifiers = new Stack<>();
        ArrayList<Error> errors = new ArrayList<>();
        boolean flag = true;

        for (Object item: this.listDeclaration) {
            if (item instanceof String) {
                value = this.type.getDefaultValue().getValue(table);
                if (identifiers.isEmpty()) {
                    identifiers.push(item.toString());

                    if (flag) {
                        result = table.addSymbol(new Symbol(this.id, this.type, value.getValue(), table.getName(), row, column));

                        if (result != null) {
                            // Semantic Error
                            errors.add((Error) result);
                            continue;
                        }

                        flag = false;
                    }
                    continue;
                }

                result = table.addSymbol(new Symbol(identifiers.pop(), this.type, value.getValue(), table.getName(), row, column));

                identifiers.push(item.toString());

                if (result != null) {
                    // Semantic Error
                    errors.add((Error) result);
                }
            } else {
                value = ((Instruction) item).getValue(table);

                if (value.getType() == null) {
                    // Semantic Error
                    errors.add((Error) value.getValue());
                    continue;
                }

                if (this.type != value.getType()) {
                    // Semantic Error
                    errors.add(new Error("Semantic", MessageFormat.format("The type: {0} can't be assign to variable of type: {1}", value.getType().name(), this.type), table.getName(), row, column));
                    continue;
                }

                if (identifiers.empty()) {
                    result = table.addSymbol(new Symbol(this.id, this.type, value.getValue(), table.getName(), row, column));

                    if (result != null) {
                        // Semantic Error
                        errors.add((Error) result);
                    }

                    flag = false;
                    continue;
                }

                result = table.addSymbol(new Symbol(identifiers.pop(), this.type, value.getValue(), table.getName(), row, column));

                if (result != null) {
                    // Semantic error
                    errors.add((Error) result);
                }
            }
        }

        if (!identifiers.empty()) {
            value = this.type.getDefaultValue().getValue(table);
            result = table.addSymbol(new Symbol(identifiers.pop(), this.type, value.getValue(), table.getName(), row, column));

            if (result != null) {
                // Semantic Error
                errors.add((Error) result);
            }
        }

        Tree tree = Tree.getInstance();
        tree.getErrors().addAll(errors);

        for (Error item: tree.getErrors()) {
            tree.updateConsole(item.toString());
        }

        return null;

    }

    @Override
    public Object compile(SymbolTable table) {
        table = this.tableCompile;
        Generator3D generator3D = Generator3D.getInstance();

        Symbol symbol;
        Value value;
        String temp;
        Stack<String> identifiers = new Stack<>();
        boolean flag = true;

        if (this.listDeclaration.size() == 0) {
            temp = generator3D.newTemporal();
            symbol = table.getSymbol(this.id);

            generator3D.addExp(temp, "P", "+", String.valueOf(symbol.getPosition()));
            this.type.getDefaultValue().type = symbol.getType();
            value = (Value) this.type.getDefaultValue().compile(table);

            generator3D.setStack(temp, value.getValue().toString());
        }

        for (Object item: this.listDeclaration) {
            temp = generator3D.newTemporal();

            if (item instanceof String) {
                if (identifiers.empty()) {
                    identifiers.push(item.toString());

                    if (flag) {
                        symbol = table.getSymbol(this.id);
                        generator3D.addExp(temp, "P", "+", String.valueOf(symbol.getPosition()));
                        this.type.getDefaultValue().type = symbol.getType();
                        flag = false;
                    } else {
                        generator3D.deleteTemporal();
                        continue;
                    }
                } else {
                    symbol = table.getSymbol(identifiers.pop());
                    identifiers.push(item.toString());
                    generator3D.addExp(temp, "P", "+", String.valueOf(symbol.getPosition()));
                    this.type.getDefaultValue().type = symbol.getType();
                }

                value = (Value) this.type.getDefaultValue().compile(table);

                generator3D.setStack(temp, value.getValue().toString());

                this.type.getDefaultValue().setFalseLabel(null);
                this.type.getDefaultValue().setTrueLabel(null);
            } else {
                if (identifiers.empty()) {
                    symbol = table.getSymbol(this.id);
                    generator3D.addExp(temp, "P", "+", String.valueOf(symbol.getPosition()));
                    flag = false;
                } else {
                    symbol = table.getSymbol(identifiers.pop());
                    generator3D.addExp(temp, "P", "+", String.valueOf(symbol.getPosition()));
                }

                value = (Value) ((Instruction) item).compile(table);

                generator3D.setStack(temp, value.getValue().toString());
            }
        }

        if (!identifiers.empty()) {
            temp = generator3D.newTemporal();
            symbol = table.getSymbol(identifiers.pop());
            generator3D.addExp(temp, "P", "+", String.valueOf(symbol.getPosition()));
            this.type.getDefaultValue().type = symbol.getType();

            value = (Value) this.type.getDefaultValue().compile(table);

            generator3D.setStack(temp, value.getValue().toString());
        }

        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<Object> getListDeclaration() {
        return listDeclaration;
    }

    public void setListDeclaration(ArrayList<Object> listDeclaration) {
        this.listDeclaration = listDeclaration;
    }
}
