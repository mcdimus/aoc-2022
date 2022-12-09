import kotlin.math.*

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun part1(input: List<String>): Int {
        var currentHeadPoint = MutableDataPoint(0, 0, "")
        var currentTailPoint = MutableDataPoint(0, 0, "")
        val visitedPoints = mutableSetOf<MutableDataPoint<*>>(currentTailPoint)

        var currentDirection: Direction
        for (command in input) {
            currentDirection = Direction.of(command[0])
            val length = command.substring(2).toInt()

            for (i in 0..<length) {
                val prev = currentHeadPoint
                currentHeadPoint = currentHeadPoint.applyDirection(currentDirection)
                if (currentTailPoint.distanceTo(currentHeadPoint) >= 2) {
                    currentTailPoint = prev
                    visitedPoints.add(currentTailPoint)
                }
            }
        }
        return visitedPoints.size
    }

    fun part2(input: List<String>): Int {
        var currentRopePoints = Array(10) { index -> MutableDataPoint(0, 0, index)}
        var currentHeadPoint = MutableDataPoint(0, 0, 0)
        val visitedPoints = mutableSetOf<MutableDataPoint<*>>(currentRopePoints.last())

        var currentDirection: Direction
        for (command in input) {
            currentDirection = Direction.of(command[0])
            val length = command.substring(2).toInt()

            for (i in 0..<length) {
                currentHeadPoint = currentHeadPoint.applyDirection(currentDirection)
                currentRopePoints[0] = currentHeadPoint
                for (y in currentRopePoints.indices.drop(1)) {
                    if (currentRopePoints[y].distanceTo(currentRopePoints[y-1]) >= 2) {
                        val xDiff = (currentRopePoints[y - 1].x - currentRopePoints[y].x) / 2.0
                        val xSign = sign(xDiff).toInt()
                        val newXDelta = xSign*ceil(abs(xDiff)).toInt()
                        val yDiff = (currentRopePoints[y - 1].y - currentRopePoints[y].y) / 2.0
                        val ySign = sign(yDiff).toInt()
                        val newYDelta = ySign*ceil(abs(yDiff)).toInt()
                        currentRopePoints[y] = MutableDataPoint(currentRopePoints[y].x + newXDelta, currentRopePoints[y].y+newYDelta, currentRopePoints[y].data )
                    }
                }
                visitedPoints.add(currentRopePoints.last())
            }
        }
        return visitedPoints.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day09_test")
    check(part1(testInput1) == 13)
    check(part2(testInput1) == 1)


    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

enum class Direction(val deltaX: Int, val deltaY: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    companion object {
        fun of(value: String) = values().single { it.name.startsWith(value) }
        fun of(value: Char) = values().single { it.name.startsWith(value) }
    }
}

fun <T> MutableDataPoint<T>.applyDirection(direction: Direction) =
    MutableDataPoint(this.x + direction.deltaX, this.y + direction.deltaY, this.data)

fun <T> MutableDataPoint<T>.distanceTo(other: MutableDataPoint<*>) =
    sqrt((other.x - this.x).toDouble().pow(2) + (other.y - this.y).toDouble().pow(2))