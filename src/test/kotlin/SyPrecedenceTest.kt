import fk.home.ShuntingYardOutput
import kotlin.test.Test

class SyPrecedenceTest {

    @Test
    fun `validate + *`() {
        validate(
            "1 + 2 * 3",
            "1 2 3 * +",
            ShuntingYardOutput.Success(7.0)
        )

        validate(
            "2 * 3 + 5",
            "2 3 * 5 +",
            ShuntingYardOutput.Success(11.0)
        )
    }

    @Test
    fun `validate + R`() {

        validate(
            "R 9 + 10",
            "9 R 10 +",
            ShuntingYardOutput.Success(13.0)
        )


        validate(
            "10 + R 9",
            "10 9 R +",
            ShuntingYardOutput.Success(13.0)
        )
    }
}