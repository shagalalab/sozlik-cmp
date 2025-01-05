package com.shagalalab.sozlik.shared.domain.component.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.shagalalab.sozlik.shared.data.locale.updateLocale
import com.shagalalab.sozlik.shared.domain.component.settings.about.SettingsAboutComponentImpl
import com.shagalalab.sozlik.shared.domain.component.settings.layout.SettingsLayoutComponentImpl
import com.shagalalab.sozlik.shared.domain.repository.SettingsRepository
import com.shagalalab.sozlik.shared.util.ShareManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SettingsComponent {
    val defaultLayout: String
    val dialogSlot: Value<ChildSlot<*, SettingsItemComponent>>

    fun onClickLayout()
    fun onClickAbout()
    fun onClickShare()
}

class SettingsComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, SettingsComponent, KoinComponent {
    private val settingsRepository: SettingsRepository by inject()
    private val shareManager: ShareManager by inject()
    private val dialogNavigation = SlotNavigation<SettingsDialogConfig>()

    override val defaultLayout by lazy { settingsRepository.getSelectedLayoutOption() }
    override val dialogSlot: Value<ChildSlot<*, SettingsItemComponent>> =
        childSlot(
            source = dialogNavigation,
            serializer = null,
            // persistent = false, // Disable navigation state saving, if needed
            handleBackButton = true, // Close the dialog on back button press
        ) { config, childComponentContext ->
            return@childSlot when (config.type) {
                SettingsDialogType.LAYOUT -> {
                    SettingsLayoutComponentImpl(
                        componentContext = childComponentContext,
                        layoutOptions = settingsRepository.getLayoutOptions(),
                        defaultOption = settingsRepository.getSelectedLayoutOption(),
                        onOptionChanged = { updatedOption ->
                            val previousOption = settingsRepository.getSelectedLayoutOption()
                            if (updatedOption != previousOption) {
                                val locale = settingsRepository.updateSelectedLayoutOption(updatedOption)
                                updateLocale(locale)
                            }
                        },
                        onDismissed = dialogNavigation::dismiss,
                    )
                }
                SettingsDialogType.ABOUT -> {
                    SettingsAboutComponentImpl(
                        componentContext = childComponentContext,
                        onDismissed = dialogNavigation::dismiss,
                    )
                }
            }
        }

    override fun onClickLayout() {
        dialogNavigation.activate(
            SettingsDialogConfig(
                type = SettingsDialogType.LAYOUT,
                data = SettingsDialogData.SettingsLayoutData(
                    options = settingsRepository.getLayoutOptions(),
                    defaultOption = settingsRepository.getSelectedLayoutOption()
                )
            )
        )
    }

    override fun onClickAbout() {
        dialogNavigation.activate(
            SettingsDialogConfig(type = SettingsDialogType.ABOUT)
        )
    }

    override fun onClickShare() {
        shareManager.shareApp()
    }
}
