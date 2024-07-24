package com.codeancy.translator.android.translate.components

import android.widget.Space
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.codeancy.translator.android.R
import com.codeancy.translator.android.core.theme.LightBlue
import com.codeancy.translator.core.presentation.UiLanguage

@Composable
fun TranslateTextField(
    modifier: Modifier = Modifier,
    fromText: String,
    toText: String?,
    isTranslating: Boolean,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    onTextFieldClick: () -> Unit
) {

    Box(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp),
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable { onTextFieldClick() }
            .padding(16.dp)
    ) {

        AnimatedContent(
            targetState = toText,
            label = "Label"
        ) { toText ->

            if (toText == null || isTranslating) {
                IdleTranslateTextField(
                    fromText = fromText,
                    isTranslating = isTranslating,
                    onTextChange = onTextChange,
                    onTranslateClick = onTranslateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                )
            } else {
                TranslatedTextField(
                    fromText = fromText,
                    toText = toText,
                    fromLanguage = fromLanguage,
                    toLanguage = toLanguage,
                    onCopyClick = onCopyClick,
                    onSpeakerClick = onSpeakerClick,
                    onCloseClick = onCloseClick,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }

    }

}


@Composable
private fun TranslatedTextField(
    modifier: Modifier = Modifier,
    fromText: String,
    toText: String,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onCopyClick: (String) -> Unit,
    onSpeakerClick: () -> Unit,
    onCloseClick: () -> Unit,
) {


    Column(
        modifier = modifier
    ) {

        LanguageDisplay(language = fromLanguage)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = fromText,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {

            IconButton(
                onClick = {
                    onCopyClick(fromText)
                }
            ) {

                Icon(
                    painter = painterResource(R.drawable.copy),
                    contentDescription = "Copy",
                    tint = LightBlue
                )

            }

            IconButton(
                onClick = {
                    onCloseClick()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Copy",
                    tint = LightBlue
                )
            }

        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        LanguageDisplay(language = toLanguage)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = toText,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {

            IconButton(
                onClick = {
                    onCopyClick(toText)
                }
            ) {

                Icon(
                    painter = painterResource(R.drawable.copy),
                    contentDescription = "Copy",
                    tint = LightBlue
                )

            }

            IconButton(
                onClick = {
                    onSpeakerClick()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.speaker),
                    contentDescription = "Speaker",
                    tint = LightBlue
                )
            }

        }
    }


}

@Composable
private fun IdleTranslateTextField(
    modifier: Modifier = Modifier,
    isTranslating: Boolean,
    onTextChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    fromText: String
) {


    var isFocused: Boolean by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {

        BasicTextField(
            value = fromText,
            onValueChange = onTextChange,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { isFocused = it.isFocused },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        if (fromText.isEmpty() && !isFocused) {

            Text(
                text = stringResource(id = R.string.enter_a_text_to_translate),
                color = LightBlue
            )
        }

        ProgressButton(
            text = stringResource(R.string.translate),
            isLoading = isTranslating,
            onClick = onTranslateClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}