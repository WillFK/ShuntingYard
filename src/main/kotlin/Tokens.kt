package fk.home


sealed interface Token

data class Value(val value: Double) : Token {
    override fun toString() = "$value"
}

sealed class Operator : Token {

    abstract val symbol: String

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
}

object Minus : Operator() {
    override val symbol = "-"
}

object Multiply : Operator() {
    override val symbol = "*"
}

object Divide : Operator() {
    override val symbol = "/"
}

object SquareRoot : Operator() {
    override val symbol = "R"
}

fun String.tokenfy(): Token {

    val asDouble = this.toDoubleOrNull()
    return asDouble?.let(::Value) ?: Operator.all.find { it.symbol == this } ?: throw IllegalArgumentException("Symbol $this not found")
}