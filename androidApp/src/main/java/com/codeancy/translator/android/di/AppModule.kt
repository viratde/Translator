package com.codeancy.translator.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.codeancy.translator.database.TranslateDatabase
import com.codeancy.translator.translate.data.history.SqlDelightHistoryDataSource
import com.codeancy.translator.translate.data.local.DatabaseDriverFactory
import com.codeancy.translator.translate.data.remote.HttpClientFactory
import com.codeancy.translator.translate.data.translate.KtorTranslateClient
import com.codeancy.translator.translate.domain.history.HistoryDataSource
import com.codeancy.translator.translate.domain.translate.Translate
import com.codeancy.translator.translate.domain.translate.TranslateClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun providesTranslationClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun providesDatabaseDriverFactory(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun providesHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun providesTranslateUseCases(
        client: TranslateClient,
        historyDataSource: HistoryDataSource
    ): Translate {
        return Translate(client, historyDataSource)
    }

}