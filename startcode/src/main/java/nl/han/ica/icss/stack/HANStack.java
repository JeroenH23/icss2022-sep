package nl.han.ica.icss.stack;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class HANStack implements IHANStack {
    private final List<ASTNode> nodes;

    public HANStack() {
        nodes = new ArrayList<>();
    }

    @Override
    public void push(Object value) {
        nodes.add((ASTNode) value);
    }

    @Override
    public Object pop() {
        return nodes.remove(nodes.size()-1);
    }

    @Override
    public Object peek() {
        return nodes.isEmpty() ? null : nodes.get(nodes.size()-1);
    }
}
