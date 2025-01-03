package com.shagalalab.sozlik.shared.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryBase

@Composable
fun WordList(words: List<DictionaryBase>, modifier: Modifier, itemClick: (Long) -> Unit) {
    if (words.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(words) { word ->
                WordItem(word, itemClick)
            }
        }
    }
}

@Composable
private fun WordItem(word: DictionaryBase, itemClick: (Long) -> Unit) {
    Column(modifier = Modifier.clickable { itemClick(word.id) }) {
        Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(word.word, modifier = Modifier.padding(vertical = 12.dp).weight(1f))
            FlagFromTo(word.type)
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, modifier = Modifier.padding(start = 16.dp))
        }
        HorizontalDivider()
    }
}
