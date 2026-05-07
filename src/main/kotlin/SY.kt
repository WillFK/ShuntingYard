package fk.home

fun parseExpression(expr: String): List<Token> =
    ShuntingYard(expr).parse()

private class ShuntingYard(val expression: String) {

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

        println(toString())
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
        builder.append("output: ${output.map(Token::toString).reduceRightOrNull { acc, token -> "$acc $token" }}\n")
        builder.append("stack: ${operatorStack.map(Operator::toString).reduceRightOrNull { acc, token -> "$acc $token" }}\n")
        return builder.toString()
    }
}