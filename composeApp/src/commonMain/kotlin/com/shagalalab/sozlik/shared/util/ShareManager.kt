package com.shagalalab.sozlik.shared.util

interface ShareManager {
    fun shareApp()
}

expect val isSettingsShareEnabled: Boolean
