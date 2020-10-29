package example;

import expression.ExpressionBaseVisitor;
import expression.ExpressionLexer;
import expression.ExpressionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class ExpressionLangSample {

    public static void main(String[] args) {
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
        return visitor.visit(parser.expr());
    }

    static class InterpreterExpressionVisitor extends ExpressionBaseVisitor<Double> {

        @Override
        public Double visitExpr(ExpressionParser.ExprContext ctx) {
            switch (ctx.getChildCount()) {
                case 1:
                    return super.visit(ctx.getChild(0));
                case 3: {

                    double v1 = super.visit(ctx.getChild(0));
                    double v2 = super.visit(ctx.getChild(2));

                    switch (ctx.getChild(1).getText()) {
                        case "+":
                            return v1 + v2;
                        case "-":
                            return v1 - v2;
                        default:
                            throw new RuntimeException(
                                    "Term wrong operation: " + ctx.getChild(1).getText());
                    }
                }
                default:
                    throw new RuntimeException(
                            "Term wrong number of children: " + ctx.getChildCount());
            }
        }

        @Override
        public Double visitTerm(ExpressionParser.TermContext ctx) {
            switch (ctx.getChildCount()) {
                case 1:
                    return super.visit(ctx.getChild(0));
                case 3: {

                    double v1 = super.visit(ctx.getChild(0));
                    double v2 = super.visit(ctx.getChild(2));

                    switch (ctx.getChild(1).getText()) {
                        case "*":
                            return v1 * v2;
                        case "/":
                            return v1 / v2;
                        default:
                            throw new RuntimeException(
                                    "Term wrong operation: " + ctx.getChild(1).getText());
                    }
                }
                default:
                    throw new RuntimeException(
                            "Term wrong number of children: " + ctx.getChildCount());
            }
        }

        @Override
        public Double visitFactor(ExpressionParser.FactorContext ctx) {
            return Double.parseDouble(ctx.getText());
        }
    }
}
