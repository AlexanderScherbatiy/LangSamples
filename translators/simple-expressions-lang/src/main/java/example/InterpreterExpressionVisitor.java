package example;

import expression.ExpressionBaseVisitor;
import expression.ExpressionParser;

public class InterpreterExpressionVisitor extends ExpressionBaseVisitor<Double> {

    @Override
    public Double visitRoot(ExpressionParser.RootContext ctx) {
        return super.visit(ctx.expr());
    }

    @Override
    public Double visitMulDiv(ExpressionParser.MulDivContext ctx) {
        double v1 = visit(ctx.expr(0));
        double v2 = visit(ctx.expr(1));
        switch (ctx.op.getType()) {
            case ExpressionParser.MUL:
                return v1 * v2;
            case ExpressionParser.DIV:
                return v1 / v2;
            default:
                throw new RuntimeException(
                        "Unknown Mul/Div operation: " + ctx.op.getText());
        }
    }

    @Override
    public Double visitAddSub(ExpressionParser.AddSubContext ctx) {
        double v1 = visit(ctx.expr(0));
        double v2 = visit(ctx.expr(1));
        switch (ctx.op.getType()) {
            case ExpressionParser.PLUS:
                return v1 + v2;
            case ExpressionParser.MINUS:
                return v1 - v2;
            default:
                throw new RuntimeException(
                        "Unknown Plus/Minus operation: " + ctx.op.getText());
        }
    }

    @Override
    public Double visitNumber(ExpressionParser.NumberContext ctx) {
        return Double.parseDouble(ctx.NUMBER().getText());
    }

    @Override
    public Double visitParentheses(ExpressionParser.ParenthesesContext ctx) {
        return visit(ctx.expr());
    }
}
