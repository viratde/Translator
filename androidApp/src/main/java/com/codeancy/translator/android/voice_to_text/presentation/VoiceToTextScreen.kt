package com.codeancy.translator.android.voice_to_text.presentation

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codeancy.translator.android.R
import com.codeancy.translator.android.core.theme.LightBlue
import com.codeancy.translator.android.voice_to_text.presentation.components.VoiceRecorderDisplay
import com.codeancy.translator.voice_to_text.presentation.DisplayState
import com.codeancy.translator.voice_to_text.presentation.VoiceToTextEvent
import com.codeancy.translator.voice_to_text.presentation.VoiceToTextState

@Composable
fun VoiceToTextScreen(
    state: VoiceToTextState,
    languageCode: String,
    onResult: (String) -> Unit,
    onEvent: (VoiceToTextEvent) -> Unit
) {

    val context = LocalContext.current

    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onEvent(
                VoiceToTextEvent
                    .PermissionResult(
                        isGranted = isGranted,
                        isPermanentlyDeclined = !isGranted && !(context as ComponentActivity)
                            .shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)
                    )
            )
        }
    )

    LaunchedEffect(recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        if (state.displayState != DisplayState.DISPLAYING_RESULT) {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        } else {
                            onResult(state.spokenText)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(75.dp),
                    shape = CircleShape
                ) {
                    AnimatedContent(targetState = state.displayState, label = "") {
                        when (it) {
                            DisplayState.SPEAKING -> {
                                Icon(
                                    imageVector = Icons.Rounded.Stop,
                                    contentDescription = stringResource(R.string.stop_recording),
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            DisplayState.DISPLAYING_RESULT -> {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = stringResource(R.string.apply),
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            else -> {
                                Icon(
                                    painter = painterResource(R.drawable.mic),
                                    contentDescription = stringResource(R.string.record_audio),
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }
                if (state.displayState == DisplayState.DISPLAYING_RESULT) {
                    IconButton(
                        onClick = {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = stringResource(R.string.record_again),
                            modifier = Modifier.size(50.dp),
                            tint = LightBlue
                        )
                    }
                }
            }
        },
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            Box(modifier = Modifier.fillMaxWidth()) {

                IconButton(
                    onClick = {
                        onEvent(VoiceToTextEvent.Close)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.close),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    )
                }

                if (state.displayState == DisplayState.SPEAKING) {
                    Text(
                        text = stringResource(R.string.listening),
                        color = LightBlue,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {


                AnimatedContent(targetState = state.displayState, label = "") {
                    when (it) {
                        DisplayState.WAITING_TO_TALK -> {
                            Text(
                                text = stringResource(R.string.start_talking),
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center
                            )
                        }

                        DisplayState.SPEAKING -> {
                            VoiceRecorderDisplay(
                                powerRatios = state.powerRatios,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                        }

                        DisplayState.DISPLAYING_RESULT -> {
                            Text(
                                text = state.spokenText,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center
                            )
                        }

                        DisplayState.ERROR -> {
                            Text(
                                text = state.recordError ?: "Unknown Error",
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        null -> {

//                            val a =

                        }
                    }
                }
            }
        }
    }
}