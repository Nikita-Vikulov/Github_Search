@file:Suppress("MatchingDeclarationName")

package com.example.githubsearch.presentation.fileviewer

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.githubsearch.presentation.theme.SyntaxColors

enum class SyntaxTokenType {
    COMMENT, STRING, KEYWORD, NUMBER, TEXT
}

private val PROGRAMMING_KEYWORDS = setOf(
    "package", "import", "val", "var", "fun", "class", "object", "return",
    "if", "else", "when", "true", "false", "private", "public", "protected",
    "internal", "override", "suspend", "interface", "data", "enum", "null",
    "try", "catch", "finally", "throw", "for", "while", "do", "as", "is",
    "in", "out", "by", "constructor", "init", "companion", "typealias", "get",
    "set", "static", "void", "int", "float", "double", "char", "bool", "new",
    "this", "super", "abstract", "final", "sealed", "open", "def", "function",
    "const", "let", "print", "echo", "async", "await", "struct", "impl",
    "pub", "fn", "use", "mod", "template", "typename", "virtual", "namespace",
    "using", "include", "typedef", "func", "chan", "select", "map", "range",
    "type", "from", "to", "not", "and", "or", "lambda", "yield", "with",
    "except", "raise", "pass", "global", "nonlocal", "assert", "del",
    "continue", "break", "switch", "case", "default", "export", "typeof",
    "instanceof", "extends", "implements"
)

private val syntaxTokenRules = listOf(
    Regex("//.*|#.*|/\\*.*?\\*/") to SyntaxTokenType.COMMENT,
    Regex("\".*?\"|'.*?'") to SyntaxTokenType.STRING,
    Regex("\\b(${PROGRAMMING_KEYWORDS.joinToString("|")})\\b") to SyntaxTokenType.KEYWORD,
    Regex("\\b\\d+\\b") to SyntaxTokenType.NUMBER
)

fun highlightCode(text: String): AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        val length = text.length

        while (currentIndex < length) {
            var matched: MatchResult? = null
            var matchedType = SyntaxTokenType.TEXT

            for ((rule, type) in syntaxTokenRules) {
                val match = rule.find(text, currentIndex)
                if (match != null && match.range.first == currentIndex) {
                    matched = match
                    matchedType = type
                    break
                }
            }

            if (matched != null) {
                val color = when (matchedType) {
                    SyntaxTokenType.COMMENT -> SyntaxColors.comment
                    SyntaxTokenType.STRING -> SyntaxColors.string
                    SyntaxTokenType.KEYWORD -> SyntaxColors.keyword
                    SyntaxTokenType.NUMBER -> SyntaxColors.number
                    SyntaxTokenType.TEXT -> SyntaxColors.defaultText
                }
                withStyle(SpanStyle(color = color)) {
                    append(matched.value)
                }
                currentIndex = matched.range.last + 1
            } else {
                withStyle(SpanStyle(color = SyntaxColors.defaultText)) {
                    append(text[currentIndex].toString())
                }
                currentIndex++
            }
        }
    }
}
