@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun part1(input: List<String>): Int {
        val grid = IntGrid(input)
        val pointsToConsider = ArrayDeque(grid.toList())

        var visibleCount = 0
        var currentPoint: MutableDataPoint<Int>
        while (pointsToConsider.isNotEmpty()) {
            currentPoint = pointsToConsider.removeFirst()
            if (grid.isOnTheEdge(currentPoint)) {
                visibleCount++
                continue
            }
            val pointsOnTheLeft = (0..<currentPoint.x).reversed().map { x -> grid.get(x, currentPoint.y) }
            if (pointsOnTheLeft.all { it!!.data < currentPoint.data }) {
                visibleCount++
                continue
            }
            val pointsOnTheRight = (currentPoint.x + 1..<grid.width).map { x -> grid.get(x, currentPoint.y) }
            if (pointsOnTheRight.all { it!!.data < currentPoint.data }) {
                visibleCount++
                continue
            }
            val pointsOnTheUp = (0..<currentPoint.y).reversed().map { y -> grid.get(currentPoint.x, y) }
            if (pointsOnTheUp.all { it!!.data < currentPoint.data }) {
                visibleCount++
                continue
            }
            val pointsOnTheDown = (currentPoint.y + 1..<grid.height).map { y -> grid.get(currentPoint.x, y) }
            if (pointsOnTheDown.all { it!!.data < currentPoint.data }) {
                visibleCount++
                continue
            }
        }

        return visibleCount
    }

    fun part2(input: List<String>): Int {
        val grid = IntGrid(input)
        val pointsToConsider = ArrayDeque(grid.toList())

        var largestScenicScore = Int.MIN_VALUE
        var currentPoint: MutableDataPoint<Int>
        while (pointsToConsider.isNotEmpty()) {
            currentPoint = pointsToConsider.removeFirst()
            if (grid.isOnTheEdge(currentPoint)) {
                continue
            }
            val pointsOnTheLeft = (0..<currentPoint.x).reversed().map { x -> grid.get(x, currentPoint.y) }
            var leftScore = 0
            for (point in pointsOnTheLeft) {
                if (point!!.data < currentPoint.data) {
                    leftScore++
                } else {
                    leftScore++
                    break
                }
            }

            val pointsOnTheRight = (currentPoint.x + 1..<grid.width).map { x -> grid.get(x, currentPoint.y) }
            var rightScore = 0
            for (point in pointsOnTheRight) {
                if (point!!.data < currentPoint.data) {
                    rightScore++
                } else {
                    rightScore++
                    break
                }
            }
            val pointsOnTheUp = (0..<currentPoint.y).reversed().map { y -> grid.get(currentPoint.x, y) }
            var upScore = 0
            for (point in pointsOnTheUp) {
                if (point!!.data < currentPoint.data) {
                    upScore++
                } else {
                    upScore++
                    break
                }
            }
            val pointsOnTheDown = (currentPoint.y + 1..<grid.height).map { y -> grid.get(currentPoint.x, y) }
            var downScore = 0
            for (point in pointsOnTheDown) {
                if (point!!.data < currentPoint.data) {
                    downScore++
                } else {
                    downScore++
                    break
                }
            }

            val totalScore = leftScore * rightScore * upScore * downScore
            if (totalScore > largestScenicScore) {
                largestScenicScore = totalScore
            }
        }

        return largestScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
