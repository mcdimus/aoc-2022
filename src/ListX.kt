fun <T> List<T>.partitionBy(condition: (T) -> Boolean) = partitionBy(condition) { it }
fun <T, R> List<T>.partitionBy(condition: (T) -> Boolean, valueConverter: (T) -> R): List<List<R>> {
    return buildList {
        var partition = mutableListOf<R>()
        this@partitionBy.forEach {
            if (condition(it)) {
                this.add(partition)
                partition = mutableListOf()
            } else {
                partition.add(valueConverter.invoke(it))
            }
        }
        this.add(partition)
    }
}