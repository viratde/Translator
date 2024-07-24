package com.codeancy.translator.android.translate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.codeancy.translator.android.R

@Composable
fun SwapLanguagesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Icon(
            painter = painterResource(R.drawable.swap_languages),
            contentDescription = stringResource(R.string.swap_languages),
            tint = MaterialTheme.colorScheme.onPrimary
        )

    }

}