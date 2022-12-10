package util.cartesian.point

enum class DiagonalDirection(val deltaX: Int, val deltaY: Int) {
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);
}