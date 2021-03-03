package taylorseries

import kotlin.math.abs
import kotlin.math.pow


fun asin(x: Double, eps: Double): Double {
    return if (abs(x) > 1)
        Double.NaN
    else {
        var res = 0.0
        var prev = 1.0
        var n = 0
        while (abs(res - prev) > eps) {
            prev = res
            val numerator = x.pow((2 * n + 1)) * factorial(2 * n)
            val denominator = (4.0.pow(n) * factorial(n) * factorial(n) * (2 * n + 1))
            res += numerator / denominator
            n++
        }
        res
    }
}

fun factorial(f: Int): Double {
    return if (f <= 1)
        1.0
    else
        f * factorial(f - 1)
}
