package day07

import readInput

fun main() {

    fun String.uniqueCharactersEndIndex(numberOfUniqueCharacters: Int) = windowed(numberOfUniqueCharacters, 1)
        .indexOfFirst { it.toSet().size == numberOfUniqueCharacters } + numberOfUniqueCharacters

    fun part1(input: List<String>): Int {
        return input.first()
            .uniqueCharactersEndIndex(4)
    }

    fun part2(input: List<String>): Int {
        return input.first()
            .uniqueCharactersEndIndex(14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day07/Day07_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = readInput("day07/Day07")
    println(part1(input))
    println(part2(input))
}
