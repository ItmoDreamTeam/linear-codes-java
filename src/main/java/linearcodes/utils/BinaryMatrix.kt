package linearcodes.utils

import java.util.stream.IntStream.range

class BinaryMatrix(val content: List<List<Int>>) {

    val height = content.size
    val width = content[0].size

    init {
        require(content.isNotEmpty())
        require(content[0].isNotEmpty())
        require(range(1, content.size).allMatch { content[it].size == content[0].size })
    }

    fun getAsIntsVector(): List<Int> {
        return content[0]
    }

    fun getAsBytesVector(): List<Byte> {
        return content[0].map { it.toByte() }
    }

    fun isZero(): Boolean {
        return content.all { it.all { it == 0 } }
    }

    fun mult(matrix: BinaryMatrix): BinaryMatrix {
        require(width == matrix.height)
        val rows = ArrayList<List<Int>>()
        for (rowIndex in 0 until height) {
            val row = ArrayList<Int>()
            for (colIndex in 0 until matrix.width) {
                row.add(computeMultElement(this, matrix, rowIndex, colIndex))
            }
            rows.add(row)
        }
        return ofInts(rows)
    }

    fun transpose(): BinaryMatrix {
        val rows = ArrayList<List<Int>>()
        for (rowIndex in 0 until width) {
            val row = ArrayList<Int>()
            for (colIndex in 0 until height) {
                row.add(content[colIndex][rowIndex])
            }
            rows.add(row)
        }
        return ofInts(rows)
    }

    companion object {

        fun ofInts(content: List<List<Int>>): BinaryMatrix {
            return BinaryMatrix(content)
        }

        fun ofBytes(content: List<List<Byte>>): BinaryMatrix {
            return BinaryMatrix(content.map { it.map { it.toInt() } })
        }

        private fun computeMultElement(a: BinaryMatrix, b: BinaryMatrix, rowIndex: Int, colIndex: Int): Int {
            var sum = 0
            for (i in a.content[0].indices) {
                sum += a.content[rowIndex][i] * b.content[i][colIndex]
                sum %= 2
            }
            return sum
        }
    }
}
