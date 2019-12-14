package linearcodes

class EliasOmegaUniversalCode {

    fun encode(input: List<Int>): String {
        return input.joinToString(separator = "") { encode(it) }
    }

    private fun encode(input: Int): String {
        var result = "0"
        var n = input
        while (n != 1) {
            val binary = n.toString(2)
            result = binary + result
            n = binary.length - 1
        }
        return result
    }

    fun decode(input: String): List<Int> {
        val result = ArrayList<Int>()
        var index = 0
        var n = 1
        while (index < input.length) {
            if (input[index] == '0') {
                index++
                result.add(n)
                n = 1
            } else {
                index += n + 1
                n = input.substring(index - n - 1, index).toInt(2)
            }
        }
        return result
    }
}
