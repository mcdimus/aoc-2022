import util.readInput

fun main() {
    fun part1(input: List<String>) = input.asSequence()
        .map { it.split(' ') }
        .map { RPSType.of(it[0].single()) to RPSType.of(it[1].single()) }
        .map { (opponentSign, mySign) -> mySign to mySign.match(opponentSign) }
        .map { (mySign, result) -> mySign.score + result.score }
        .sum()

    fun part2(input: List<String>) = input.asSequence()
        .map { it.split(' ') }
        .map { RPSType.of(it[0].single()) to RPSResult.of(it[1].single()) }
        .map { (opponentSign, expectedResult) -> opponentSign.match(expectedResult) to expectedResult }
        .map { (mySign, result) -> mySign.score + result.score }
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private enum class RPSType(val score: Int, private vararg val chars: Char) {
    ROCK(score = 1, 'A', 'X') {
        override val winsAgainst by lazy { SCISSORS }
    },
    PAPER(score = 2, 'B', 'Y') {
        override val winsAgainst by lazy { ROCK }
    },
    SCISSORS(score = 3, 'C', 'Z') {
        override val winsAgainst by lazy { PAPER }
    };

    companion object {
        fun of(char: Char) = values().single { it.chars.contains(char) }
    }

    abstract val winsAgainst: RPSType

    fun match(other: RPSType) = when {
        this == other -> RPSResult.DRAW
        this.winsAgainst == other -> RPSResult.WIN
        else -> RPSResult.LOSE
    }

    fun match(result: RPSResult) = when (result) {
        RPSResult.LOSE -> this.winsAgainst
        RPSResult.DRAW -> this
        RPSResult.WIN -> RPSType.values().single { it.winsAgainst == this }
    }

}

private enum class RPSResult(val score: Int, private val char: Char) {
    WIN(6, 'Z'),
    DRAW(3, 'Y'),
    LOSE(0, 'X');

    companion object {
        fun of(char: Char) = values().single { it.char == char }
    }
}
