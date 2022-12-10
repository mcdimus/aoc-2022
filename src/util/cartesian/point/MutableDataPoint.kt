package util.cartesian.point

data class MutableDataPoint<T>(override var x: Int, override var y: Int, override var data: T) : DataPoint<T> {
    override fun move(deltaX: Int, deltaY: Int) = MutableDataPoint(x + deltaX, y + deltaY, data)
}