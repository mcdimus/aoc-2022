package util

fun String.toIntRange(delimiter: Char = '-') = this.split(delimiter).let { it[0].toInt()..it[1].toInt() }

infix fun IntRange.contains(other: IntRange): Boolean {
    return this.contains(other.first) && this.contains(other.last)
}

infix fun IntRange.overlaps(other: IntRange): Boolean {
    return this.contains(other.first) || this.contains(other.last)
            || other.contains(this.first) || other.contains(this.last)
}