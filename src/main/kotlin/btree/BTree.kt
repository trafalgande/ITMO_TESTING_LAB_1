package btree

import java.util.Stack

class BTree(private val T: Int) {
    inner class Node {
        var n = 0
        var key = IntArray(2 * T - 1)
        var child = arrayOfNulls<Node>(2 * T)
        var leaf = true
        fun find(k: Int): Int {
            for (i in 0 until n)
                if (key[i] == k)
                    return i
            return -1
        }
    }

    private var root: Node?

    fun remove(key: Int) {
        val x = search(root, key) ?: return
        remove(x, key)
    }

    fun insert(key: Int) {
        val r = root
        if (r!!.n == 2 * T - 1) {
            val s = Node()
            root = s
            s.leaf = false
            s.n = 0
            s.child[0] = r
            split(s, 0, r)
            insertNode(s, key)
        } else {
            insertNode(r, key)
        }
    }

    fun contain(k: Int): Boolean {
        return search(root, k) != null
    }

    fun traverse() {
        traverse(root)
    }

    private fun search(x: Node?, key: Int): Node? {
        var i = 0
        if (x == null) return x
        while (i < x.n) {
            if (key < x.key[i]) break
            if (key == x.key[i]) return x
            i++
        }
        return if (x.leaf) null else search(x.child[i], key)
    }

    private fun split(x: Node?, pos: Int, y: Node?) {
        val z = Node()
        z.leaf = y!!.leaf
        z.n = T - 1
        for (j in 0 until T - 1)
            z.key[j] = y.key[j + T]

        if (!y.leaf)

            for (j in 0 until T)
                z.child[j] = y.child[j + T]

        y.n = T - 1

        for (j in x!!.n downTo pos + 1)
            x.child[j + 1] = x.child[j]

        x.child[pos + 1] = z
        for (j in x.n - 1 downTo pos)
            x.key[j + 1] = x.key[j]

        x.key[pos] = y.key[T - 1]
        x.n = x.n + 1
    }

    private fun insertNode(x: Node?, k: Int) {
        if (x!!.leaf) {
            var i = x.n - 1
            while (i >= 0 && k < x.key[i]) {
                x.key[i + 1] = x.key[i]
                i--
            }

            x.key[i + 1] = k
            x.n = x.n + 1
        } else {
            var i = x.n - 1
            while (i >= 0 && k < x.key[i])
                i--

            i++
            val tmp = x.child[i]
            if (tmp!!.n == 2 * T - 1) {
                split(x, i, tmp)
                if (k > x.key[i])
                    i++

            }
            insertNode(x.child[i], k)
        }
    }

    private fun remove(x: Node?, key: Int) {
        val x_ = x!!
        var pos = x_.find(key)
        if (pos != -1) {
            if (x_.leaf) {
                var i = 0
                while (i < x_.n && x_.key[i] != key)
                    i++

                while (i < x_.n) {
                    if (i != 2 * T - 2)
                        x_.key[i] = x_.key[i + 1]
                    i++
                }
                x_.n--
                return
            }
            if (!x_.leaf) {
                var pred = x_.child[pos]
                val predKey: Int
                if (pred!!.n >= T) {
                    while (true) {
                        if (pred!!.leaf) {
                            println(pred.n)
                            predKey = pred.key[pred.n - 1]
                            break
                        } else
                            pred = pred.child[pred.n]

                    }
                    remove(pred, predKey)
                    x_.key[pos] = predKey
                    return
                }
                var nextNode = x_.child[pos + 1]
                if (nextNode!!.n >= T) {
                    var nextKey = nextNode.key[0]
                    if (!nextNode.leaf) {
                        nextNode = nextNode.child[0]
                        while (true) {
                            if (nextNode!!.leaf) {
                                nextKey = nextNode.key[nextNode.n - 1]
                                break
                            } else
                                nextNode = nextNode.child[nextNode.n]
                        }
                    }
                    remove(nextNode, nextKey)
                    x_.key[pos] = nextKey
                    return
                }
                var temp = pred.n + 1
                pred.key[pred.n++] = x_.key[pos]
                run {
                    var i = 0
                    var j = pred.n
                    while (i < nextNode.n) {
                        pred.key[j++] = nextNode.key[i]
                        pred.n++
                        i++
                    }
                }
                for (i in 0 until nextNode.n + 1)
                    pred.child[temp++] = nextNode.child[i]

                x_.child[pos] = pred
                for (i in pos until x_.n)
                    if (i != 2 * T - 2) {
                        x_.key[i] = x_.key[i + 1]

                        for (j in pos + 1 until x_.n + 1)
                            if (j != 2 * T - 1)
                                x_.child[j] = x_.child[j + 1]

                        x_.n--
                        if (x_.n == 0)
                            if (x_ === root)
                                root = x_.child[0]

                        remove(pred, key)
                        return
                    }
            } else {
                pos = 0
                while (pos < x_.n) {
                    if (x_.key[pos] > key)
                        break
                    pos++
                }
                val tmp = x_.child[pos]
                if (tmp!!.n >= T) {
                    remove(tmp, key)
                    return
                }
                val nb: Node?
                val devider: Int
                if (pos != x_.n && x_.child[pos + 1]!!.n >= T) {
                    devider = x_.key[pos]
                    nb = x_.child[pos + 1]
                    x_.key[pos] = nb!!.key[0]
                    tmp.key[tmp.n++] = devider
                    tmp.child[tmp.n] = nb.child[0]
                    for (i in 1 until nb.n)
                        nb.key[i - 1] = nb.key[i]

                    for (i in 1..nb.n)
                        nb.child[i - 1] = nb.child[i]

                    nb.n--
                    remove(tmp, key)
                    return
                } else if (pos != 0 && x_.child[pos - 1]!!.n >= T) {
                    devider = x_.key[pos - 1]
                    nb = x_.child[pos - 1]
                    x_.key[pos - 1] = nb!!.key[nb.n - 1]
                    val child = nb.child[nb.n]
                    nb.n--
                    for (i in tmp.n downTo 1)
                        tmp.key[i] = tmp.key[i - 1]

                    tmp.key[0] = devider
                    for (i in tmp.n + 1 downTo 1)
                        tmp.child[i] = tmp.child[i - 1]

                    tmp.child[0] = child
                    tmp.n++
                    remove(tmp, key)
                    return
                } else {
                    val lt: Node?
                    val rt: Node?

                    if (pos != x_.n) {
                        devider = x_.key[pos]
                        lt = x_.child[pos]
                        rt = x_.child[pos + 1]
                    } else {
                        devider = x_.key[pos - 1]
                        rt = x_.child[pos]
                        lt = x_.child[pos - 1]

                        pos--
                    }
                    for (i in pos until x_.n - 1)
                        x_.key[i] = x_.key[i + 1]

                    for (i in pos + 1 until x_.n)
                        x_.child[i] = x_.child[i + 1]

                    x_.n--
                    lt!!.key[lt.n++] = devider
                    var i = 0
                    var j = lt.n
                    while (i < rt!!.n + 1) {
                        if (i < rt.n) {
                            lt.key[j] = rt.key[i]
                        }
                        lt.child[j] = rt.child[i]
                        i++
                        j++
                    }
                    lt.n += rt.n
                    if (x_.n == 0)
                        if (x_ === root)
                            root = x_.child[0]
                    remove(lt, key)
                    return
                }
            }
        }
    }

    private fun findKeys(a: Int, b: Int, x: Node?, st: Stack<Int>) {
        var i = 0
        while (i < x!!.n && x.key[i] < b) {
            if (x.key[i] > a)
                st.push(x.key[i])

            i++
        }
        if (!x.leaf)
            for (j in 0 until i + 1)
                findKeys(a, b, x.child[j], st)

        }

    private fun traverse(x: Node?) {
        for (i in 0 until x!!.n)
            print(x.key[i].toString() + " ")
        if (!x.leaf)
            for (i in 0 until x.n + 1)
                traverse(x.child[i])
    }

    init {
        root = Node()
        root!!.n = 0
        root!!.leaf = true
    }
}
