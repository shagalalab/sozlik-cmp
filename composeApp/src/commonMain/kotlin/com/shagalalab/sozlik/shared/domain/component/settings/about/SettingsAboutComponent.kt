package com.shagalalab.sozlik.shared.domain.component.settings.about

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsItemComponent

interface SettingsAboutComponent: SettingsItemComponent {
    fun onDismissClicked()
}

class SettingsAboutComponentImpl(
    private val componentContext: ComponentContext,
    private val onDismissed: () -> Unit,
) : SettingsAboutComponent, ComponentContext by componentContext {
    override fun onDismissClicked() {
        onDismissed()
    }
}
