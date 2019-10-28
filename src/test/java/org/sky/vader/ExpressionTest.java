package org.sky.vader;

import org.junit.jupiter.api.Test;
import org.sky.vader.ExpressionTree.Expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.sky.vader.Operation.*;

class ExpressionTest {

    @Test
    public void simplePlus(){
        var expression = new Expression(PLUS, 4, 5);

        assertEquals("4 + 5", expression.toString());
    }

    @Test
    public void rightExpression(){
        var expression = new Expression(PLUS,
                new Expression(PLUS, 3, 4), 5);

        assertEquals("3 + 4 + 5", expression.toString());
    }

    @org.junit.jupiter.api.Test
    public void leftExpression(){
        var expression = new Expression(PLUS,
                3,
                new Expression(PLUS, 4, 5));
        assertEquals("3 + 4 + 5", expression.toString());
    }

    @Test
    public void devide(){
        var expression = new Expression(DIVIDE,
                new Expression(PLUS, 1,2),
                new Expression(MINUS, 1, 2));

        assertEquals("(1 + 2)/(1 - 2)", expression.toString());
    }

    @Test
    public void minus(){
        var expression = new Expression(PLUS,
                new Expression(MINUS,
                    new Expression(PLUS, 1,2),
                    3),
                4);

        assertEquals("1 + 2 - 3 + 4", expression.toString());
    }

    @Test
    public void minusWithBraces(){
        var expression = new Expression(MINUS,
                new Expression(PLUS, 1, 2),
                new Expression(PLUS, 3,4));

        assertEquals("1 + 2 -(3 + 4)", expression.toString());
    }

    @Test
    public void divideWithBraces(){
        var expression = new Expression(DIVIDE,
                new Expression(MINUS, 1, 2),
                new Expression(MULTIPLE, 3, 4));

        assertEquals("(1 - 2)/(3 * 4)", expression.toString());
    }

    @Test
    public void divideMultiply(){
        var expression = new Expression(MULTIPLE,
                new Expression(DIVIDE, 3, 4),
                4);

        assertEquals("3 / 4 * 4", expression.toString());
    }

}