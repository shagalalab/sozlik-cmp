package com.shagalalab.sozlik.shared.util

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow

class AppleShareManager : ShareManager {
    private val shareAppMessage: String = "Men Sózlik baǵdarlamasınan paydalanaman, siz hám onı mına jerden ornatıp alsańız boladı: https://bit.ly/m/sozlik"

    override fun shareApp() {
        val activityController = UIActivityViewController(activityItems = listOf(shareAppMessage), applicationActivities = null)
        val window = UIApplication.sharedApplication.windows().first() as UIWindow?
        window?.rootViewController?.presentViewController(
            activityController as UIViewController,
            animated = true,
            completion = null,
        )
    }
}

actual val isSettingsShareEnabled: Boolean = true
