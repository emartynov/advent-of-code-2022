package day03

import readInput

private fun Char.toScore() = if (isLowerCase())
    this - 'a' + 1
else
    this - 'A' + 27

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { rugzakContent ->
            rugzakContent.windowed(
                size = rugzakContent.length / 2,
                step = rugzakContent.length / 2
            )
        }.map { compartmentValues ->
            compartmentValues[0].toSet()
                .intersect(
                    compartmentValues[1]
                        .toSet()
                ).first()
        }.sumOf { sameElement ->
            sameElement.toScore()
        }
    }

    fun part2(input: List<String>): Int {
        return input.windowed(3, 3)
            .map { groupRugzakContent ->
                groupRugzakContent
                    .map { it.toSet() }
                    .reduce { content1, content2 ->
                        content1.intersect(content2)
                    }
            }.map { commonFromGroup ->
                commonFromGroup.first()
            }.sumOf { sameElement ->
                sameElement.toScore()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}
