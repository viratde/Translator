package com.codeancy.translator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codeancy.translator.Greeting
import com.codeancy.translator.android.core.presentation.Routes
import com.codeancy.translator.android.translate.presentation.AndroidTranslateViewModel
import com.codeancy.translator.android.translate.presentation.TranslateScreen
import com.codeancy.translator.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.codeancy.translator.android.voice_to_text.presentation.VoiceToTextScreen
import com.codeancy.translator.translate.presentation.TranslateEvent
import com.codeancy.translator.voice_to_text.presentation.VoiceToTextEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}


@Composable
fun TranslateRoot(
) {


    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.TRANSLATE
    ) {

        composable(Routes.TRANSLATE) {

            val androidTranslateViewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by androidTranslateViewModel.state.collectAsState()
            val voiceResult by it.savedStateHandle.getStateFlow<String?>("voiceResult", null)
                .collectAsState()

            LaunchedEffect(voiceResult) {
                androidTranslateViewModel.onEvent(
                    TranslateEvent.SubmitVoiceResult(result = voiceResult)
                )
                it.savedStateHandle["voiceResult"] = null
            }

            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(
                                Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}"
                            )
                        }

                        else -> androidTranslateViewModel.onEvent(event)
                    }
                }
            )

        }

        composable(
            Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    defaultValue = "en"
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val languageCode = backStackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel = hiltViewModel<AndroidVoiceToTextViewModel>()
            val state = viewModel.state.collectAsState()

            VoiceToTextScreen(
                state = state.value,
                languageCode = languageCode,
                onEvent = { event ->
                    when (event) {
                        VoiceToTextEvent.Close -> {
                            navController.popBackStack()
                        }

                        else -> viewModel.onEvent(event)
                    }

                },
                onResult = { spokenText ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "voiceResult", spokenText
                    )
                    navController.popBackStack()
                }
            )
        }

    }

}
