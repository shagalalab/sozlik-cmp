package com.shagalalab.sozlik.shared.domain.repository

interface SettingsRepository {
    fun getLayoutOptions(): List<String>
    fun getSelectedLayoutOption(): String
    fun getSelectedLocale(): String
    fun updateSelectedLayoutOption(layout: String): String
}
