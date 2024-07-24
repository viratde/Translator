package com.codeancy.translator.translate.data.translate

import com.codeancy.translator.NetworkConstants
import com.codeancy.translator.core.domain.language.Language
import com.codeancy.translator.translate.domain.translate.TranslateClient
import com.codeancy.translator.translate.domain.translate.TranslateError
import com.codeancy.translator.translate.domain.translate.TranslateException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class KtorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {


    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {

        val result = try {
            httpClient.post {
                url(NetworkConstants.BASE_URL + "/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDao(
                        textToTranslate = fromText,
                        targetLanguageCode = toLanguage.langCode,
                        sourceLanguageCode = fromLanguage.langCode
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }



        when (result.status.value) {
            in 200..299 -> Unit
            in 500..599 -> throw TranslateException(TranslateError.SERVER_ERROR)
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        try {
            return result.body<TranslatedDto>().translatedText
        } catch (err: Exception) {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }

    }

}