package com.codeancy.translator.translate.domain.history

import com.codeancy.translator.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(historyItem: HistoryItem)
}