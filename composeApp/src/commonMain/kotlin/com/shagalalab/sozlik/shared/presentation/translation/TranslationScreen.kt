package com.shagalalab.sozlik.shared.presentation.translation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.resources.Raleway_Bold
import com.shagalalab.sozlik.resources.Res
import com.shagalalab.sozlik.resources.arrow_left
import com.shagalalab.sozlik.resources.back
import com.shagalalab.sozlik.resources.book
import com.shagalalab.sozlik.resources.heart
import com.shagalalab.sozlik.resources.heart_border
import com.shagalalab.sozlik.resources.share
import com.shagalalab.sozlik.resources.share_2
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.presentation.common.FlagFromTo
import com.shagalalab.sozlik.shared.util.parseHtml
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalAdaptiveApi::class, ExperimentalCupertinoApi::class)
@Composable
fun TranslationScreen(component: TranslationComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Column {
        AdaptiveTopAppBar(
            navigationIcon = {
                AdaptiveWidget(
                    cupertino = {
                        CupertinoNavigateBackButton(
                            onClick = component::onBackButtonPress,
                        ) {
                            CupertinoText(stringResource(Res.string.back))
                        }
                    },
                    material = {
                        IconButton(onClick = component::onBackButtonPress) {
                            Icon(painter = painterResource(Res.drawable.arrow_left), contentDescription = null)
                        }
                    }
                )
            },
            title = {},
            actions = {
                IconButton(onClick = component::onFavoriteClick) {
                    Icon(painter = painterResource(if (state.isFavorite) Res.drawable.heart else Res.drawable.heart_border), contentDescription = null)
                }
                AdaptiveWidget(
                    cupertino = {
                        IconButton(onClick = component::onShareClick) {
                            Icon(painter = painterResource(Res.drawable.share), contentDescription = null)
                        }
                    },
                    material = {
                        IconButton(onClick = component::onShareClick) {
                            Icon(painter = painterResource(Res.drawable.share_2), contentDescription = null)
                        }
                    }
                )
            }
        )
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.book),
                contentDescription = null,
                modifier = Modifier.width(200.dp).align(Alignment.Center).alpha(0.1f),
                colorFilter = ColorFilter.tint(Color.LightGray)
            )
            Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
                state.translation?.let {
                    Text(
                        text = it.rawWord ?: it.word,
                        style = MaterialTheme.typography.displayMedium,
                        fontFamily = FontFamily(Font(Res.font.Raleway_Bold))
                    )
                    FlagFromTo(it.type, Modifier.padding(vertical = 24.dp))
                    Text(it.translation.parseHtml())
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        component.getTranslation()
    }
}
