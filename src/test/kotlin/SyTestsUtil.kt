import fk.home.SyCalculatorOutput
import fk.home.calculate
import fk.home.parseExpression
import fk.home.syQueueToString
import kotlin.test.assertEquals

fun validate(
    expression: String,
    expectedParsedExpression: String,
    expectedResult: SyCalculatorOutput
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