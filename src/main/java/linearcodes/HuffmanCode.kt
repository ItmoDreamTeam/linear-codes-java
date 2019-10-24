package linearcodes

class HuffmanCode {

    fun apply(symbols: Set<Symbol>): List<EncodedSymbol> {
        val symbolNodes = symbols.map { SymbolTreeNode(it.value, it.frequency) }
        val tree: MutableList<TreeNode> = symbolNodes.toMutableList()
        while (tree.size > 1) {
            tree.sortBy { it.frequency }
            val leftNode = tree.removeAt(0)
            leftNode.propagate("0")
            val rightNode = tree.removeAt(0)
            rightNode.propagate("1")
            tree.add(TreeNode.of(leftNode, rightNode))
        }
        return symbolNodes.map { EncodedSymbol(it.value, it.frequency, it.codeword) }.sortedByDescending { it.frequency }
    }

    data class Symbol(val value: String, val frequency: Double)

    data class EncodedSymbol(val value: String, val frequency: Double, val codeword: String)

    private open class TreeNode(val frequency: Double, val leftNode: TreeNode? = null, val rightNode: TreeNode? = null) {
        open fun propagate(bit: String) {
            leftNode?.propagate(bit)
            rightNode?.propagate(bit)
        }

        companion object {
            fun of(leftNode: TreeNode, rightNode: TreeNode): TreeNode {
                return TreeNode(leftNode.frequency + rightNode.frequency, leftNode, rightNode)
            }
        }
    }

    private class SymbolTreeNode(val value: String, frequency: Double) : TreeNode(frequency) {
        var codeword: String = ""

        override fun propagate(bit: String) {
            codeword = bit + codeword
        }
    }
}
