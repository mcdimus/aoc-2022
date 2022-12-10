package util.cartesian.point

interface DataPoint<T> : Point<DataPoint<T>> {
    val data: T

    companion object {
        fun <T> of(x: Int, y: Int, data: T): DataPoint<T> = ImmutableDataPoint(x, y, data)
    }

    private data class ImmutableDataPoint<T>(override val x: Int, override val y: Int, override val data: T) :
        DataPoint<T> {
        override fun move(deltaX: Int, deltaY: Int) = ImmutableDataPoint(x + deltaX, y + deltaY, data)
    }
}