package impl.expression

import api.expression.ExpressionParser
import api.expression.exception.ParseException
import impl.expression.factory.ExpressionParserFactory
import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class ExpressionParserTest {

    private fun assertEqualsForExpression(
        expected: Int,
        expression: String,
        expressionParser: ExpressionParser = ExpressionParserFactory.getInstance()
    ) {
        assertEquals("Incorrect answer for expression: \"$expression\"", expected, expressionParser.parse(expression))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parse null`() {
        val expressionParser = ExpressionParserFactory.getInstance()
        expressionParser.parse(null)
    }

    @Test
    fun `parse int`() {
        val expected = 47023930
        assertEqualsForExpression(expected, expected.toString())
    }

    @Test(expected = ParseException::class)
    fun `parse long`() {
        val expressionParser = ExpressionParserFactory.getInstance()
        val longValue = 1L shl 50
        expressionParser.parse(longValue.toString())
    }

    @Test(expected = ParseException::class)
    fun `parse nonNumber`() {
        val expressionParser = ExpressionParserFactory.getInstance()
        expressionParser.parse("2562H+ello+There3455")
    }

    @Test
    fun `parse sum`() {
        assertEqualsForExpression(703, "1 + 12 + 3 + 564 + 123")
    }

    @Test(expected = ArithmeticException::class)
    fun `parse expression with overflow`() {
        val expressionParser = ExpressionParserFactory.getInstance()
        expressionParser.parse("${Int.MAX_VALUE} + 1")
    }

    @Test
    fun `parse simple expression`() {
        assertEqualsForExpression(-449, "1 - 12 + 3 - 564 + 123")
    }

    @Test
    fun `parse expression with negative int`() {
        assertEqualsForExpression(-950, "-500 - 12 + 3 - 564 + 123")
    }

    @Test
    fun `parse few expressions using one parser`() {
        val expressionParser = ExpressionParserFactory.getInstance()
        assertEqualsForExpression(-950, "-500 - 12 + 3 - 564 + 123", expressionParser)
        assertEqualsForExpression(703, "1 + 12 + 3 + 564 + 123", expressionParser)
    }

    @Test
    fun `parse expression with whitespaces`() {
        assertEqualsForExpression(
            -950,
            "\t\t\n\n-500\t -12 +\n \t\t\t3 - 564\t\t\t + \t123\t\t\t"
        )
    }
}