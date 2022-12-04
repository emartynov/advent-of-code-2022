package day03

import readInput

/*
A,X - Rock
B,Y - Paper
C,Z - Scissors

Rock -> Scissors
Scissors -> Paper
Paper -> Rock

 */

enum class Variants {
    Rock,
    Paper,
    Scissors
}

val mapping = mapOf(
    'A' to Variants.Rock,
    'X' to Variants.Rock,
    'B' to Variants.Paper,
    'Y' to Variants.Paper,
    'C' to Variants.Scissors,
    'Z' to Variants.Scissors,
)

fun Variants.value() = ordinal + 1

infix fun Variants.test(other: Variants): Int {
    return when (ordinal - other.ordinal) {
        0 -> 3 // same
        1 -> 6 // every wins over predecessor
        -2 -> 6 // rock wins scissors
        else -> 0 // lost
    }
}

infix fun Variants.score(other: Variants) = value() + (this test other)

fun Variants.selectVariant(strategy: Char): Variants {
    return if (strategy == 'Y') this //draw
    else { // lose
        val selectVariantOrdinal = if (strategy == 'X') ordinal - 1 else ordinal + 1
        if (selectVariantOrdinal < 0) Variants.Scissors
        else if (selectVariantOrdinal > Variants.Scissors.ordinal) Variants.Rock
        else Variants.values()[selectVariantOrdinal]
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { moveString -> moveString[0].toVariants() to moveString[2].toVariants() }
            .sumOf {
                it.second score it.first
            }
    }

    fun part2(input: List<String>): Int {
        return input.map { moveString ->
            val opponentVariant = moveString[0].toVariants()
            opponentVariant to opponentVariant.selectVariant(moveString[2])
        }.sumOf {
            it.second score it.first
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}

private fun Char.toVariants(): Variants = mapping[this]!!
