package func;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class SimpleFunctionLang {
    public static void main(String[] args) {
        String expr = "42 + 3;";
        System.out.printf("%s = %f%n", expr, execute(expr));
    }

    private static double execute(String expr) {

        CodePointCharStream input = CharStreams.fromString(expr);
        FuncLexer lexer = new FuncLexer(input);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        FuncParser parser = new FuncParser(tokenStream);
        InterpreterFuncVisitor visitor = new InterpreterFuncVisitor();
        return visitor.visit(parser.root());
    }
}
