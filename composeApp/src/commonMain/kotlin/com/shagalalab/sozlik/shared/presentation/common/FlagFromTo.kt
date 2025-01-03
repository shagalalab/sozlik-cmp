package com.shagalalab.sozlik.shared.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryType
import org.jetbrains.compose.resources.painterResource
import sozlik_cmp.composeapp.generated.resources.Res
import sozlik_cmp.composeapp.generated.resources.flag_karakalpakstan
import sozlik_cmp.composeapp.generated.resources.flag_russia
import sozlik_cmp.composeapp.generated.resources.flag_uk

@Composable
fun FlagFromTo(type: DictionaryType, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Image(
            painter = painterResource(if (type == DictionaryType.RU_QQ) Res.drawable.flag_russia else Res.drawable.flag_karakalpakstan),
            contentDescription = null,
            modifier = Modifier.width(40.dp).aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )
        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.padding(horizontal = 4.dp))
        Image(
            painter = painterResource(if (type == DictionaryType.RU_QQ) Res.drawable.flag_karakalpakstan else Res.drawable.flag_uk),
            contentDescription = null,
            modifier = Modifier.width(40.dp).aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )
    }
}
