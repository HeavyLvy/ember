fun main() {
    val code = """
    name = "cayden"
    age = 0.15
    """.trimIndent()

    for ((i, line) in code.split("\n").withIndex()) {
        if (line != "") { // Skip empty lines
            println("$i: $line")
            println(lex(line))
        }
    }
}
