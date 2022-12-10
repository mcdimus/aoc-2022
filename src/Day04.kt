import util.contains
import util.overlaps
import util.readInput
import util.toIntRange

fun main() {

    fun part1(input: List<String>) = parseInput(input)
        .count { it.first contains it.second || it.second contains it.first }

    fun part2(input: List<String>) = parseInput(input)
        .count { it.first overlaps it.second }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>) = input
    .map { it.split(',') }
    .map { it[0] to it[1] }
    .map { it.first.toIntRange() to it.second.toIntRange() }