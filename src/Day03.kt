fun main() {

    fun part1(input: List<String>) = input
        .map { it.substring(0, it.length / 2) to it.substring(it.length / 2, it.length) }
        .map { (first, second) -> first.first(second::contains) }
        .sumOf { priority(it) }

    fun part2(input: List<String>): Int {
        return input
            .windowed(size = 3, step = 3)
            .map { group ->
                group.map { rucksack -> rucksack.groupingBy { it }.eachCount() }
                    .fold(initial = mutableMapOf<Char, MutableList<Int>>()) { acc, map ->
                        map.forEach { (char, count) ->
                            acc.compute(char) { _, l -> (l ?: mutableListOf()).also { it.add(count) } }
                        }
                        acc
                    }
                    .filter { it.value.size == 3 }
                    .map { it.key to it.value.sum() }
                    .sortedBy { it.second }
                    .last()
                    .first
            }.map { priority(it) }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))


}

private fun priority(it: Char) = when (it) {
    in 'a'..'z' -> it.code - 96
    in 'A'..'Z' -> it.code - 64 + 26
    else -> 0
}
