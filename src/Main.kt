fun main() {
    val code = """
    name = "John Doe"
    age = 28
    height = 6.5
    """.trimIndent()
    for ((i, line) in code.split("\n").withIndex()) {
        if (line != "") { // Skip empty lines
            println("$i: $line")
            val tokens = lex(line)
            val ast = parse(tokens)
            println(ast)
        }
    }
}
