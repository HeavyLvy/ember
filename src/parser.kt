fun parse(tokens: List<Pair<String, String>>): Any {
	val ast: MutableMap<String, Any> = mutableMapOf()
	var currentNodeIndex = 0

	// Returns: Pair<String, Pair<String, String>> or Pair<String, Int>
	fun parseExpression(startIndex: Int): Any {
		var currentTokenIndex = startIndex

		val leftOperand = "token" to tokens[currentTokenIndex]
		currentTokenIndex++

		if (currentTokenIndex < tokens.size) {
			if (tokens[currentTokenIndex].first == "lparen") { // Handle calling
				currentTokenIndex++

				val arguments: MutableList<MutableList<Pair<Any, Any>>> =
					mutableListOf() // NOTE: I want to use 'any' everywhere😭
				var argumentBuilder: MutableList<Pair<Any, Any>> = mutableListOf()

				while (tokens[currentTokenIndex].first != "rparen") {
					argumentBuilder += tokens[currentTokenIndex]
					if (tokens[currentTokenIndex].first == "comma" || tokens[currentTokenIndex + 1].first == "rparen") {
						if (tokens[currentTokenIndex].first == "comma") {
							argumentBuilder.removeLast() // Remove the extra comma
							arguments += argumentBuilder
						} else {
							arguments += argumentBuilder
						}
						argumentBuilder = mutableListOf()
					}
					currentTokenIndex++
				}
				// TODO: parse each argument, using 'for loop' then calling 'parse'
				ast[currentNodeIndex.toString()] = mapOf(
					"call" to leftOperand,
					"args" to arguments
				)
			} else { // Handle expression
				val operator = "token" to tokens[currentTokenIndex]
				currentTokenIndex++
				val rightOperand = parseExpression(currentTokenIndex)
				currentNodeIndex++

				ast[currentNodeIndex.toString()] = mapOf(
					"op" to operator,
					"left" to leftOperand,
					"right" to rightOperand
				)
			}

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