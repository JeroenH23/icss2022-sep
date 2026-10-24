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
        ASTNode stylesheet = new Stylesheet();
        currentContainer.push(stylesheet);
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        ASTNode stylerule = new Stylerule();
        currentContainer.push(stylerule);
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode declaration = new Declaration();
        currentContainer.push(declaration);
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        ast.root = (Stylesheet) currentContainer.pop();
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule stylerule;

        stylerule = (Stylerule) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(stylerule);
    }


    @Override
    public void enterSelector(ICSSParser.SelectorContext ctx) {
        Selector selector = new TagSelector("");
        currentContainer.push(selector);
    }

    @Override
    public void enterProperty(ICSSParser.PropertyContext ctx) {
        PropertyName propertyName = new PropertyName();
        currentContainer.push(propertyName);
    }

    @Override
    public void enterValue(ICSSParser.ValueContext ctx) {
        PixelLiteral pixelLiteral = new PixelLiteral("100px");
        currentContainer.push(pixelLiteral);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration declaration;

        declaration = (Declaration) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(declaration);
    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext ctx) {
        TagSelector selector;

        selector = (TagSelector) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(selector);
    }

    @Override
    public void exitProperty(ICSSParser.PropertyContext ctx) {
        PropertyName propertyName;

        propertyName = (PropertyName) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(propertyName);
    }

    @Override
    public void exitValue(ICSSParser.ValueContext ctx) {
       PixelLiteral pixelLiteral;

       pixelLiteral = (PixelLiteral) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(pixelLiteral);
    }
}