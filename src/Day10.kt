import util.readInput

fun main() {
    fun part1(input: List<String>): Int {
        var registerValue = 1
        return input.joinToString(separator = " ")
            .split(" ")
            .mapIndexed { index, s ->
                (index + 1 to registerValue).also {
                    if (s != "noop" && s != "addx") {
                        registerValue += s.toInt()
                    }
                }
            }.sumOf { (cycle, registerValue) -> if ((cycle - 20) % 40 == 0) cycle * registerValue else 0 }
    }

    fun part2(input: List<String>): Int {
        var registerValue = 1
        var currentSpritePosition = 1
        val grid = input.joinToString(separator = " ")
            .split(" ")
            .mapIndexed { index, s ->
                var value = ' '
                if (index % 40 in (currentSpritePosition - 1)..(currentSpritePosition + 1)) {
                    value = '#'
                }
                if (s != "noop" && s != "addx") {
                    registerValue += s.toInt()
                    currentSpritePosition = registerValue
                }
                value
            }

        grid.forEachIndexed { index, c ->
            print(c)
            if ((index + 1) % 40 == 0) println()
        }
        return grid.count { it == '#' }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 124)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
