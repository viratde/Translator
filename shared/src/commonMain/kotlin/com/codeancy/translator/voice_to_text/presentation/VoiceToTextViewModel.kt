package com.codeancy.translator.voice_to_text.presentation

import com.codeancy.translator.voice_to_text.domain.VoiceToTextParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    private val parser: VoiceToTextParser,
    coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VoiceToTextState())

    val state = _state.combine(
        parser.state
    ) { state, voiceResult ->
        state.copy(
            spokenText = voiceResult.result,
            recordError = if(state.canRecord) voiceResult.error else "Cannot record without permission.",
            displayState = when {
                voiceResult.error != null || !state.canRecord -> DisplayState.ERROR
                voiceResult.result.isNotBlank() && !voiceResult.isSpeaking -> DisplayState.DISPLAYING_RESULT
                voiceResult.result.isNotBlank() && voiceResult.isSpeaking -> DisplayState.SPEAKING
                else -> DisplayState.WAITING_TO_TALK
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VoiceToTextState())

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.SPEAKING) {
                    _state.update {
                        it.copy(
                            powerRatios = it.powerRatios + parser.state.value.powerRatio
                        )
                    }
                }
                delay(50L)
                println("I am running")
            }
        }
    }

    fun onEvent(event: VoiceToTextEvent) {

        when (event) {
            is VoiceToTextEvent.PermissionResult -> {
                _state.update {
                    it.copy(canRecord = event.isGranted)
                }
            }

            VoiceToTextEvent.Reset -> {
                parser.reset()
                _state.update { VoiceToTextState() }
            }

            is VoiceToTextEvent.ToggleRecording -> toggleListening(event.languageCode)
            else -> {}
        }

    }

    private fun toggleListening(languageCode: String) {
        parser.cancel()
        if (state.value.displayState == DisplayState.SPEAKING) {
            parser.stopListening()
        } else {
            parser.startListening(languageCode)
        }
    }

}