fun lex(code: String): List<Pair<String, String>> {
    val tokens: MutableList<Pair<String, String>> = mutableListOf()
    val patterns = mapOf(
        // Types
        "" to "ÂåćÇ↕↞↯↭¾½⅓ⁿ",
        "double" to "[0-9]+\\.[0-9]+",
        "int" to "\\d+(?<!\\.\\d\\*)",
        "string" to "\"([^\"]*)\"",
        "bool" to "true|false",
        "null" to "null",

        // Keywords
        "import" to "import",
        "if" to "if",
        "else" to "else",
        "func" to "func",
        "return" to "return",
        "and" to "and|\\&",
        "or" to "or|\\|",
        "not" to "not|\\!",

        // Identifier
        "id" to "[a-zA-Z_]\\w*",

        // Operations
        "comp" to "==",
        "assign" to "[=]",

        // Mathematical Operations
        "add" to "\\+",
        "sub" to "\\-",
        "mul" to "\\*",
        "div" to "\\/",
        "mod" to "\\%",
        "greater" to "\\>",
        "less" to "\\<",
        "lparen" to "\\(",
        "rparen" to "\\)",
    )
    // Create the final pattern
    val finalPatternSb = StringBuilder()
    patterns.values.forEach { pattern -> finalPatternSb.append("$pattern|") }
    val finalPattern = Regex("\\(${finalPatternSb.dropLast(1)}\\)") // Drop the last "|"

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
