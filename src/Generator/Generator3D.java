package Generator;

import java.text.MessageFormat;

public class Generator3D {

    private final StringBuilder codeIn;
    private int temporal;
    private int label;
    private boolean existStr;
    private boolean isFunction;
    private boolean isPot;
    private final StringBuilder functions;
    private static Generator3D myGenerator;

    private Generator3D() {
        this.codeIn = new StringBuilder();
        this.functions = new StringBuilder();
        this.temporal = 0;
        this.label = 0;
        this.existStr = false;
        this.isFunction = false;
    }

    public static Generator3D getInstance() {
        if (myGenerator == null) {
            myGenerator = new Generator3D();
        }

        return myGenerator;
    }

    public String newTemporal() {
        return MessageFormat.format("t{0}", this.temporal++);
    }

    public String newLabel() {
        return MessageFormat.format("L{0}", this.label++);
    }

    public void inFunction(String code) {
        if (isFunction) {
            functions.append(code);
        } else {
            codeIn.append(code);
        }
    }

    public void addGoto(String label) {
        inFunction(MessageFormat.format("goto {0};\n", label));
    }

    public void printLabel(String label) {
        inFunction(MessageFormat.format("{0}:\n", label));
    }

    public void printStr(){
        if (existStr) {
            return;
        }

        existStr = true;
        //this.isFunction = true;

        newFunc("printStr");
        String out = newLabel();
        String check = newLabel();

        String tmpP = newTemporal();
        String tmpH = newTemporal();

        addExp(tmpP, "P", "+", "1");
        getStack(tmpH, tmpP);

        String tmp = newTemporal();
        printLabel(check);
        getHeap(tmp, tmpH);
        addIf(tmp, "==", "-1", out);
        addPrint("c", "int", tmp);
        addExp(tmpH, tmpH, "+", "1");
        addGoto(check);
        printLabel(out);
        endFun();
        //this.isFunction = false;
    }

    public void newFunc(String name) {
        this.isFunction = true;
        functions.append("void ").append(name).append("() {\n");
    }

    public void endFun() {
        functions.append("return;\n}\n");
        this.isFunction = false;
    }

    public void addExp(String result, String left, String op, String right) {
        inFunction(MessageFormat.format("{0} = {1}{2}{3};\n", result, left, op, right));
    }

    public void getStack(String temporal, String position) {
        inFunction(MessageFormat.format("{0} = STACK[(int){1}];\n", temporal, position));
    }

    public void setStack(String position, String value) {
        inFunction(MessageFormat.format("STACK[(int){0}] = {1};\n", position, value));
    }

    public void getHeap(String temporal, String position) {
        inFunction(MessageFormat.format("{0} = HEAP[(int){1}];\n", temporal, position));
    }

    public void setHeap(String position, String value){
        inFunction(MessageFormat.format("HEAP[(int){0}] = {1};\n", position, value));
    }

    public void nextHeap() {
        inFunction("H = H + 1;\n");
    }

    public void addIf(String left, String op, String right, String label) {
        inFunction(MessageFormat.format("if ({0} {1} {2}) goto {3};\n", left, op, right, label));
    }

    public void newEnvironment(String size) {
        inFunction(MessageFormat.format("P = P + {0};\n", size));
    }

    public void getEnvironment(String size) {
        inFunction(MessageFormat.format("P = P - {0};\n", size));
    }

    public void getFunc(String name) {
        inFunction(MessageFormat.format("{0}();\n", name));
    }

    public void addPrint(String type, String cast, Object value) {
        inFunction(MessageFormat.format("printf(\"%{0}\", ({1}){2});\n", type, cast, value.toString()));
    }

    public String initHeader(){
        StringBuilder temporals = new StringBuilder();

        for (int i = 0; this.temporal != 0 && i <= this.temporal; i++) {
            temporals.append("t").append(String.valueOf(i)).append(i < this.temporal ? ", ": ";\n");
        }

        return "/*---------------HEADER---------------*/\n" +
                "#include <stdio.h>\n" +
                "float STACK[30101999];\n" +
                "float HEAP[30101999];\n" +
                "float P;\n" +
                "float H;\n" +
                (temporal != 0 ? "float " + temporals : "\n");

    }

    public String getCode() {
        return initHeader() + "\n" +
                this.functions.toString() + "\n" +
                "/*---------------MAIN---------------*/\n" +
                "void main() {\n" +
                "P = 1;\n" +
                "H = 0;\n" +
                this.codeIn.toString() + "\n" +
                "return;\n" +
                "}";
    }

    public void deleteTemporal(){
        this.temporal--;
    }

    public void clear() {
        myGenerator = null;
    }

}
