package fk.home

internal fun List<Token>.syQueueToString(): String {
    return this
        .map { it.toString() }
        .reduceRightOrNull { acc, string ->  "$acc $string"  }
        ?: ""
}

internal class ShuntingYardParser private constructor(val expression: String) {

    val segments = expression.split(" ").toMutableList()
    val output = mutableListOf<Token>()
    val operatorStack = mutableListOf<Operator>()

    fun parse(): List<Token> {

        while (segments.isNotEmpty()) {
            segments.removeFirst().tokenfy().let(::processToken)
        }
        popOperators()
        return output.toList()
    }

    private fun processToken(token: Token) {
        if (token is Value) {
            output.add(token)
        } else {
            processOperator(token as Operator)
        }
    }

    private fun processOperator(op: Operator) {

        if (operatorStack.isEmpty()) {
            // if stack is empty, just add operator to it
            operatorStack.add(op)
        } else {
            val last = operatorStack.last()
            if (last.precedence > op.precedence || (last.precedence == op.precedence && last.associativity == Associativity.LEFT)) {
                operatorStack.removeLast().let(output::add)
                processOperator(op)
            } else {
                operatorStack.add(op)
            }
        }
    }

    private fun popOperators() {
        while (operatorStack.isNotEmpty()) {
            operatorStack.removeLast().let(output::add)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("expression: $expression\n")
        builder.append("segments: $segments\n")
        builder.append("output: ${output.syQueueToString()}\n")
        builder.append("stack: ${operatorStack.syQueueToString()}\n")
        return builder.toString()
    }

    companion object {

        internal fun parseExpression(expr: String): List<Token> =
            ShuntingYardParser(expr).parse()
    }
}