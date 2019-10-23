package linearcodes

interface BlockCode {

    fun encodeBlock(input: String): String

    fun decodeBlock(input: String): String
}
