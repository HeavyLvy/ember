fun main() {
    val code = """
    print = "Hello, World"
    result = print(34 + 5, 12)
    """.trimIndent()
    for ((i, line) in code.split("\n").withIndex()) {
        if (line != "") { // Skip empty lines
            println("\n$i: $line")
            val tokens = lex(line)
//            println(tokens)
            val ast = parse(tokens)
//            println(ast)
            println("\tTOKENS: $tokens\n\tAST: $ast")
        }
    }
}
