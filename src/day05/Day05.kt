package day05

import readInput

fun main() {

    fun List<String>.toRangeSets() = map { pair ->
        pair.split(",")
            .map { pairString ->
                pairString.split("-")
                    .map { it.toUInt() }
            }
    }.map { pairRangeString ->
        pairRangeString.map { indexValues ->
            UIntRange(indexValues[0], indexValues[1]).toSet()
        }
    }

    fun part1(input: List<String>): Int {
        return input.toRangeSets().map { pairRange ->
            val intersect = pairRange[0].intersect(pairRange[1])
            if (intersect == pairRange[1] || intersect == pairRange[0]) 1 else 0
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.toRangeSets().map { pairRange ->
            val intersect = pairRange[0].intersect(pairRange[1])
            if (intersect.isEmpty()) 0 else 1
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day05/Day05")
    println(part1(input))
    println(part2(input))
}
