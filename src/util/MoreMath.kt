package util

import kotlin.math.sqrt

object MoreMath {

    /**
     * Returns the Greatest Common Divisor for the given numbers.
     *
     * *See also:* [Greatest Common Divisor | Brilliant Math & Science Wiki](https://brilliant.org/wiki/greatest-common-divisor/)
     */
    fun gcd(input: LongArray): Long {
        var result = input[0]
        for (i in 1 until input.size) result = gcd(result, input[i])
        return result
    }

    fun gcd(a: Long, b: Long): Long {
        var n1 = a
        var n2 = b
        while (n2 > 0) {
            val temp = n2
            n2 = n1 % n2
            n1 = temp
        }
        return n1
    }

    fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    fun lcm(input: List<Long>): Long {
        return lcm(input.toLongArray())
    }

    fun lcm(input: LongArray): Long {
        var result = input[0]
        for (i in 1 until input.size) result = lcm(result, input[i])
        return result
    }

    fun primeFactorsOf(number: Int): List<Int> {
        return buildList {
            var n = number
            while (n % 2 == 0) {
                add(2)
                n /= 2
            }

            val squareRoot = sqrt(n.toDouble()).toInt()
            for (i in 3..squareRoot step 2) {
                while (n % i == 0) {
                    add(i)
                    n /= i
                }
            }
            if (n > 2) {
                add(n)
            }
        }
    }

}