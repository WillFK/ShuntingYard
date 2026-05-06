package fk.home

fun parseExpression(expr: String) {
    ShuntingYard(expr).parse()
}

private class ShuntingYard(val expression: String) {

    val segments = expression.split(" ").toMutableList()
    val output = mutableListOf<Token>()
    val operatorStack = mutableListOf<Operator>()

    fun parse() {

        while (segments.isNotEmpty()) {
            segments.removeFirst().tokenfy().let(::processToken)
        }
        popOperators()

        println(toString())
    }

    private fun processToken(token: Token) {
        if (token is Value) {
            output.add(token)
        } else {
            processOperator(token as Operator)
        }
    }

    private fun processOperator(op: Operator) {
        operatorStack.add(op)
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