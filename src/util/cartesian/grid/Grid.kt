package util.cartesian.grid

import util.cartesian.point.DiagonalDirection
import util.cartesian.point.Direction
import util.cartesian.point.Point

interface Grid<T : Point<*>, SELF : Grid<T, SELF>> : Iterable<T> {
    val width: Int
    val values: List<T>

    fun getSize() = values.size
    fun getHeight() = values.size / width

    override fun iterator() = values.iterator()

    fun get(x: Int, y: Int) = values[y * width + x]
    fun getOrNull(x: Int, y: Int) = values.getOrNull(y * width + x)

    fun getAdjacentPoints(point: T) = getAdjacentPoints(x = point.x, y = point.y)
    fun getAdjacentPoints(x: Int, y: Int) =
        Direction.values().mapNotNull { getOrNull(x = x + it.deltaX, y = y + it.deltaY) }

    fun getSurroundingPoints(point: T) = getSurroundingPoints(x = point.x, y = point.y)
    fun getSurroundingPoints(x: Int, y: Int) = getAdjacentPoints(x = x, y = y) +
            DiagonalDirection.values().mapNotNull { getOrNull(x = x + it.deltaX, y = y + it.deltaY) }

    fun isOnTheEdge(point: T) =
        point.x == 0 || point.x == width - 1
                || point.y == 0 || point.y == getHeight() - 1
}