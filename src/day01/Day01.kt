package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.split { string -> string.isEmpty() }
            .map { list ->
                list.sumOf { string -> string.toInt() }
            }.max()
    }

    fun part2(input: List<String>): Int {
        return input.split { string -> string.isEmpty() }
            .map { list ->
                list.sumOf { string -> string.toInt() }
            }.sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}

fun <T : Any> List<T>.split(isMarker: (T) -> Boolean): List<List<T>> {
    val result = mutableListOf<List<T>>()
    var previousIndex = 0
    forEachIndexed { index, value ->
        if (isMarker(value)) {
            result.add(subList(previousIndex, index))
            previousIndex = index + 1
        } else if (index == size -1) {
            result.add(subList(previousIndex, size))
        }
    }
    return result
}
