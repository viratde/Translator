package com.codeancy.translator.android.translate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeancy.translator.android.TranslatorTheme
import com.codeancy.translator.core.presentation.UiLanguage
import java.util.Locale

@Composable
fun LanguageDropDownItem(
    modifier: Modifier = Modifier,
    language: UiLanguage,
    onClick: () -> Unit
) {

    DropdownMenuItem(
        text = {
            Text(
                text = language.language.langName.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
        },
        onClick = onClick,
        leadingIcon = {
            Image(
                painter = painterResource(language.drawableRes),
                contentDescription = "${language.language.langName} Language Icon",
                modifier = Modifier.size(40.dp)
            )
        },
        modifier = modifier
    )

}

@Preview
@Composable
fun LanguageDropDownItemPreview() {
    TranslatorTheme {
        LanguageDropDownItem(language = UiLanguage.byCode("en")) {

        }
    }
}