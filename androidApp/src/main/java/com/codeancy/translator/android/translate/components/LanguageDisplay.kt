package com.codeancy.translator.android.translate.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeancy.translator.android.core.theme.LightBlue
import com.codeancy.translator.core.presentation.UiLanguage

@Composable
fun LanguageDisplay(
    modifier: Modifier = Modifier,
    language: UiLanguage
) {


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        SmallLanguageIcon(language = language)
        Spacer(Modifier.width(8.dp))

        Text(
            text = language.language.langName,
            color = LightBlue
        )

    }

}