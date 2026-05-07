package fk.home

fun main() {

    "1 + 2 * 3 - 4"
        .let(::parseExpression)
        .also(::println)
        .let(::calculate)
        .also(::println)
}