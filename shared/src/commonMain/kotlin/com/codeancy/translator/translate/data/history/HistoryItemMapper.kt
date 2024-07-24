package com.codeancy.translator.translate.data.history

import com.codeancy.translator.database.HistoryEntity
import com.codeancy.translator.translate.domain.history.HistoryItem

fun HistoryEntity.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id = id,
        fromText = fromText,
        fromLanguageCode = fromLanguageCode,
        toLanguageCode = toLanguageCode,
        toText = toText
    )
}