package com.codeancy.translator.translate.domain.translate

import com.codeancy.translator.core.domain.language.Language
import com.codeancy.translator.core.domain.util.Resource
import com.codeancy.translator.translate.domain.history.HistoryDataSource
import com.codeancy.translator.translate.domain.history.HistoryItem

class Translate(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend operator fun invoke(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {

        return try {

            val translatedText = client.translate(fromLanguage, fromText, toLanguage)
            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText,
                )
            )
            Resource.Success(translatedText)
        } catch (err: TranslateException) {
            err.printStackTrace()
            Resource.Error(err)
        }

    }

}