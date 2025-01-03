package com.shagalalab.sozlik.shared.domain.component.settings.layout

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsItemComponent

interface SettingsLayoutComponent: SettingsItemComponent {
    val layoutOptions: List<String>
    val defaultOption: String
    fun onDismissClicked()
    fun onOptionSelected(option: String)
}

class SettingsLayoutComponentImpl(
    private val componentContext: ComponentContext,
    override val layoutOptions: List<String>,
    override val defaultOption: String,
    private val onOptionChanged: (String) -> Unit,
    private val onDismissed: () -> Unit,
) : SettingsLayoutComponent, ComponentContext by componentContext {

    override fun onDismissClicked() {
        onDismissed()
    }

    override fun onOptionSelected(option: String) {
        onOptionChanged(option)
    }
}
