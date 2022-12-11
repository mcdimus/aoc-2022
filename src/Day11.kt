import util.MoreMath.lcm
import util.readInput
import kotlin.math.floor

fun main() {

    fun part1(input: List<String>): Long {
        val monkeys = parseInput(input)
        for (round in (1..20)) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    monkey.inspectionsCount++
                    val oldItem = monkey.items.removeAt(0)
                    val newItem = floor(monkey.operation(oldItem) / 3.0).toLong()
                    val targetMonkey = monkey.targets[newItem % monkey.test == 0L]!!
                    monkeys[targetMonkey].items.add(newItem)
                }
            }
        }
        return monkeys
            .sortedByDescending { it.inspectionsCount }
            .take(2)
            .map { it.inspectionsCount }
            .reduce(Long::times)
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseInput(input)
        val monkeysLCM = lcm(monkeys.map { it.test })
        for (round in (1..10_000)) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    monkey.inspectionsCount++
                    val oldItem = monkey.items.removeAt(0)
                    val newItem = monkey.operation(oldItem) % monkeysLCM
                    val targetMonkey = monkey.targets[newItem % monkey.test == 0L]!!
                    monkeys[targetMonkey].items.add(newItem)
                }
            }
        }
        return monkeys
            .sortedByDescending { it.inspectionsCount }
            .take(2)
            .map { it.inspectionsCount }
            .reduce(Long::times)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

private class MonkeyA(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val targets: Map<Boolean, Int>
) {
    var inspectionsCount = 0L
}

private fun parseInput(input: List<String>): List<MonkeyA> =
    input.windowed(size = 6, step = 7, partialWindows = true).map { it.toMonkey() }

private fun List<String>.toMonkey(): MonkeyA {
    val id = this[0].replace("Monkey ", "").replace(":", "").toInt()
    val items = this[1].trim().replace("Starting items: ", "").split(", ").map { it.toLong() }
    val secondOperand = this[2].split(' ').last().toLongOrNull()
    val op: (Long) -> Long = if (this[2].contains('+')) {
        { item -> item.plus(secondOperand ?: item) }
    } else {
        { item -> item.times(secondOperand ?: item) }
    }
    val test = this[3].split(' ').last().toLong()
    val targets = buildMap {
        put(true, this@toMonkey[4].split(' ').last().toInt())
        put(false, this@toMonkey[5].split(' ').last().toInt())
    }
    return MonkeyA(
        id = id,
        items = items.toMutableList(),
        operation = op,
        test = test,
        targets = targets
    )
}
