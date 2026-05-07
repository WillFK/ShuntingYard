package fk.home

import kotlin.math.sqrt

internal class ShuntingYardCalculator {

    val tokens: MutableList<Token>
    private var parenthesisCounter = 0

    constructor(tokens: List<Token>) {
        this.tokens = tokens.toMutableList()
    }

    private fun calculate(): ShuntingYardOutput  {
        return try {
            popToken()
                .solve()
                .let(ShuntingYardOutput::Success)
        } catch (e: Throwable) {
            when (e) {
                is CustomCalculatorError -> e.error
                else -> ShuntingYardOutput.Error.Generic
            }
        }
    }

    /**
     * if this function is called, it's assumed there should be elements within `tokens`
     */
    private fun popToken(): Node {
        return when (val last = tokens.removeLast()) {
            is Value -> Node.Number(last.value.toDouble())
            is Operator if last.associativity == Associativity.RIGHT -> {
                val next = popToken()
                Node.Square(next)
            }
            is Plus -> {
                val b = popToken()
                val a = popToken()
                Node.Sum(a, b)
            }
            is Minus -> {
                val b = popToken()
                val a = popToken()
                Node.Subtraction(a, b)
            }
            is Multiply -> {
                val b = popToken()
                val a = popToken()
                Node.Multiplication(a, b)
            }
            is Divide -> {
                val b = popToken()
                val a = popToken()
                Node.Division(a, b)
            }
            else -> throw IllegalArgumentException("Unexpected token type ${tokens.removeLast()}")
        }
    }

    companion object {

        fun calculate(tokens: List<Token>): ShuntingYardOutput {
            return ShuntingYardCalculator(tokens).calculate()
        }
    }
}

private data class CustomCalculatorError(val error: ShuntingYardOutput.Error) : Throwable()

sealed interface ShuntingYardOutput {

    data class Success(val value: Double) : ShuntingYardOutput

    sealed interface Error : ShuntingYardOutput {

        object ParenthesisMismatch : Error

        object ExpressionFormat : Error

        object DivisionByZero : Error

        object SquareRootOfNegativeNumber: Error

        object Math : Error

        object Generic: Error
    }

}

sealed interface Node {

    fun solve() : Double

    data class Number(val number: Double) : Node {
        override fun solve(): Double = number
    }

    data class Square(val next: Node) : Node {
        override fun solve(): Double =
            next.solve()
                .let { radicand ->
                    if (radicand < 0) {
                        throw CustomCalculatorError(ShuntingYardOutput.Error.SquareRootOfNegativeNumber)
                    } else {
                        sqrt(radicand)
                    }
                }
    }

    data class Sum(val left: Node, val right: Node) : Node {
        override fun solve(): Double = left.solve() + right.solve()
    }

    data class Subtraction(val left: Node, val right: Node) : Node {
        override fun solve(): Double = left.solve() - right.solve()
    }

    data class Multiplication(val left: Node, val right: Node) : Node {
        override fun solve(): Double = left.solve() * right.solve()
    }

    data class Division(val left: Node, val right: Node) : Node {
        override fun solve(): Double = right.solve().let { divisor ->
            if (divisor == .0) {
                throw CustomCalculatorError(ShuntingYardOutput.Error.DivisionByZero)
            } else {
                left.solve() / divisor
            }
        }
    }
}
