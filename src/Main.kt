import kotlin.system.exitProcess

fun main() {
    val code = """
    x =
    message = 'Hello, World'
    print(message + '👋')
    """.trimIndent()
    for ((i, line) in code.split("\n").withIndex()) {
        if (line != "") { // Skip empty lines

            fun raise(msg: Any = "", errorType: String = "Exception") {
                println("$errorType on line: ${i+1}\n\t$msg")
                exitProcess(-1)
            }

            println("\n$i: \"$line\"")
            val tokens = lex(line)
//            println(tokens)
            val ast = parse(tokens, ::raise)
//            println(ast)
            println("\tTOKENS: $tokens\n\tAST: $ast")
        }
    }
}
