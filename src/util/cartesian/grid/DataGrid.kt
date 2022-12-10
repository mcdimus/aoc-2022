package util.cartesian.grid

import util.cartesian.point.DataPoint

interface DataGrid<P : DataPoint<T>, T> : Grid<P, DataGrid<P, T>> {

    companion object {
        fun <T> of(values: List<DataPoint<T>>, width: Int): DataGrid<DataPoint<T>, T> =
            ImmutableDataGrid(values, width)

        fun <T> of(input: List<String>, dataMapper: (Char) -> T): DataGrid<DataPoint<T>, T> {
            val width = input.first().length
            val values = input.flatMapIndexed { y, row ->
                row.toCharArray().mapIndexed { x, c -> DataPoint.of(x = x, y = y, data = dataMapper(c)) }
            }
            return of(values, width)
        }
    }

    private class ImmutableDataGrid<T>(
        override val values: List<DataPoint<T>>,
        override val width: Int
    ) : DataGrid<DataPoint<T>, T> {
        init {
            require(values.size % width == 0) { "Not a rectangular grid" }
        }
    }

}