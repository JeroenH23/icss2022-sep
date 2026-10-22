package nl.han.ica.icss.parser;

import java.util.Stack;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.stack.HANStack;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private final AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private final HANStack currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack();
	}
    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet stylesheet = new Stylesheet();
        currentContainer.push(stylesheet);
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule styleRule = new Stylerule();
        currentContainer.push(styleRule);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule styleRule = new Stylerule();

        styleRule.addChild((ASTNode) currentContainer.peek());
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet styleSheet = new Stylesheet();

        ast.root = styleSheet;

        styleSheet.addChild((ASTNode) currentContainer.peek());

//        styleSheet.addChild((ASTNode) currentContainer.peek());
    }

//    @Override
//    public void enterSelector(ICSSParser.SelectorContext ctx) {
//        ASTNode selector = new TagSelector("");
//        currentContainer.push(selector);
//    }
//
//    @Override
//    public void exitSelector(ICSSParser.SelectorContext ctx) {
//        ASTNode selector;
//
//        selector = (ASTNode)currentContainer.pop();
//
//        selector.addChild(selector);
//    }
//
//    @Override
//    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
//        ASTNode declaration = new Declaration();
//        currentContainer.push(declaration);
//    }
//
//    @Override
//    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
//        ASTNode declaration;
//
//        declaration = (ASTNode)currentContainer.pop();
//
//        declaration.addChild(declaration);
//    }


}