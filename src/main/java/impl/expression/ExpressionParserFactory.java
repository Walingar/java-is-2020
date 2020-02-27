package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserFactory {
    public static ExpressionParser getInstance() {
        return new ExpressionParserImpl();
    }

    public static class ExpressionParserImpl implements ExpressionParser {

        @Override
        public int parse(String expression) throws ParseException, IllegalArgumentException, ArithmeticException {

            Parser parser = new Parser(expression);

            int expression_result = 0;
            while (parser.hasNextInt()) {
                int x = parser.nextInt();
                if ((long) x + (long) expression_result > Integer.MAX_VALUE) {
                    throw new ArithmeticException();
                }
                expression_result += x;
            }

            System.out.println(expression_result);
            return expression_result;
        }
    }

    public static class Parser {

        private final String expression;
        private boolean eof;
        private int current_position;

        Parser(String expr) throws IllegalArgumentException {
            current_position = 0;
            eof = false;
            if (expr != null) {
                expression = expr.replaceAll("\\s", "");
            } else {
                throw new IllegalArgumentException("null");
            }
        }

        boolean hasNextInt() {
            return !eof;
        }

        int nextInt() throws ParseException {

            StringBuilder string_builder = new StringBuilder();
            boolean met_digit = false;

            if (eof) {
                throw new ParseException("unexpected end of string");
            }

            while (current_position < expression.length()) {
                char cur_char = expression.charAt(current_position);

                if (!expectedSymbol(cur_char)) {
                    throw new ParseException("unexpected symbol");
                }

                if (Character.isDigit(cur_char)) {
                    met_digit = true;
                    string_builder.append(cur_char);
                } else if ((cur_char == '+' || cur_char == '-') && !met_digit) {
                    string_builder.append(cur_char);
                } else if (met_digit) {
                    break;
                }
                current_position++;
            }

            if (current_position == expression.length()) {
                eof = true;
            }

            if (met_digit) {
                return convertToInt(string_builder);
            } else {
                throw new ParseException("token is not a number");
            }
        }

        private boolean ariphmeticSymbol(char symbol) {
            return symbol == '-' || symbol == '+';
        }

        private boolean expectedSymbol(char symbol) {
            return Character.isDigit(symbol) || ariphmeticSymbol(symbol);
        }

        private int convertToInt(StringBuilder string_builder) throws ParseException {
            try {
                return Integer.parseInt(string_builder.toString());
            } catch (NumberFormatException e) {
                throw new ParseException("cant convert number to int");
            }
        }
    }
}