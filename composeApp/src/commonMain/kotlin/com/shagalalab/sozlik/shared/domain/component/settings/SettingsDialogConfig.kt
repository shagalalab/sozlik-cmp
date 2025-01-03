package com.shagalalab.sozlik.shared.domain.component.settings

import kotlinx.serialization.Serializable

@Serializable
data class SettingsDialogConfig(
    val type: SettingsDialogType,
    val data: SettingsDialogData? = null
)

@Serializable
enum class SettingsDialogType {
    LAYOUT,
    ABOUT,
}

@Serializable
sealed interface SettingsDialogData {
    data class SettingsLayoutData(val options: List<String>, val defaultOption: String) : SettingsDialogData
}
