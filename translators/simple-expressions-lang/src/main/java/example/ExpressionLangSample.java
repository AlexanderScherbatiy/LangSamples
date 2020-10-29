package example;

import expression.ExpressionBaseVisitor;
import expression.ExpressionLexer;
import expression.ExpressionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class ExpressionLangSample {

    public static void main(String[] args) {
        show("42");
        show("2 + 3");
        show("2 * 3");
        show("5 - 3");
        show("6 / 2");
    }

    private static void show(String expr) {
        System.out.printf("%s = %.2f%n", expr, calculate(expr));
    }

    public static double calculate(String source) {
        CodePointCharStream input = CharStreams.fromString(source);
        ExpressionLexer lexer = new ExpressionLexer(input);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokenStream);
        InterpreterExpressionVisitor visitor = new InterpreterExpressionVisitor();
        return visitor.visit(parser.root());
    }

    static class InterpreterExpressionVisitor extends ExpressionBaseVisitor<Double> {

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
    }
}
