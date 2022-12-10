import util.readInput

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun part1(input: List<String>): String {
        val stacks = parseStacks(input)
        val commands = parseCommands(input)

        for (command in commands) {
            for (i in 0..<command.amount) {
                val c = stacks[command.fromStackIndex].removeFirst()
                stacks[command.toStackIndex].addFirst(c)
            }
        }
        return stacks.fold("") { acc, stack -> acc + stack.first() }
    }

    fun part2(input: List<String>): String {
        val stacks = parseStacks(input)
        val commands = parseCommands(input)

        for (command in commands) {
            val crates = mutableListOf<Char>()
            for (i in 0..<command.amount) {
                crates.add(stacks[command.fromStackIndex].removeFirst())
            }
            crates.reversed().forEach {
                stacks[command.toStackIndex].addFirst(it)
            }
        }
        return stacks.fold("") { acc, stack -> acc + stack.first() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun parseStacks(input: List<String>): MutableList<ArrayDeque<Char>> {
    val stacks = mutableListOf<ArrayDeque<Char>>()
    input.takeWhile { !it[1].isDigit() }
        .map { it.windowed(size = 3, step = 4) }
        .forEach { strings ->
            strings.forEachIndexed { stackIndex, crate ->
                if (stackIndex + 1 > stacks.size) {
                    stacks.add(ArrayDeque())
                }
                if (crate.isNotBlank()) {
                    stacks[stackIndex].add(crate[1])
                }
            }
        }
    return stacks
}

private fun parseCommands(input: List<String>) = input
    .dropWhile { it.isNotBlank() }
    .drop(1)
    .map { Command.of(it) }

private data class Command(val amount: Int, val fromStackIndex: Int, val toStackIndex: Int) {

    companion object {
        fun of(value: String): Command {
            val ints = value
                .replace("move ", "")
                .replace("from ", "")
                .replace("to ", "")
                .split(' ')
                .map { it.toInt() }
            return Command(amount = ints[0], fromStackIndex = ints[1] - 1, toStackIndex = ints[2] - 1)
        }
    }

}