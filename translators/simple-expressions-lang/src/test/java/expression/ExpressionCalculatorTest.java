package expression;

import example.ExpressionCalculator;
import org.junit.Assert;
import org.junit.Test;

public class ExpressionCalculatorTest {

    private static final double DELTA = 0.001;

    @Test
    public void testNumber() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(5, c.calculate("5"), DELTA);
        Assert.assertEquals(42, c.calculate("42"), DELTA);
    }

    @Test
    public void testAdd() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(6, c.calculate("2 + 4"), DELTA);
        Assert.assertEquals(7, c.calculate("5 + 2"), DELTA);
        Assert.assertEquals(10, c.calculate("1 + 2 + 3 + 4"), DELTA);
    }

    @Test
    public void testSub() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(7, c.calculate("10 - 3"), DELTA);
        Assert.assertEquals(4, c.calculate("6 - 2"), DELTA);
        Assert.assertEquals(7, c.calculate("10 - 1 - 2"), DELTA);
    }

    @Test
    public void testMul() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(6, c.calculate("2 * 3"), DELTA);
        Assert.assertEquals(20, c.calculate("4 * 5"), DELTA);
        Assert.assertEquals(24, c.calculate("1 * 2 * 3 * 4"), DELTA);
    }

    @Test
    public void testDiv() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(2, c.calculate("12 / 6"), DELTA);
        Assert.assertEquals(1.5, c.calculate("3 / 2"), DELTA);
        Assert.assertEquals(4, c.calculate("24 / 3 / 2"), DELTA);
    }

    @Test
    public void testParentheses() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(9, c.calculate("(1 + 2) * 3"), DELTA);
        Assert.assertEquals(3, c.calculate("12 / (3 + 1)"), DELTA);
    }

    @Test
    public void testExpression() {
        ExpressionCalculator c = new ExpressionCalculator();
        Assert.assertEquals(9, c.calculate("(1 + 2) * 3"), DELTA);
        Assert.assertEquals(3, c.calculate("12 / (3 + 1)"), DELTA);
    }
}
