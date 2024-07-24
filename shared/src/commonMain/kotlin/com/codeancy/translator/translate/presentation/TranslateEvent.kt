package com.codeancy.translator.translate.presentation

import com.codeancy.translator.core.presentation.UiLanguage
import com.codeancy.translator.translate.domain.history.HistoryItem

sealed class TranslateEvent {
    data class ChooseFromLanguage(val language: UiLanguage) : TranslateEvent()
    data class ChooseToLanguage(val language: UiLanguage) : TranslateEvent()
    data object StopChoosingLanguages : TranslateEvent()
    data object SwapLanguages : TranslateEvent()
    data class ChangeTranslationText(val text: String) : TranslateEvent()
    data object OpenFromLanguageDropDown : TranslateEvent()
    data object OpenToLanguageDropDown : TranslateEvent()
    data object CloseTranslation : TranslateEvent()
    data class SelectHistoryItem(val item: UiHistoryItem) : TranslateEvent()
    data object EditTranslation : TranslateEvent()
    data object RecordAudio : TranslateEvent()
    data class SubmitVoiceResult(val result: String?) : TranslateEvent()
    data object onErrorSeen : TranslateEvent()
    data object Translate : TranslateEvent()

}