import util.partitionBy
import util.readInput

fun main() {
    fun part1(input: List<String>) = input
        .partitionBy(condition = String::isBlank, valueConverter = String::toInt)
        .maxOfOrNull(List<Int>::sum)

    fun part2(input: List<String>) = input
        .partitionBy(condition = String::isBlank, valueConverter = String::toInt)
        .map(List<Int>::sum)
        .sortedDescending()
        .take(3)
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
