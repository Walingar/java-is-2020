package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    private enum Operation {
        IDLE,
        ADD,
        SUB
    }

    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }

        /*
         * ReplaceAll might be implemented by 'for' to make sure that it is O(n).
         */
        String[] expressionList = expression.replaceAll("[\t\n]", "").split(" ");
        int ret = 0;
        Operation operation = Operation.ADD;

        for (String operand : expressionList) {
            if (operand.equals("+")) {
                if (operation != Operation.IDLE) {
                    throw new ParseException("Wrong operands sequence");
                }

                operation = Operation.ADD;
            } else if (operand.equals("-")) {
                if (operation != Operation.IDLE) {
                    throw new ParseException("Wrong operands sequence");
                }

                operation = Operation.SUB;
            } else {
                int value;

                if (operation == Operation.IDLE) {
                    switch(operand.charAt(0)) {
                        case '+':
                            operation = Operation.ADD;
                            break;
                        case '-':
                            operation = Operation.SUB;
                            break;
                        default:
                            throw new ParseException("Wrong operands sequence");
                    }

                    operand = operand.substring(1);
                }

                try {
                    value = Integer.parseInt(operand);
                } catch (NumberFormatException e) {
                    throw new ParseException(e.getMessage());
                }

                switch (operation) {
                    case ADD:
                        ret = Math.addExact(ret, value);
                        break;
                    case SUB:
                        ret = Math.subtractExact(ret, value);
                        break;
                }

                operation = Operation.IDLE;
            }
        }

        return ret;
    }
}