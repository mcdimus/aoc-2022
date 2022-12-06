fun main() {
    fun part1(input: List<String>) = findLastIndexOfUniqueCharsSequence(input[0], 4)

    fun part2(input: List<String>) = findLastIndexOfUniqueCharsSequence(input[0], 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 29)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun findLastIndexOfUniqueCharsSequence(str: String, sequenceSize: Int) = str.indices
    .firstOrNull { i -> hasUniqueSequenceStartingFromIndexOfSize(str, i, sequenceSize) }
    ?.plus(sequenceSize)
    ?: -1

private fun hasUniqueSequenceStartingFromIndexOfSize(str: String, i: Int, size: Int) =
    str.substring(i, i + size).toSet().size == size
