import fk.home.ShuntingYardCalculator.Companion.calculate
import fk.home.ShuntingYardParser.Companion.parseExpression
import fk.home.ShuntingYardOutput
import fk.home.syQueueToString
import kotlin.test.assertEquals

fun validate(
    expression: String,
    expectedParsedExpression: String,
    expectedResult: ShuntingYardOutput
) {

    parseExpression(expression)
        .also {
            assertEquals(expectedParsedExpression, it.syQueueToString())
        }
        .let(::calculate)
        .also {
            assertEquals(expectedResult, it)
        }
}