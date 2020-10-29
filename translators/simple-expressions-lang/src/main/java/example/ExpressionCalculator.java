package example;

import expression.ExpressionLexer;
import expression.ExpressionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class ExpressionCalculator {

    public double calculate(String expression) {
        CodePointCharStream input = CharStreams.fromString(expression);
        ExpressionLexer lexer = new ExpressionLexer(input);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokenStream);
        InterpreterExpressionVisitor visitor = new InterpreterExpressionVisitor();
        return visitor.visit(parser.root());
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.printf("Provide an arithmetic expression as a first argument!%n");
            System.exit(0);
        }

        String expr = args[0];
        double value = new ExpressionCalculator().calculate(expr);
        System.out.printf("%s = %f%n", expr, value);
    }
}
