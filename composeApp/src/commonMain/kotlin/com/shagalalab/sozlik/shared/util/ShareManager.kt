package com.shagalalab.sozlik.shared.util

interface ShareManager {
    fun shareApp()
    fun shareTranslation(wordTranslation: String)
}

expect val isSettingsShareEnabled: Boolean
