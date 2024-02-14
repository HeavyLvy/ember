fun main() {
    val code = """
    name = "cayden"
    age = 0.15
    """.trimIndent()

    for ((i, line) in code.split("\n").withIndex()) {
        if (line != "") { // Skip empty lines
            println("$i: $line")
            val tokens = lex(line)
            val ast = parse(tokens)
        }
    }
}
