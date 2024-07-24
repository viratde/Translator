package com.codeancy.translator.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.codeancy.translator.core.domain.util.CommonFlow
import com.codeancy.translator.core.domain.util.toCommonFlow
import com.codeancy.translator.database.TranslateDatabase
import com.codeancy.translator.translate.domain.history.HistoryDataSource
import com.codeancy.translator.translate.domain.history.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    private val translateDatabase: TranslateDatabase
) : HistoryDataSource {

    private val queries = translateDatabase.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { history ->
                history.map { it.toHistoryItem() }
            }.toCommonFlow()
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        queries.insertHistoryEntity(
            id = null,
            fromLanguageCode = historyItem.fromLanguageCode,
            fromText = historyItem.fromText,
            toLanguageCode = historyItem.toLanguageCode,
            toText = historyItem.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }


}