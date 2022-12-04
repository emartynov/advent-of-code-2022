package day04

import readInput

fun main() {

    fun List<String>.toRangeSets() = map { pair ->
        pair.split(",")
            .map {
                it.split("-")
            }
    }.map { pairRangeString ->
        pairRangeString.map { indexValues ->
            UIntRange(indexValues[0].toUInt(), indexValues[1].toUInt()).toSet()
        }
    }

    fun part1(input: List<String>): Int {
        return input.toRangeSets().map { pairRange ->
            val intersect = pairRange[0].intersect(pairRange[1])
            if (
                intersect.equals(pairRange[1]) ||
                intersect.equals(pairRange[0])
            ) 1 else 0
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.toRangeSets().map { pairRange ->
            val intersect = pairRange[0].intersect(pairRange[1])
            if (intersect.isEmpty()) 0 else 1
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day04/Day04")
    println(part1(input))
    println(part2(input))
}
