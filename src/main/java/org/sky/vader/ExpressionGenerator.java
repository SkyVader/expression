package org.sky.vader;

import org.sky.vader.ExpressionTree.Expression;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import static org.sky.vader.Operation.*;


/**
 * Генерация дерева выражения методом эквивалентных преобразований
 */
public final class ExpressionGenerator {

    private final int expressionValue;
    private final int complexity;
    private final Random random = new Random();
    private final Operation[] operations = Operation.values();

    /**
     *
     * @param expressionValue - итоговое значение выражения,
     *                        оно должно получиться при вычислении выражения
     * @param complexity - сложность выражения.
     *                   На данный момент число отражает количество операций в выражении
     */
    public ExpressionGenerator(int expressionValue, int complexity) {
        this.expressionValue = expressionValue;
        this.complexity = complexity;
    }

    public String expression(){
        return generate().toString();
    }

    private ExpressionTree generate() {
        ExpressionTree tree = initRoot();
        Queue<ExpressionTree> leafs = new ArrayDeque<>();
        leafs.add(tree);

        while ( !leafs.isEmpty()) {
            if(leafs.size() >= complexity) break;

            var node = leafs.poll();
            if(isScalar(node)) continue;

            leafs.add(convertionLeftNodeToExpression(node));
            leafs.add(convertionRightNodeToExpression(node));
        }
        return tree;
    }

    private ExpressionTree initRoot() {
        return createExpression(expressionValue);
    }

    private ExpressionTree convertionRightNodeToExpression(ExpressionTree node) {
        node.setRight(changeScalarToExpression(node.getRight()));
        return node.getRight();
    }

    private ExpressionTree convertionLeftNodeToExpression(ExpressionTree node) {
        node.setLeft(changeScalarToExpression(node.getLeft()));
        return node.getLeft();
    }

    private ExpressionTree changeScalarToExpression(ExpressionTree node) {
        if(isScalar(node)){ //TODO вообще говоря, здесь не скаляр оказаться не должен, но кто его знает
            var l = createExpression(((ExpressionTree.Scalar)node).value());
            node.setLeft(l);
            return l;
        }
        return node;
    }

    private boolean isScalar(ExpressionTree node) {
        return node.type().equals("scalar");
    }

    private ExpressionTree createExpression(int value) {
        if(value == 0) {
            return zeroExpression(splitter(100));
        } else if(value == 1){
            return oneExpression(splitter(100));
        } else {
            switch (randomOperation()) {
                case PLUS:
                    return plus(value);
                case MINUS:
                    return minus(value);
                case DIVIDE:
                    return divide(value);
                case MULTIPLE:
                    return multiple(value);
            }
        }
        return ExpressionTree.Scalar.of(value);
    }

    private ExpressionTree zeroExpression(int value) {
        return new Expression(MINUS, value, value);
    }

    private ExpressionTree oneExpression(int value) {
        return new Expression(DIVIDE, value, value);
    }

    private ExpressionTree plus(int value) {
        int rightValue = splitter(value/2);
        return new Expression(PLUS, value - rightValue, rightValue);
    }

    private ExpressionTree minus(int value) {
        int rightValue = splitter(value/2);
        return new Expression(MINUS, value + rightValue, rightValue);
    }

    private ExpressionTree divide(int value) {
        int rightValue = splitter(10);
        return new Expression(DIVIDE, value*rightValue, rightValue);
    }

    private ExpressionTree multiple(int value) {
        int rightValue = gcd(value, splitter(value));
        return new Expression(MULTIPLE, value/rightValue, rightValue);
    }

    private int splitter(int bound) {
        return bound > 0 ? random.nextInt(bound) + (bound/2) : expressionValue;
    }

    private int gcd(int value, int splitter) {
        int r = 0;
        for (int i = 1; i <= Math.max(value, splitter); i++) {
            if(value % i == 0 && splitter % i == 0){
                r = i;
            }
        }
        return r;
    }

    private Operation randomOperation() {
        return operations[random.nextInt(4)];
    }

    public static void main(String[] args) {
        var v = new ExpressionGenerator(0, 50);
        System.out.println(v.expression());
    }
}

