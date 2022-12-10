import util.cartesian.point.DataPoint
import util.cartesian.point.Direction
import util.cartesian.point.Point
import util.readInput
import kotlin.math.*

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun part1(input: List<String>): Int {
        var currentHeadPoint = Point.of(0, 0)
        var currentTailPoint = Point.of(0, 0)
        val visitedPoints = mutableSetOf(currentTailPoint)

        var currentDirection: Direction
        for (command in input) {
            currentDirection = Direction.of(command[0])
            val length = command.substring(2).toInt()

            for (i in 0..<length) {
                val prev = currentHeadPoint
                currentHeadPoint = currentHeadPoint.move(currentDirection)
                if (currentTailPoint distanceTo currentHeadPoint >= 2) {
                    currentTailPoint = prev
                    visitedPoints.add(currentTailPoint)
                }
            }
        }
        return visitedPoints.size
    }

    fun part2(input: List<String>): Int {
        val currentRopePoints = Array(10) { index -> DataPoint.of(0, 0, index)}
        var currentHeadPoint = DataPoint.of(0, 0, 0)
        val visitedPoints = mutableSetOf(currentRopePoints.last())

        var currentDirection: Direction
        for (command in input) {
            currentDirection = Direction.of(command[0])
            val length = command.substring(2).toInt()

            for (i in 0..<length) {
                currentHeadPoint = currentHeadPoint.move(currentDirection)
                currentRopePoints[0] = currentHeadPoint
                for (y in currentRopePoints.indices.drop(1)) {
                    if (currentRopePoints[y] distanceTo currentRopePoints[y-1] >= 2) {
                        val xDiff = (currentRopePoints[y - 1].x - currentRopePoints[y].x) / 2.0
                        val xSign = sign(xDiff).toInt()
                        val newXDelta = xSign*ceil(abs(xDiff)).toInt()
                        val yDiff = (currentRopePoints[y - 1].y - currentRopePoints[y].y) / 2.0
                        val ySign = sign(yDiff).toInt()
                        val newYDelta = ySign*ceil(abs(yDiff)).toInt()
                        currentRopePoints[y] = currentRopePoints[y].move(newXDelta, newYDelta)
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
