fun parse(tokens: List<Pair<Any, Any>>): Any {
	val ast: MutableMap<String, Any> = mutableMapOf()
	var currentNodeIndex = -1

	// Returns: Pair<String, Pair<String, String>> or Pair<String, Int>
	fun parseExpression(startIndex: Int): Any {
		var currentTokenIndex = startIndex

		try {
			tokens[currentTokenIndex]
		} catch (e: IndexOutOfBoundsException) {
			throw ParseErrorException("Invalid Syntax", "SyntaxError")
		}
		val leftOperand = "token" to tokens[currentTokenIndex]

		currentTokenIndex++

		if (currentTokenIndex < tokens.size) {
			if (tokens[currentTokenIndex].first == "lparen") { // Handle calling
				if (tokens[currentTokenIndex-1].first !=  "id") {
					throw ParseErrorException("Invalid Syntax", "SyntaxError")
				}
				currentTokenIndex++

				val arguments: MutableList<MutableList<Pair<Any, Any>>> =
					mutableListOf() // NOTE: I want to use 'any' everywhere😭
				var argumentBuilder: MutableList<Pair<Any, Any>> = mutableListOf()
				var layer = 0 // used to keep track of what ( or ) is on.
				while (layer != 1) { // Stupid linter. it's not always true cuz it will change its layer if proper syntax
					if (tokens[currentTokenIndex].first == "rparen") layer++
					if (tokens[currentTokenIndex].first == "lparen") layer--
					if (layer == 1) break

					argumentBuilder += tokens[currentTokenIndex]
					if (layer == 0 && tokens[currentTokenIndex].first == "comma" || layer == 0 && tokens[currentTokenIndex + 1].first == "rparen") {
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
				// TO-DO COMPLETE: parse each argument, using 'for loop' then calling 'parse' function. NOT 'parseExpression'.
				val parsedArguments: MutableList<Any> = mutableListOf()
				println(arguments)
				for (argument in arguments) {
					val parsedArgument = parse(argument)
					parsedArguments += parsedArgument
				}
				currentNodeIndex++
				ast[currentNodeIndex.toString()] = mapOf(
					"call" to leftOperand,
					"args" to parsedArguments
				)
				// TODO: finish parsing after parsing a Call.
			} else if (leftOperand.first == "func") {
				println("AGGHHHH")
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