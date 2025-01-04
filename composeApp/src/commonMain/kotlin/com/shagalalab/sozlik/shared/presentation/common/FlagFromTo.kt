package com.shagalalab.sozlik.shared.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.resources.Res
import com.shagalalab.sozlik.resources.arrow_right
import com.shagalalab.sozlik.resources.flag_karakalpakstan
import com.shagalalab.sozlik.resources.flag_russia
import com.shagalalab.sozlik.resources.flag_uk
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryType
import org.jetbrains.compose.resources.painterResource

@Composable
fun FlagFromTo(type: DictionaryType, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Image(
            painter = painterResource(if (type == DictionaryType.RU_QQ) Res.drawable.flag_russia else Res.drawable.flag_karakalpakstan),
            contentDescription = null,
            modifier = Modifier.width(40.dp).aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )
        Icon(painter = painterResource(Res.drawable.arrow_right), null, modifier = Modifier.padding(horizontal = 4.dp))
        Image(
            painter = painterResource(if (type == DictionaryType.RU_QQ) Res.drawable.flag_karakalpakstan else Res.drawable.flag_uk),
            contentDescription = null,
            modifier = Modifier.width(40.dp).aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )
    }
}
