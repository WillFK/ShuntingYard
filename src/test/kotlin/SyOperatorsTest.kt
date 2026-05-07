import fk.home.SyCalculatorOutput
import sun.security.util.KeyUtil.validate
import kotlin.test.Test

class SyOperatorsTest {


    /**
     * Validate single digit expression
     */
    @Test
    fun `validate 1`() {
        validate("1", "1", SyCalculatorOutput.Success(1.0))
    }

    /**
     * Validate each type of operation
     *
     * Sum...
     */
    @Test
    fun `validate sum`() {
        validate("1 + 20", "1 20 +", SyCalculatorOutput.Success(21.0))
    }


    @Test
    fun `validate sum inverted`() {
        validate("20 + 1", "20 1 +", SyCalculatorOutput.Success(21.0))
    }

    /**
     * ...Subtraction...
     */

    @Test
    fun `validate subtraction`() {
        validate("20 - 10", "20 10 -", SyCalculatorOutput.Success(10.0))
    }


    @Test
    fun `validate subtraction inverted`() {
        validate("10 - 20", "10 20 -", SyCalculatorOutput.Success(-10.0))
    }

    /**
     * ...Multiplication...
     */

    @Test
    fun `validate multiplication`() {
        validate("5 * 3", "5 3 *", SyCalculatorOutput.Success(15.0))
    }


    @Test
    fun `validate multiplication inverted`() {
        validate("3 * 5", "3 5 *", SyCalculatorOutput.Success(15.0))
    }

    /**
     * ...Division
     */

    @Test
    fun `validate division`() {
        validate("4 / 2", "4 2 /", SyCalculatorOutput.Success(2.0))
    }


    @Test
    fun `validate division inverted`() {
        validate("2 / 4", "2 4 /", SyCalculatorOutput.Success(.5))
    }

    @Test
    fun `validate division by 0`() {
        validate("10 / 0", "10 0 /", SyCalculatorOutput.Error.DivisionByZero)
    }

    /**
     * ...and finally, square root!
     */
    @Test
    fun `validate square root`() {
        validate("R 9", "9 R", SyCalculatorOutput.Success(3.0))
    }

    @Test
    fun `validate square root of negative`() {
        validate("R -9", "-9 R", SyCalculatorOutput.Error.SquareRootOfNegativeNumber)
    }
}