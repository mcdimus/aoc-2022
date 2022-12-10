package util.cartesian.point

enum class Direction(val deltaX: Int, val deltaY: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    companion object {
        fun of(value: Char) = values().single { it.name.startsWith(value) }
        fun of(value: String) = values().single { it.name.startsWith(value) }
    }
}