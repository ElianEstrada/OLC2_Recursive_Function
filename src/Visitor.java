import Abstract.Instruction;
import Environment.RelationalOp;
import Environment.Type;
import Expression.Arithmetic;
import Expression.Identifier;
import Expression.Primitive;
import Expression.Relational;
import Instructions.*;
import grammar.GrammarBaseVisitor;
import grammar.GrammarParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

public class Visitor extends GrammarBaseVisitor<Object> {

    @Override
    public Object visitStart(GrammarParser.StartContext ctx) {
        return visit(ctx.listInstructions());
    }

    @Override
    public ArrayList<Object> visitListInstructions(GrammarParser.ListInstructionsContext ctx) {
        ArrayList<Object> instructions = new ArrayList<>();

        for (ParseTree item: ctx.instruction()) {
            instructions.add(visit(item));
        }

        return instructions;
    }

    @Override
    public Object visitInsMain(GrammarParser.InsMainContext ctx) {
        return visit(ctx.main());
    }

    @Override
    public Object visitMain(GrammarParser.MainContext ctx) {
        return new Program(ctx.RVOID().getSymbol().getLine(), ctx.RVOID().getSymbol().getCharPositionInLine(), (ArrayList<Object>) visit(ctx.listInstructions()));
    }

    @Override
    public Object visitInsDeclar(GrammarParser.InsDeclarContext ctx) {
        return visit(ctx.declaration());
    }

    @Override
    public Object visitDeclaration(GrammarParser.DeclarationContext ctx) {
        ArrayList<Object> listDeclaration = new ArrayList<>();

        for (ParseTree item: ctx.listDeclarations()) {
            listDeclaration.add(visit(item));
        }

        return new Declaration(ctx.IDEN().getSymbol().getLine(), ctx.IDEN().getSymbol().getCharPositionInLine(), (Type) visit(ctx.type()), ctx.IDEN().getText(), listDeclaration);
    }

    @Override
    public Object visitListDeclarations(GrammarParser.ListDeclarationsContext ctx) {
        if (ctx.expression() == null) {
            return ctx.IDEN().getText();
        }

        return visit(ctx.expression());
    }

    @Override
    public Type visitType(GrammarParser.TypeContext ctx) {
        return switch (ctx.getText()) {
            case "int" -> Type.INTEGER;
            default -> null;
        };
    }

    @Override
    public Object visitInsAssign(GrammarParser.InsAssignContext ctx) {
        return visit(ctx.assignment());
    }

    @Override
    public Object visitAssignment(GrammarParser.AssignmentContext ctx) {
        return new Assignmate(ctx.id.getLine(), ctx.id.getCharPositionInLine(), ctx.id.getText(), visit(ctx.expression()));
    }

    @Override
    public Object visitInsIf(GrammarParser.InsIfContext ctx) {
        return visit(ctx.if_());
    }

    @Override
    public Object visitIf(GrammarParser.IfContext ctx) {
        if (ctx.ifOptions() == null) {
            return new If(ctx.RIF().getSymbol().getLine(), ctx.RIF().getSymbol().getCharPositionInLine(), visit(ctx.expression()), (ArrayList<Object>) visit(ctx.listInstructions()), null, null);
        }

        Object moreIf = visit(ctx.ifOptions());

        if (moreIf instanceof If) {
            return new If(ctx.RIF().getSymbol().getLine(), ctx.RIF().getSymbol().getCharPositionInLine(), visit(ctx.expression()), (ArrayList<Object>) visit(ctx.listInstructions()), (Instruction) moreIf, null);
        }

        return new If(ctx.RIF().getSymbol().getLine(), ctx.RIF().getSymbol().getCharPositionInLine(), visit(ctx.expression()), (ArrayList<Object>) visit(ctx.listInstructions()), null, (ArrayList<Object>) moreIf);
    }

    @Override
    public Object visitIfOptions(GrammarParser.IfOptionsContext ctx) {
        if (ctx.if_() == null) {
            return visit(ctx.listInstructions());
        }

        return visit(ctx.if_());
    }

    @Override
    public Object visitInsPrint(GrammarParser.InsPrintContext ctx) {
        return visit(ctx.print());
    }

    @Override
    public Object visitPrint(GrammarParser.PrintContext ctx) {
        return new Print(ctx.RPRINT().getSymbol().getLine(), ctx.RPRINT().getSymbol().getCharPositionInLine(), visit(ctx.expression()));
    }

    @Override
    public Object visitInsRet(GrammarParser.InsRetContext ctx) {
        return visit(ctx.return_());
    }

    @Override
    public Object visitReturn(GrammarParser.ReturnContext ctx) {
        return new Return(ctx.RRETURN().getSymbol().getLine(), ctx.RRETURN().getSymbol().getCharPositionInLine(), visit(ctx.expression()));
    }

    @Override
    public Object visitInsFunc(GrammarParser.InsFuncContext ctx) {
        return visit(ctx.function());
    }

    @Override
    public Object visitFunction(GrammarParser.FunctionContext ctx) {
        if (ctx.parameters() == null) {
            return new Function(ctx.IDEN().getSymbol().getLine(), ctx.IDEN().getSymbol().getCharPositionInLine(), (Type) visit(ctx.type()), ctx.IDEN().getText(), new ArrayList<>(), (ArrayList<Object>) visit(ctx.listInstructions()));
        }

        return new Function(ctx.IDEN().getSymbol().getLine(), ctx.IDEN().getSymbol().getCharPositionInLine(), (Type) visit(ctx.type()), ctx.IDEN().getText(), (ArrayList<String>) visit(ctx.parameters()), (ArrayList<Object>) visit(ctx.listInstructions()));
    }

    @Override
    public Object visitParameters(GrammarParser.ParametersContext ctx) {
        ArrayList<String> listID = new ArrayList<>();

        listID.add(ctx.IDEN().getText());

        for (ParseTree item: ctx.listID()) {
            listID.add(visit(item).toString());
        }

        return listID;
    }

    @Override
    public Object visitListID(GrammarParser.ListIDContext ctx) {
        return ctx.IDEN().getText();
    }

    @Override
    public Object visitInsCall(GrammarParser.InsCallContext ctx) {
        return visit(ctx.callFunction());
    }

    @Override
    public Object visitCallFunction(GrammarParser.CallFunctionContext ctx) {
        if (ctx.values() == null) {
            return new CallFunction(ctx.IDEN().getSymbol().getLine(), ctx.IDEN().getSymbol().getCharPositionInLine(), ctx.IDEN().getText(), new ArrayList<>());
        }

        return new CallFunction(ctx.IDEN().getSymbol().getLine(), ctx.IDEN().getSymbol().getCharPositionInLine(), ctx.IDEN().getText(), (ArrayList<Object>) visit(ctx.values()));
    }

    @Override
    public Object visitValues(GrammarParser.ValuesContext ctx) {

        ArrayList<Object> listExpression = new ArrayList<>();

        listExpression.add(visit(ctx.expression()));

        for (ParseTree item: ctx.listExpression()) {
            listExpression.add(visit(item));
        }

        return listExpression;
    }

    @Override
    public Object visitListExpression(GrammarParser.ListExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitArithExp(GrammarParser.ArithExpContext ctx) {
        if (ctx.left != null) {
            return new Arithmetic(ctx.op.getLine(), ctx.op.getCharPositionInLine(), visit(ctx.left), visit(ctx.right), ctx.op.getText());
        }

        return new Arithmetic(ctx.op.getLine(), ctx.op.getCharPositionInLine(), null, visit(ctx.right), ctx.op.getText());
    }

    @Override
    public Object visitRelaExp(GrammarParser.RelaExpContext ctx) {
        RelationalOp op;

        op = switch (ctx.op.getText()) {
            case "==" -> RelationalOp.EQ;
            case "!=" -> RelationalOp.NEQ;
            case ">" -> RelationalOp.GT;
            case ">=" -> RelationalOp.GTE;
            case "<" -> RelationalOp.LT;
            case "<=" -> RelationalOp.LTE;
            default -> null;
        };

        return new Relational(ctx.op.getLine(), ctx.op.getCharPositionInLine(), visit(ctx.left), visit(ctx.right), op);
    }

    @Override
    public Object visitAgroupExp(GrammarParser.AgroupExpContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitIntExp(GrammarParser.IntExpContext ctx) {
        return new Primitive(ctx.INT().getSymbol().getLine(), ctx.INT().getSymbol().getCharPositionInLine(), Type.INTEGER, ctx.INT().getText());
    }

    @Override
    public Object visitStrExp(GrammarParser.StrExpContext ctx) {
        return new Primitive(ctx.STRING().getSymbol().getLine(), ctx.STRING().getSymbol().getCharPositionInLine(), Type.STRING, ctx.STRING().getText().substring(1, ctx.getText().length() - 1));
    }

    @Override
    public Object visitIdExp(GrammarParser.IdExpContext ctx) {
        return new Identifier(ctx.IDEN().getSymbol().getLine(), ctx.IDEN().getSymbol().getCharPositionInLine(), ctx.IDEN().getText());
    }
}
