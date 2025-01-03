package com.shagalalab.sozlik.shared.util

import android.content.Context
import android.content.Intent

internal class AndroidShareManager(private val context: Context) : ShareManager {
    private val shareAppMessage: String = "Men Sózlik baǵdarlamasınan paydalanaman, siz hám onı mına jerden ornatıp alsańız boladı: https://bit.ly/m/sozlik"

    override fun shareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareAppMessage)
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }
}

actual val isSettingsShareEnabled: Boolean = true
