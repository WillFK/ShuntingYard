package fk.home

fun main() {

    "1 + 2 * 3 - 3213"
        .let(::parseExpression)
        .also { println(it.syQueueToString()) }
        .let(::calculate)
        .also(::println)
}