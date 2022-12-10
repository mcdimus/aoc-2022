package util.cartesian.grid

import util.cartesian.point.MutableDataPoint

class MutableDataGrid<T>(
    override val values: List<MutableDataPoint<T>>,
    override val width: Int
) : DataGrid<MutableDataPoint<T>, T> {

    init {
        require(values.size % width == 0) { "Not a rectangular grid" }
    }

    companion object {
        fun <T> of(input: List<String>, dataMapper: (Char) -> T): MutableDataGrid<T> {
            val width = input.first().length
            val values = input.flatMapIndexed { y, row ->
                row.toCharArray().mapIndexed { x, c -> MutableDataPoint(x = x, y = y, data = dataMapper(c)) }
            }
            return MutableDataGrid(values, width)
        }
    }
}