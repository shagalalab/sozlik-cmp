package com.shagalalab.sozlik

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume
import com.shagalalab.sozlik.shared.App
import com.shagalalab.sozlik.shared.domain.component.root.RootComponentImpl
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val lifecycle = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycle = lifecycle)
    val root = RootComponentImpl(context)

    return ComposeUIViewController {
        App(root)

        DisposableEffect(true) {
            lifecycle.resume()
            onDispose { lifecycle.destroy() }
        }
    }
}
