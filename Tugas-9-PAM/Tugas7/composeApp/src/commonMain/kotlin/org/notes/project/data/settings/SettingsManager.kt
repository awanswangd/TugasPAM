package org.notes.project.data.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeConfig {
    SYSTEM, LIGHT, DARK
}

enum class SortConfig {
    CREATED_DESC, CREATED_ASC, TITLE_ASC, TITLE_DESC
}

class SettingsManager(private val settings: Settings) {

    private val _theme = MutableStateFlow(getTheme())
    val theme = _theme.asStateFlow()

    private val _sort = MutableStateFlow(getSort())
    val sort = _sort.asStateFlow()

    fun setTheme(theme: ThemeConfig) {
        settings["theme"] = theme.name
        _theme.value = theme
    }

    private fun getTheme(): ThemeConfig {
        val name = settings.get<String>("theme") ?: ThemeConfig.SYSTEM.name
        return try { ThemeConfig.valueOf(name) } catch (e: Exception) { ThemeConfig.SYSTEM }
    }

    fun setSort(sort: SortConfig) {
        settings["sort"] = sort.name
        _sort.value = sort
    }

    private fun getSort(): SortConfig {
        val name = settings.get<String>("sort") ?: SortConfig.CREATED_DESC.name
        return try { SortConfig.valueOf(name) } catch (e: Exception) { SortConfig.CREATED_DESC }
    }
}
