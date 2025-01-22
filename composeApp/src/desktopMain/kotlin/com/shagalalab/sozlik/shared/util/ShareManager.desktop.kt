package com.shagalalab.sozlik.shared.util

class DesktopShareManager : ShareManager {
    override fun shareApp() {
        // No-op, not used in desktop
    }

    override fun shareTranslation(wordTranslation: String) {
        // No-op, not used in desktop
    }
}

actual val isSettingsShareEnabled: Boolean = false
