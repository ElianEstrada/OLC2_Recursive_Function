import Abstract.Instruction;
import Environment.Error;
import Environment.SymbolTable;
import Environment.Tree;
import Generator.Generator3D;
import Instructions.Function;
import Instructions.Program;
import grammar.GrammarLexer;
import grammar.GrammarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        StringBuilder content = new StringBuilder("print(\"Hola\")");

        JFileChooser jFileChooser = new JFileChooser("/media/elian_estrada/Documents/Elian/2023/Usac/Primer_Semestre/Conferencia/RecursividadC3D/testFile");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Elian", "el"));

        int result = jFileChooser.showOpenDialog(null);

        if (result != JFileChooser.CANCEL_OPTION) {
            File file = jFileChooser.getSelectedFile();

            if ((file == null) || (file.getName().equals(""))) {
                System.out.println("Archivo invalido");
            } else {
                Scanner scanner = new Scanner(file);
                content.delete(0, content.length());

                while (scanner.hasNext()) {
                    content.append(scanner.nextLine()).append("\n");
                }
            }
        }

        CharStream charStream = CharStreams.fromString(content.toString());

        GrammarLexer grammarLexer = new GrammarLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(grammarLexer);

        GrammarParser grammarParser = new GrammarParser(commonTokenStream);
        GrammarParser.StartContext startContext = grammarParser.start();

        Visitor visitor = new Visitor();
        SymbolTable globalTable = new SymbolTable(null);
        globalTable.setName("Global");

        Tree tree = Tree.getInstance();
        tree.setInstructions((ArrayList<Instruction>) visitor.visit(startContext));
        tree.setGlobalTable(globalTable);

        Object resultIns;
        Error error;

        for (Instruction item: tree.getInstructions()) {

            if (item instanceof Function) {
                tree.addFunction(item);
                continue;
            }

            if (!(item instanceof Program)) {
                // Semantic Error
                error = new Error("Semantic", MessageFormat.format("The instruction: {0} out of main", item.getClass().getName()), globalTable.getName(), item.getRow(), item.getColumn());
                tree.getErrors().add(error);
                tree.updateConsole(error.toString());
                continue;
            }

            resultIns = item.interpret(globalTable);

            if (resultIns instanceof Error) {
                tree.getErrors().add((Error) resultIns);
                tree.updateConsole(resultIns.toString());
            }
        }

        //System.out.println("/*------------OUT INTERPRET------------*/\n" + tree.getConsole().toString());

        if (tree.getErrors().size() != 0) {
            System.out.println("no se puede compilar porque hay errores");
            return;
        }

        for (Instruction item: tree.getInstructions()) {
            item.compile(globalTable);
        }

        Generator3D generator3D = Generator3D.getInstance();
        System.out.println("/*------------OUT COMPILER------------*/\n" + generator3D.getCode());

    }
}