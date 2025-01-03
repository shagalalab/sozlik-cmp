package com.shagalalab.sozlik.shared.data.repository

import com.russhwolf.settings.Settings
import com.shagalalab.sozlik.shared.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settings: Settings) : SettingsRepository {
    companion object {
        private const val LAYOUT_LATIN = "Latın"
        private const val LAYOUT_CYRILLIC = "Кириллше"
        private const val LOCALE_LATIN = "en"
        private const val LOCALE_CYRILLIC = "ru"
        private const val KEY_SELECTED_LAYOUT = "key_selected_layout"
        private const val KEY_SELECTED_LOCALE = "key_selected_locale"
    }

    override fun getLayoutOptions(): List<String> {
        return listOf(LAYOUT_LATIN, LAYOUT_CYRILLIC)
    }

    override fun getSelectedLayoutOption(): String {
        return settings.getString(KEY_SELECTED_LAYOUT, LAYOUT_LATIN)
    }

    override fun getSelectedLocale(): String {
        return settings.getString(KEY_SELECTED_LOCALE, LOCALE_LATIN)
    }

    override fun updateSelectedLayoutOption(layout: String): String {
        val locale = if (layout == LAYOUT_CYRILLIC) LOCALE_CYRILLIC else LOCALE_LATIN
        settings.putString(KEY_SELECTED_LAYOUT, layout)
        settings.putString(KEY_SELECTED_LOCALE, locale)
        return locale
    }
}
