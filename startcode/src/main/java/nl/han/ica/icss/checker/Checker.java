package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
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
        enterStyleRule(((Stylerule)node.getChildren().get(0)));
    }

    private void enterStyleRule(Stylerule node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof Declaration) {
                enterDeclaration((Declaration) child);
            }
        }
    }

    private void enterDeclaration(Declaration node) {
        node.getChildren();

        for (ASTNode child : node.getChildren()) {
            if (child instanceof PixelLiteral) {
                if (node.property.name.equals("color")) {
                    node.setError("Pixels kunnen geen kleur zijn!");
                }
            }

            if (child instanceof ColorLiteral) {
                if (node.property.name.equals("width")) {
                    node.setError("test");
                }
            }
        }
    }
}