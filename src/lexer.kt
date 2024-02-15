fun lex(code: String): List<Pair<String, String>> {
    /**
     * # Patterns
     * ## Datatypes:
     * - "double": Matches a double value (e.g., 3.14).
     * - "int": Matches an integer value (e.g., 42).
     * - "string": Matches a string enclosed in double or single quotes (e.g., "Hello" or 'World').
     * - "bool": Matches boolean values (e.g., true or false).
     * - "null": Matches the null keyword.
     * ## Keywords:
     * - "import": Matches the import keyword.
     * - "if": Matches the if keyword.
     * - "else": Matches the else keyword.
     * - "func": Matches the func keyword.
     * - "return": Matches the return keyword.
     * - "then": Matches the then keyword.
     * - "end": Matches the end keyword.
     * - "and": Matches the logical "and" operator (&&).
     * - "or": Matches the logical "or" operator (||).
     * ## Identifiers:
     * - "id": Matches an identifier (variable names, function names, etc.).
     * ## Operators:
     * - "noteql": Matches the "not equal to" operator (!=).
     * - "not": Matches the "not" keyword or operator (!).
     * - "eql": Matches the "equal to" operator (==).
     * - "assign": Matches the assignment operator (=).
     * - "add": Matches the addition operator (+).
     * - "sub": Matches the subtraction operator (-).
     * - "mul": Matches the multiplication operator (*).
     * - "div": Matches the division operator (/).
     * - "mod": Matches the modulo operator (%).
     * - "greater": Matches the greater than operator (>).
     * - "less": Matches the less than operator (<).
     * - "lparen": Matches the left parenthesis symbol (().
     * - "rparen": Matches the right parenthesis symbol ()).
     * */
    val tokens: MutableList<Pair<String, String>> = mutableListOf()
    val patterns = mapOf(
        "double" to "[0-9]+\\.[0-9]+",
        "int" to "\\d+(?<!\\.\\d\\*)",
        "string" to "\"([^\"]*)\"|'([^\']*)'",
        "bool" to "true|false",
        "null" to "null",


        "import" to "import",
        "if" to "if",
        "else" to "else",
        "func" to "func",
        "return" to "return",
        "and" to "and|\\&",
        "or" to "or|\\|",
        "then" to "then",
        "end" to "end",


        "id" to "[a-zA-Z_]\\w*",

        "noteql" to "!=",
        "not" to "not|\\!",
        "eql" to "==",
        "assign" to "[=]",

        "add" to "\\+",
        "sub" to "\\-",
        "mul" to "\\*",
        "div" to "\\/",
        "mod" to "\\%",
        "greater" to "\\>",
        "less" to "\\<",
        "lparen" to "\\(",
        "rparen" to "\\)",
        "comma" to "\\,",
        )

    // Create the final pattern
    val finalPatternSb = StringBuilder()
    patterns.values.forEach { pattern -> finalPatternSb.append("$pattern|") }
    val finalPattern = Regex("(${finalPatternSb.dropLast(1)})") // Drop the last "|"

    // Find matches
    val matches = finalPattern.findAll(code)
    val foundMatches = matches.map { it.value }.toList()

    // Assign matches with token
    for (match in foundMatches) {
        for ((left, right) in patterns) {
            if (Regex(right).find(match) != null) {
                tokens += if (left == "string") {
                    left to match.dropLast(1).drop(1)
                } else {
                    left to match
                }
                break
            }
        }
    }
    return tokens
}
