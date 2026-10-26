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
    public void enterVariabeleassignment(ICSSParser.VariabeleassignmentContext ctx) {
        VariableAssignment variableAssignment = new VariableAssignment();
        currentContainer.push(variableAssignment);
    }

    @Override
    public void enterVariabelreference(ICSSParser.VariabelreferenceContext ctx) {
        VariableReference variableReference = new VariableReference(ctx.getText());
        currentContainer.push(variableReference);
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
    public void enterSelector(ICSSParser.SelectorContext ctx) {
        if (ctx.getText().startsWith("#")) {
            Selector selector = new IdSelector(ctx.getText());
            currentContainer.push(selector);
        } else if (ctx.getText().startsWith(".")) {
            Selector selector = new ClassSelector(ctx.getText());
            currentContainer.push(selector);
        } else {
            Selector selector = new TagSelector(ctx.getText());
            currentContainer.push(selector);
        }
    }

    @Override
    public void enterProperty(ICSSParser.PropertyContext ctx) {
        PropertyName propertyName = new PropertyName(ctx.getText());
        currentContainer.push(propertyName);
    }

    @Override
    public void enterValue(ICSSParser.ValueContext ctx) {
        if (ctx.getText().startsWith("#")) {
            Literal literal = new ColorLiteral(ctx.getText());
            currentContainer.push(literal);
        } else if (ctx.getText().endsWith("px") && ctx.PLUS() == null && ctx.MUL() == null && ctx.SCALAR() == null) {
            Literal literal = new PixelLiteral(ctx.getText());
            currentContainer.push(literal);
        } else if (ctx.getText().endsWith("TRUE") || ctx.getText().endsWith("FALSE")) {
            BoolLiteral boolLiteral = new BoolLiteral(ctx.getText());
            currentContainer.push(boolLiteral);
        } else if (ctx.CAPITAL_IDENT() != null) {
            VariableReference variableReference = new VariableReference(ctx.getText());
            currentContainer.push(variableReference);
        } else if (ctx.PLUS() != null) {
            AddOperation addOperation = new AddOperation();
            currentContainer.push(addOperation);
        } else if (ctx.MUL() != null) {
            MultiplyOperation multiplyOperation = new MultiplyOperation();
            currentContainer.push(multiplyOperation);
        } else  if (ctx.SCALAR() != null) {
            ScalarLiteral scalarLiteral = new ScalarLiteral(ctx.getText());
            currentContainer.push(scalarLiteral);
        }

//        else if (ctx.add() != null) {
//            AddOperation addOperation = new AddOperation();
//            currentContainer.push(addOperation);
//        } else if (ctx.multiply() != null) {
//            MultiplyOperation multiplyOperation = new MultiplyOperation();
//            currentContainer.push(multiplyOperation);
//        }
    }

//    @Override
//    public void enterAdd(ICSSParser.AddContext ctx) {
//        AddOperation addOperation = new AddOperation();
//        currentContainer.push(addOperation);
//    }
//
//    @Override
//    public void enterMultiply(ICSSParser.MultiplyContext ctx) {
//        MultiplyOperation multiplyOperation = new MultiplyOperation();
//        currentContainer.push(multiplyOperation);
//    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        ast.root = (Stylesheet) currentContainer.pop();
    }

    @Override
    public void exitVariabeleassignment(ICSSParser.VariabeleassignmentContext ctx) {
        VariableAssignment variableAssignment;

        variableAssignment = (VariableAssignment) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(variableAssignment);
    }


    @Override
    public void exitVariabelreference(ICSSParser.VariabelreferenceContext ctx) {
        VariableReference variableReference;

        variableReference = (VariableReference) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(variableReference);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule stylerule;

        stylerule = (Stylerule) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(stylerule);
    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext ctx) {
        Selector selector;

        selector = (Selector) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(selector);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration declaration;

        declaration = (Declaration) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(declaration);
    }

    @Override
    public void exitProperty(ICSSParser.PropertyContext ctx) {
        PropertyName propertyName;

        propertyName = (PropertyName) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(propertyName);
    }

    @Override
    public void exitValue(ICSSParser.ValueContext ctx) {
        Expression expression;

        expression = (Expression) currentContainer.pop();

        ((ASTNode) currentContainer.peek()).addChild(expression);
    }

//    @Override
//    public void exitAdd(ICSSParser.AddContext ctx) {
//        AddOperation addOperation;
//
//        addOperation = (AddOperation) currentContainer.pop();
//
//        ((ASTNode) currentContainer.peek()).addChild(addOperation);
//    }
//
//    @Override
//    public void exitMultiply(ICSSParser.MultiplyContext ctx) {
//        MultiplyOperation multiplyOperation;
//
//        multiplyOperation = (MultiplyOperation) currentContainer.pop();
//
//        ((ASTNode) currentContainer.peek()).addChild(multiplyOperation);
//    }
}