package subject

import java.util.ArrayList

data class Dummy(val name: String?, val actionOrder: IntArray?) {
    fun act(): Pair<ArrayList<() -> Unit>, IntArray?> {
        val buffer = ArrayList<() -> Unit>(10)
        for (i in actionOrder!!.indices) {
            val action: () -> Unit = resolveAction(this, actionOrder[i])
            buffer.add(action)
        }
        return Pair(buffer, actionOrder)
    }

    private fun resolveAction(me: Dummy, n: Int): () -> Unit {
        return when (n) {
            1 -> come(me)
            2 -> notice(me, null)
            3 -> sap(me)
            4 -> describe(null)
            5 -> dontBelieve(me)
            6 -> jews(me)
            else -> fun() { println("stub") }
        }
    }

    private fun come(who: Dummy): () -> Unit {
        return { println("${who.name}, нервничая, вошел следом") }
    }

    private fun notice(who: Dummy, whom: Dummy?): () -> Unit {
        return {
            println(
                "Потому что ${who.name} увидел развалившегося в кресле" +
                        "${whom?.name ?: "человека"}, "
            )
        }
    }

    private fun sap(who: Dummy): () -> Unit {
        return { println("${who.name} был ошеломлен") }
    }


    private fun describe(whom: Dummy?): () -> Unit {
        return {
            println("${whom?.name ?: ""}положившего ноги на пульт управления и ковыряющего левой рукой в зубах правой головы. " +
                    "Правая голова, казалось, была всецело занята этим, но зато левая улыбалась широко и непринужденно.")
        }
    }

    private fun dontBelieve(who: Dummy): () -> Unit {
        return {
            println("Количество вещей, видя которые, ${who.name} не верил своим глазам, все росло.")
        }
    }

    private fun jews(who: Dummy): () -> Unit {
        return {
            print("Его [${who.name}] челюсть отвисла")
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dummy

        if (name != other.name) return false
        if (!actionOrder.contentEquals(other.actionOrder)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + actionOrder.contentHashCode()
        return result
    }
}
