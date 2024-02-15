fun main() {
	val code = """
    message = 'Hello, World
	print(message + '👋')
    """.trimIndent()
	val lines = code.split("\n")
	var lineNum = 0
	try {
		for ((i, line) in lines.withIndex()) {
			lineNum++
			if (line.isNotBlank()) { // Skip empty lines
				println("\n$i: \"$line\"")

				val tokens = lex(line)
				val ast = parse(tokens)

				println("\tTOKENS: $tokens\n\tAST: $ast")
			}
		}
	} catch (e: ParseErrorException) { // Special error raised from parser
//		println("  File \"<stdin>\", line $lineNum\n\t${lines[lineNum]}\n${e.toString()}") // pythonic
		println("${Color.RED}${e.errorType} in '<stdin>' on line: $lineNum\n\t${lines[lineNum - 1]}\n${e.message}")
	}
}

class ParseErrorException(message: String, val errorType: String = "Exception") : Exception(message) {
	override fun toString(): String {
		return "$errorType: $message"
	}
}

class Color {
	companion object {
		const val RESET = "\u001B[0m"
		const val BLACK = "\u001B[30m"
		const val RED = "\u001B[31m"
		const val GREEN = "\u001B[32m"
		const val YELLOW = "\u001B[33m"
		const val BLUE = "\u001B[34m"
		const val MAGENTA = "\u001B[35m"
		const val CYAN = "\u001B[36m"
		const val WHITE = "\u001B[37m"

		fun colorText(text: String, color: String): String {
			return "$color$text$RESET"
		}
	}
}

