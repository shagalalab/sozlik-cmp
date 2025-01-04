package com.shagalalab.sozlik.shared.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.resources.Res
import com.shagalalab.sozlik.resources.app_name
import com.shagalalab.sozlik.resources.search
import com.shagalalab.sozlik.resources.search_placeholder
import com.shagalalab.sozlik.resources.x
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent
import com.shagalalab.sozlik.shared.presentation.common.WordList
import com.shagalalab.sozlik.shared.util.parseHtml
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(component: SearchComponent, modifier: Modifier = Modifier) {
    val query by component.query.collectAsState()
    val state by component.state.collectAsState()

    Column {
        TopAppBar(title = { Text(stringResource(Res.string.app_name)) })
        OutlinedTextField(
            value = query,
            onValueChange = {
                component.onQueryChanged(it)
            },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            placeholder = { Text(stringResource(Res.string.search_placeholder)) },
            leadingIcon = { Icon(painter = painterResource(Res.drawable.search), contentDescription = null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { component.onQueryChanged("") }) {
                        Icon(painter = painterResource(Res.drawable.x), null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        )
        state.message?.let {
            Text(
                it.parseHtml(),
                modifier = Modifier.background(Color.LightGray.copy(alpha = 0.7f)).padding(16.dp).fillMaxWidth()
            )
        }
        WordList(state.suggestions, modifier, component::onSearchItemClicked)
    }
}
