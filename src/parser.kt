fun parse(tokens: List<Pair<String, String>>): Any {
    val ast: MutableMap<String, Any> = mutableMapOf()
    var currentNodeIndex = 0

    // Returns: Pair<String, Pair<String, String>> or Pair<String, Int>
    fun parseExpression(startIndex: Int): Any {
        var currentTokenIndex = startIndex

        val leftOperand = "token" to tokens[currentTokenIndex]
        currentTokenIndex++

        if (currentTokenIndex < tokens.size) {

            val operator = "token" to tokens[currentTokenIndex]
            currentTokenIndex++
            val rightOperand = parseExpression(currentTokenIndex)
            currentNodeIndex++

            ast[currentNodeIndex.toString()] = mapOf(
                "op" to operator,
                "left" to leftOperand,
                "right" to rightOperand
            )
        } else {
            return leftOperand
        }
        return "node" to currentNodeIndex
    }
    parseExpression(0)
    if (ast.isEmpty()) {
        return 0 to tokens[0]
    }
    return ast
}