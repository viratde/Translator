package com.codeancy.translator.translate.domain.translate

import com.codeancy.translator.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ) : String

}