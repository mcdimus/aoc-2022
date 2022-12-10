package util.cartesian.point

data class MutablePoint(override var x: Int, override var y: Int) : Point<MutablePoint> {
    override fun move(deltaX: Int, deltaY: Int) = MutablePoint(x + deltaX, y + deltaY)

    companion object {
        fun of(x: Int,y: Int) = MutablePoint(x, y)
    }
}