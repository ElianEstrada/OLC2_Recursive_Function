package Environment;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    /*-----------------Interpret-----------------*/
    private static ArrayList<Symbol> variables;
    private HashMap<String, Symbol> table;
    private SymbolTable prev;
    private String name;


    /*-----------------Translate-----------------*/
    private int size;
    private String exitLabel;
    private String cycleLabel;
    private String type;
    private String valueRet;


    public SymbolTable(SymbolTable prev) {
        setPrev(prev);
        setTable(new HashMap<String, Symbol>());
    }

    public SymbolTable(SymbolTable prev, String name) {
        setPrev(prev);
        setTable(new HashMap<String, Symbol>());
        setName(name);
        setSize(0);
        setExitLabel("");
        setCycleLabel("");
        setType("");
        setValueRet("");
    }

    public Object addSymbol(Symbol newSymbol) {
        if (table.containsKey(newSymbol.getId().toLowerCase())) {
            //Semantic Error
            return new Error("Semantic", MessageFormat.format("This variable {0}, already exist in current context", newSymbol.getId()), getName(), newSymbol.getRow(), newSymbol.getColumn());
        }
        newSymbol.setPosition(this.getTotalSize());
        table.put(newSymbol.getId().toLowerCase(), newSymbol);
        this.size++;

        return null;
    }

    public Symbol getSymbol(String id) {
        SymbolTable currentTable = this;

        while (currentTable != null) {
            if(currentTable.getTable().containsKey(id.toLowerCase())){
                return currentTable.getTable().get(id.toLowerCase());
            }
            currentTable = currentTable.getPrev();
        }

        return null;
    }

    public Object updateSymbol (Symbol symbol) {
        SymbolTable currentTable = this;
        Symbol oldSymbol;

        while (currentTable != null) {
            if(currentTable.getTable().containsKey(symbol.getId())){
                oldSymbol = currentTable.getTable().get(symbol.getId());

                if (oldSymbol.getType().equals(symbol.getType())){
                    oldSymbol.setValue(symbol.getValue());
                    return null;
                } else {
                    //Semantic Error
                    return new Error("Semantic", MessageFormat.format("Can't assign value of type: {0} in a variable of type: {1}", symbol.getType().name(), oldSymbol.getType().name()), this.name, symbol.getRow(), symbol.getColumn());
                }
            }

            currentTable = currentTable.getPrev();
        }

        return new Error("Semantic", MessageFormat.format("The id: {0} does not exist in current context", symbol.getId()), this.name, symbol.getRow(), symbol.getColumn());
    }

    public static ArrayList<Symbol> getVariables() {
        return variables;
    }

    public static void setVariables(ArrayList<Symbol> variables) {
        SymbolTable.variables = variables;
    }

    public HashMap<String, Symbol> getTable() {
        return table;
    }

    public void setTable(HashMap<String, Symbol> table) {
        this.table = table;
    }

    public SymbolTable getPrev() {
        return prev;
    }

    public void setPrev(SymbolTable prev) {
        this.prev = prev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getExitLabel() {
        return exitLabel;
    }

    public void setExitLabel(String exitLabel) {
        this.exitLabel = exitLabel;
    }

    public String getCycleLabel() {
        return cycleLabel;
    }

    public void setCycleLabel(String cycleLabel) {
        this.cycleLabel = cycleLabel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueRet() {
        return valueRet;
    }

    public void setValueRet(String valueRet) {
        this.valueRet = valueRet;
    }

    public int getTotalSize() {
        SymbolTable current = this;
        int total = 0;
        while (current != null) {
            total += current.size;
            current = current.prev;
        }

        return total;
    }

}
