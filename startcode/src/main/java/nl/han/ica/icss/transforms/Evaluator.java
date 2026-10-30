package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private LinkedList<HashMap<String, ExpressionType>> variablesTypes;

    public Evaluator() {
        //variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variablesTypes = new LinkedList<>();
        applyStylesheet(ast.root);
    }

    private void applyStylesheet(Stylesheet node) {
        //Ophalen van de styleRule

        for (ASTNode child : node.getChildren()) {
            if (child instanceof Stylerule) {
                applyStylerule((Stylerule) child);
            }

            if (child instanceof IfClause) {
                applyIfClause((IfClause) child);
            }

            if (child instanceof ElseClause) {
                applyElseClause((ElseClause) child);
            }
        }
    }

    private void applyStylerule(Stylerule node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof Declaration) {
                applyDeclaration((Declaration) child);
            }
        }
    }

    private void applyIfClause(IfClause node) {
        evalIfClause(node.elseClause);
    }

    private void applyElseClause(ElseClause node) {
       evalElseClause(node.body);
    }


    private void applyDeclaration(Declaration node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof AddOperation) {
                applyAddOperation((AddOperation) child);
            }
        }
        // Dit is de transformatie
//        node.expression = evalExpression(node.expression);
    }

    private void applyAddOperation(AddOperation node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof MultiplyOperation) {
                applyMultiplyOperation((MultiplyOperation) child);
            }
        }
//        node.lhs = evalAddExpression(node.lhs);
    }

    private void applyMultiplyOperation(MultiplyOperation node) {
        node.rhs = evalMultiplyOperation(node.rhs);
    }

    private Expression evalAddExpression(Expression expression) {
        if (expression instanceof VariableReference) {
            return (Expression) expression;
        }
        if (expression instanceof AddOperation) {
            AddOperation operation = (AddOperation) expression;

            PixelLiteral left = (PixelLiteral) evalAddExpression(operation.lhs);
            PixelLiteral right = (PixelLiteral) evalAddExpression(operation.rhs);

            // Hier wordt de nieuwe knoop aangemaakt waar de nieuwe resultaat wordt weergegeven
            return new PixelLiteral(left.value + right.value);
        }
        return expression;
    }

    private Expression evalMultiplyOperation(Expression expression) {
        if (expression instanceof ScalarLiteral) {
            return (Expression) expression;
        }

        if (expression instanceof PixelLiteral) {
            return (Expression) expression;
        }

        if (expression instanceof MultiplyOperation) {
            MultiplyOperation operation = (MultiplyOperation) expression;

            ScalarLiteral left = (ScalarLiteral) evalMultiplyOperation(operation.lhs);
            PixelLiteral right = (PixelLiteral) evalMultiplyOperation(operation.rhs);

            return new PixelLiteral(left.value + right.value);
        }
        return expression;
    }

    private Expression evalIfClause() {


//            Expression operation = (Expression) conditionalExpression.getChildren().get(0);
//
//            PixelLiteral left = evalIfClause(operation.lhs);
//            PixelLiteral right = evalIfClause(operation.rhs);
//
//            // Hier wordt de nieuwe knoop aangemaakt waar de nieuwe resultaat wordt weergegeven
//            return new PixelLiteral(left.value + right.value);

        return null;
    }

    private void evalElseClause(ArrayList<ASTNode> body) {

    }


//    public int eval(ExpressionNode node) {
//        if (node instanceof AddNode) {
//            return eval(node.left) + eval(node.right);
//        } else if (node instanceof ValueNode) {
//            return node.value;
//        }
//        return 0;
//    }

//    private Expression evalExpression(Expression expression) {
//
//        if (expression instanceof PixelLiteral) {
//            return (Literal)expression;
//        }
//        if (expression instanceof AddOperation) {
//            AddOperation operation = (AddOperation)expression;
//
//            PixelLiteral left = evalExpression(operation.lhs);
//            PixelLiteral right = evalExpression(operation.rhs);
//
//            // Hier wordt de nieuwe knoop aangemaakt waar de nieuwe resultaat wordt weergegeven
//            return new PixelLiteral(left.value + right.value);
//        }
//    }


}
