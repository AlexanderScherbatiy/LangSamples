package func;

public class InterpreterFuncVisitor extends FuncBaseVisitor<Double> {

    @Override
    public Double visitRoot(FuncParser.RootContext ctx) {
        double value = 0;
        for (FuncParser.StmtContext stmtContext : ctx.stmt()) {
            value = visit(stmtContext);
        }

        return value;
    }

    @Override
    public Double visitExprStmt(FuncParser.ExprStmtContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitMulDiv(FuncParser.MulDivContext ctx) {
        double v1 = visit(ctx.expr(0));
        double v2 = visit(ctx.expr(1));
        switch (ctx.op.getType()) {
            case FuncParser.MUL:
                return v1 * v2;
            case FuncParser.DIV:
                return v1 / v2;
            default:
                throw new RuntimeException(
                        "Unknown Mul/Div operation: " + ctx.op.getText());
        }
    }

    @Override
    public Double visitAddSub(FuncParser.AddSubContext ctx) {
        double v1 = visit(ctx.expr(0));
        double v2 = visit(ctx.expr(1));
        switch (ctx.op.getType()) {
            case FuncParser.PLUS:
                return v1 + v2;
            case FuncParser.MINUS:
                return v1 - v2;
            default:
                throw new RuntimeException(
                        "Unknown Add/Sub operation: " + ctx.op.getText());
        }
    }

    @Override
    public Double visitNumber(FuncParser.NumberContext ctx) {
        return Double.parseDouble(ctx.NUMBER().getText());
    }
}
