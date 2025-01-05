package com.shagalalab.sozlik.shared.data.locale

import platform.Foundation.NSUserDefaults

actual fun updateLocale(locale: String) {
    NSUserDefaults.standardUserDefaults().setObject(listOf(locale), "AppleLanguages")
}
