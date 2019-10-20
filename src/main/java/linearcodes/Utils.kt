package linearcodes

object Utils {

    fun stringToList(s: String): List<Byte> {
        return s.map { it.toString().toByte() }
    }

    fun listToString(l: List<Byte>): String {
        return l.joinToString(separator = "")
    }
}
