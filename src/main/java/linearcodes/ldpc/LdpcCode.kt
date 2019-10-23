package linearcodes.ldpc

interface LdpcCode {

    fun encodeBlock(input: String): String

    fun decodeBlock(input: String): String
}
