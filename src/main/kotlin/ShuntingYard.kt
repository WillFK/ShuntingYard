package fk.home

class ShuntingYard private constructor() {

    companion object {
        fun parse(expression: String): List<Token> = ShuntingYardParser.parseExpression(expression)
        fun calculate(tokens: List<Token>): ShuntingYardOutput = ShuntingYardCalculator.calculate(tokens)
    }
}