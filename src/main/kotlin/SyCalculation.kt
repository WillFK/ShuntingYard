package fk.home

import kotlin.math.sqrt

fun calculate(tokens: List<Token>): Double {
    return Calculator(tokens).calculate()
}

private class Calculator {

    val tokens: MutableList<Token>
    private var parenthesisCounter = 0

    constructor(tokens: List<Token>) {
        this.tokens = tokens.toMutableList()
    }

    fun calculate(): Double = popToken().solve()

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
}

sealed interface Node {

    fun solve() : Double

    data class Number(val number: Double) : Node {
        override fun solve(): Double = number
    }

    data class Square(val next: Node) : Node {
        override fun solve(): Double = sqrt(next.solve())
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
        override fun solve(): Double = left.solve() / right.solve()
    }
}
