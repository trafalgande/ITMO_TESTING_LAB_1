import subject.Dummy

fun main(args: Array<String>) {
    val dummy = Dummy("Arthur", intArrayOf(1, 2, 3, 4, 5, 6))
    val res = dummy.act()

    for (pair in res.first) {
        pair.invoke()
    }
}
