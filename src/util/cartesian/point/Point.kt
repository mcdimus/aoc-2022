package util.cartesian.point

import kotlin.math.pow
import kotlin.math.sqrt

interface Point<SELF : Point<SELF>> {
    val x: Int
    val y: Int

    infix fun distanceTo(other: Point<*>) =
        sqrt((other.x - this.x).toDouble().pow(2) + (other.y - this.y).toDouble().pow(2))

    fun move(deltaX: Int, deltaY: Int): SELF
    fun move(direction: Direction) = move(direction.deltaX, direction.deltaY)

    companion object {
        fun of(x: Int, y: Int): Point<*> = ImmutablePoint(x, y)
    }

    private data class ImmutablePoint(override val x: Int, override val y: Int) : Point<ImmutablePoint> {
        override fun move(deltaX: Int, deltaY: Int) = ImmutablePoint(x + deltaX, y + deltaY)

    }
}