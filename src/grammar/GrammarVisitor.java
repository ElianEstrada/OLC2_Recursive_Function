// Generated from /media/elian_estrada/Documents/Elian/2023/Usac/Primer_Semestre/Conferencia/RecursividadC3D/Grammar.g4 by ANTLR 4.10.1
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(GrammarParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#listInstructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListInstructions(GrammarParser.ListInstructionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insDeclar}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsDeclar(GrammarParser.InsDeclarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insAssign}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsAssign(GrammarParser.InsAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insIf}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsIf(GrammarParser.InsIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insPrint}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsPrint(GrammarParser.InsPrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insFunc}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsFunc(GrammarParser.InsFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insMain}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsMain(GrammarParser.InsMainContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insCall}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsCall(GrammarParser.InsCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insRet}
	 * labeled alternative in {@link GrammarParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsRet(GrammarParser.InsRetContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(GrammarParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#listDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListDeclarations(GrammarParser.ListDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(GrammarParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(GrammarParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(GrammarParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#ifOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfOptions(GrammarParser.IfOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(GrammarParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#return}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(GrammarParser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(GrammarParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#callFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallFunction(GrammarParser.CallFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(GrammarParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by the {@code values}
	 * labeled alternative in {@link GrammarParser#instructioninstructioninstructioninstructioninstructioninstructioninstructioninstructionexpressionexpressionexpressionexpressionexpressionexpressionexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValues(GrammarParser.ValuesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#listID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListID(GrammarParser.ListIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#listExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpression(GrammarParser.ListExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(GrammarParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by the {@code agroupExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgroupExp(GrammarParser.AgroupExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExp(GrammarParser.IdExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExp(GrammarParser.FuncExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code strExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrExp(GrammarParser.StrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithExp(GrammarParser.ArithExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relaExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelaExp(GrammarParser.RelaExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intExp}
	 * labeled alternative in {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExp(GrammarParser.IntExpContext ctx);
}