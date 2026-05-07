package fk.home


sealed interface Token

data class Value(val value: Int) : Token {
    override fun toString() = "$value"
}

enum class OperationType {
    Unary,
    Binary
}

enum class Associativity {
    LEFT,
    RIGHT
}
sealed class Operator : Token {

    abstract val symbol: String

    abstract val associativity: Associativity

    abstract val precedence: Int

    override fun toString(): String {
        return symbol
    }

    companion object {
        val all = listOf(
            Plus,
            Minus,
            Multiply,
            Divide,
            SquareRoot
        )
    }
}

object Plus : Operator() {
    override val symbol = "+"

    override val associativity = Associativity.LEFT

    override val precedence = 0
}

object Minus : Operator() {
    override val symbol = "-"

    override val associativity = Associativity.LEFT

    override val precedence = 0
}

object Multiply : Operator() {
    override val symbol = "*"

    override val associativity = Associativity.LEFT

    override val precedence = 1
}

object Divide : Operator() {
    override val symbol = "/"

    override val associativity = Associativity.LEFT

    override val precedence = 2
}

object SquareRoot : Operator() {
    override val symbol = "R"

    override val associativity = Associativity.RIGHT

    override val precedence = 3
}

fun String.tokenfy(): Token {

    return this.toIntOrNull()?.let(::Value)
        ?: Operator.all.find { it.symbol == this }
        ?: throw IllegalArgumentException("Symbol $this not found")
}