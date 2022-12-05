package day05

import readInput

private class Schema(
    numberOfStacks: Int
) {
    private val stacks: MutableList<List<Char>> = MutableList(numberOfStacks) { emptyList() }
    operator fun get(indexFromOne: Int): List<Char> = stacks[indexFromOne - 1]

    fun add(indexFromOne: Int, value: Char): Schema {
        val updatedStack = listOf(value) + get(indexFromOne)
        return updateStack(indexFromOne, updatedStack)
    }

    private fun updateStack(indexFromOne: Int, updatedStack: List<Char>): Schema {
        stacks.add(indexFromOne - 1, updatedStack)
        stacks.removeAt(indexFromOne)
        return this
    }

    fun move(move: Move, transform: (List<Char>) -> List<Char> = { it }): Schema {
        val fromStack = get(move.fromIndex)
        val toStack = get(move.toIndex)

        val elements = fromStack.subList(fromStack.size - move.numberOfCrates, fromStack.size)

        return updateStack(
            indexFromOne = move.fromIndex,
            updatedStack = fromStack.dropLast(move.numberOfCrates)
        ).updateStack(
            indexFromOne = move.toIndex,
            updatedStack = toStack + transform.invoke(elements)
        )
    }

    fun allLast(): String = stacks.map { it.lastOrNull()?.toString().orEmpty() }
        .joinToString("")
}

private data class Move(
    val numberOfCrates: Int,
    val fromIndex: Int,
    val toIndex: Int
)

fun main() {

    fun readStacksAndMoves(input: List<String>): Pair<Schema, List<Move>> {
        val schemaEndIndex = input.indexOf("")
        val numberOfStacks = input[schemaEndIndex - 1].chunked(4).last().trim().toInt()
        var initialSchema = Schema(numberOfStacks)
        input.subList(0, schemaEndIndex - 1)
            .map {
                it.chunked(4)
                    .forEachIndexed { index, value ->
                        if (value.startsWith("[")) {
                            initialSchema = initialSchema.add(index + 1, value[1])
                        }
                    }
            }
        val moves = input.subList(schemaEndIndex + 1, input.size)
            .map {
                it.split(" ")
            }.map { row ->
                row.mapNotNull { it.toIntOrNull() }
            }.map { numbers ->
                Move(
                    numberOfCrates = numbers[0],
                    fromIndex = numbers[1],
                    toIndex = numbers[2]
                )
            }
        return initialSchema to moves
    }

    fun playMoves(schema: Schema, moves: List<Move>, transform: (List<Char>) -> List<Char> = { it }): Schema =
        moves.fold(schema) { accSchema, move -> accSchema.move(move, transform) }

    fun part1(input: List<String>): String {
        val data = readStacksAndMoves(input)
        val updatedSchema = playMoves(data.first, data.second) { it.reversed() }
        return updatedSchema.allLast()
    }

    fun part2(input: List<String>): String {
        val data = readStacksAndMoves(input)
        val updatedSchema = playMoves(data.first, data.second)
        return updatedSchema.allLast()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("day05/Day05")
    println(part1(input))
    println(part2(input))
}
