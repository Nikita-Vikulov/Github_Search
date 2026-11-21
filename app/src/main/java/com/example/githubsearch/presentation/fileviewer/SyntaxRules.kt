package com.example.githubsearch.presentation.fileviewer

val syntaxTokenRules = listOf(
    Regex("//.*|#.*") to SyntaxTokenType.COMMENT,
    Regex("\".*?\"|'.*?'") to SyntaxTokenType.STRING,
    Regex("\\b(${KOTLIN_KEYWORDS.joinToString("|")})\\b") to SyntaxTokenType.KEYWORD,
    Regex("\\b\\d+\\b") to SyntaxTokenType.NUMBER
)
