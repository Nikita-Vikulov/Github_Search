package com.example.githubsearch.presentation.common

import androidx.compose.ui.graphics.Color
import com.example.githubsearch.presentation.theme.AppColors

enum class ProgrammingLanguage(val key: String, val color: Color) {
    KOTLIN("kotlin", AppColors.langKotlin),
    JAVA("java", AppColors.langJava),
    PYTHON("python", AppColors.langPython),
    JAVASCRIPT("javascript", AppColors.langJavascript),
    TYPESCRIPT("typescript", AppColors.langTypescript),
    HTML("html", AppColors.langHtml),
    CSS("css", AppColors.langCss),
    CPP("c++", AppColors.langCpp),
    CSHARP("c#", AppColors.langCsharp),
    C("c", AppColors.langC),
    GO("go", AppColors.langGo),
    RUST("rust", AppColors.langRust),
    SWIFT("swift", AppColors.langSwift),
    RUBY("ruby", AppColors.langRuby),
    PHP("php", AppColors.langPhp);

    companion object {
        fun fromKey(key: String?): ProgrammingLanguage? {
            if (key.isNullOrBlank()) return null
            return entries.firstOrNull { it.key.equals(key, ignoreCase = true) }
        }

        fun getColorForLanguage(language: String?): Color {
            return fromKey(language)?.color ?: AppColors.langDefault
        }
    }
}
