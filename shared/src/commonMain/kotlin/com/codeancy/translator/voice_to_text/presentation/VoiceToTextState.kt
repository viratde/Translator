package com.codeancy.translator.voice_to_text.presentation

data class VoiceToTextState(
    val powerRatios: List<Float> = listOf(),
    val spokenText: String = "",
    val canRecord: Boolean = false,
    val recordError: String? = null,
    val displayState: DisplayState? = null
)


enum class DisplayState {
    WAITING_TO_TALK,
    SPEAKING,
    DISPLAYING_RESULT,
    ERROR
}