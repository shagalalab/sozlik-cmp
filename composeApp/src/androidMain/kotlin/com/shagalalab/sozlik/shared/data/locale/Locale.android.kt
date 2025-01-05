package com.shagalalab.sozlik.shared.data.locale

import java.util.Locale

actual fun updateLocale(locale: String) {
    Locale.setDefault(Locale(locale))
}
