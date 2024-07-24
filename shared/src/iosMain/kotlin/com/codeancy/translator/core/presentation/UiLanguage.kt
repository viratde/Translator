package com.codeancy.translator.core.presentation

import com.codeancy.translator.core.domain.language.Language

actual class UiLanguage(
    actual val language: Language,
    val imageName: String
) {
    actual companion object {
        actual fun byCode(code: String): UiLanguage {
            return allLanguages.find {
                it.language.langCode == code
            } ?: throw IllegalArgumentException("Language with code $code not found")
        }

        actual val allLanguages: List<UiLanguage>
            get() = Language.entries.map { language ->
                UiLanguage(
                    language = language,
                    imageName = language.langName.lowercase()
                )
            }
    }

}