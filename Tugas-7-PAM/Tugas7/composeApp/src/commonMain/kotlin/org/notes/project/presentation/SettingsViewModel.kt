package org.notes.project.presentation

import androidx.lifecycle.ViewModel
import org.notes.project.data.settings.SettingsManager
import org.notes.project.data.settings.SortConfig
import org.notes.project.data.settings.ThemeConfig

class SettingsViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {

    val theme = settingsManager.theme
    val sort = settingsManager.sort

    fun setTheme(theme: ThemeConfig) {
        settingsManager.setTheme(theme)
    }

    fun setSort(sort: SortConfig) {
        settingsManager.setSort(sort)
    }
}
