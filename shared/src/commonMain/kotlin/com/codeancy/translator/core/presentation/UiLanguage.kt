package com.codeancy.translator.core.presentation

import com.codeancy.translator.core.domain.language.Language

expect class UiLanguage {

    val language: Language

    companion object {
        fun byCode(code: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }

}