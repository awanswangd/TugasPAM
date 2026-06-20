package org.notes.project.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.notes.project.data.settings.SettingsManager
import org.notes.project.data.settings.SortConfig
import org.notes.project.data.settings.ThemeConfig
import org.notes.project.platform.DeviceInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsManager: SettingsManager,
    onBackClick: () -> Unit
) {
    val theme by settingsManager.theme.collectAsState()
    val sort by settingsManager.sort.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                ThemeOption(
                    title = "System Default",
                    selected = theme == ThemeConfig.SYSTEM,
                    onClick = { settingsManager.setTheme(ThemeConfig.SYSTEM) }
                )
                ThemeOption(
                    title = "Light",
                    selected = theme == ThemeConfig.LIGHT,
                    onClick = { settingsManager.setTheme(ThemeConfig.LIGHT) }
                )
                ThemeOption(
                    title = "Dark",
                    selected = theme == ThemeConfig.DARK,
                    onClick = { settingsManager.setTheme(ThemeConfig.DARK) }
                )
            }

            HorizontalDivider()

            Column {
                Text(
                    text = "Default Sort Order",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                SortOption(
                    title = "Created (Newest first)",
                    selected = sort == SortConfig.CREATED_DESC,
                    onClick = { settingsManager.setSort(SortConfig.CREATED_DESC) }
                )
                SortOption(
                    title = "Created (Oldest first)",
                    selected = sort == SortConfig.CREATED_ASC,
                    onClick = { settingsManager.setSort(SortConfig.CREATED_ASC) }
                )
                SortOption(
                    title = "Title (A-Z)",
                    selected = sort == SortConfig.TITLE_ASC,
                    onClick = { settingsManager.setSort(SortConfig.TITLE_ASC) }
                )
                SortOption(
                    title = "Title (Z-A)",
                    selected = sort == SortConfig.TITLE_DESC,
                    onClick = { settingsManager.setSort(SortConfig.TITLE_DESC) }
                )
            }

            HorizontalDivider()

            DeviceInfoSection()
        }
    }
}

/**
 * Section yang menampilkan informasi perangkat.
 * DeviceInfo di-inject melalui Koin — bukan dibuat manual.
 */
@Composable
private fun DeviceInfoSection() {
    val deviceInfo: DeviceInfo = koinInject()

    Column {
        Text(
            text = "Device Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        DeviceInfoRow(label = "Device", value = deviceInfo.getDeviceName())
        DeviceInfoRow(label = "OS Version", value = deviceInfo.getOsVersion())
        DeviceInfoRow(label = "App Version", value = deviceInfo.getAppVersion())
    }
}

@Composable
private fun DeviceInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.PhoneAndroid,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ThemeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title)
    }
}

@Composable
private fun SortOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title)
    }
}
