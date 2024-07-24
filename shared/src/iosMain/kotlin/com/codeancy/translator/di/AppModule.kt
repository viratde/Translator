package com.codeancy.translator.di

import com.codeancy.translator.database.TranslateDatabase
import com.codeancy.translator.translate.data.history.SqlDelightHistoryDataSource
import com.codeancy.translator.translate.data.local.DatabaseDriverFactory
import com.codeancy.translator.translate.data.remote.HttpClientFactory
import com.codeancy.translator.translate.data.translate.KtorTranslateClient
import com.codeancy.translator.translate.domain.history.HistoryDataSource
import com.codeancy.translator.translate.domain.translate.Translate

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(TranslateDatabase(DatabaseDriverFactory().create()))
    }

    private val translateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCases by lazy {
        Translate(translateClient, historyDataSource)
    }
}