package com.codeancy.translator.android.translate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codeancy.translator.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    modifier: Modifier = Modifier,
    language:UiLanguage
) {

    Image(
        painter = painterResource(language.drawableRes),
        contentDescription = "Language Icon",
        modifier = modifier.size(25.dp)
    )

}