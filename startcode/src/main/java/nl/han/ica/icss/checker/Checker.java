package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.LinkedList;

public class Checker {

    private LinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        enterStylesheet(ast.root);
    }

    public void enterStylesheet(Stylesheet node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof Stylerule) {
                enterStyleRule((Stylerule) child);
            }
        }
    }

    public void enterStyleRule(Stylerule node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof Declaration) {
                enterDeclaration((Declaration) child);
            }
        }
    }

    public void enterDeclaration(Declaration node) {
        node.getChildren();

        for (ASTNode child : node.getChildren()) {
            if (child instanceof PixelLiteral) {
                if (node.property.name.equals("color") || node.property.name.equals("background-color")) {
                    node.setError("Pixels kunnen geen kleur zijn!");
                }
            }

            if (child instanceof ColorLiteral) {
                if (node.property.name.equals("width")) {
                    node.setError("Kleur kan geen pixels zijn");
                }
            }

            if (child instanceof AddOperation) {
                enterAddOperation((AddOperation) child);
            }

            if (child instanceof VariableReference) {
                if (node.getChildren().get(1) instanceof VariableReference) {

                }
            }
        }
    }

    private void enterAddOperation(AddOperation node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof MultiplyOperation) {
                enterMultiplyOperation((MultiplyOperation) child);
            }
        }
    }

    private void enterMultiplyOperation(MultiplyOperation node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof ColorLiteral) {
                node.setError("Je mag niet een kleur gebruiken bij een berekening");
            }
        }
    }
}