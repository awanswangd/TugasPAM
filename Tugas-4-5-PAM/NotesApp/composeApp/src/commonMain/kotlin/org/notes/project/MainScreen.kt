package org.notes.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import org.notes.project.components.BottomNavBar
import org.notes.project.navigation.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState   = rememberDrawerState(DrawerValue.Closed)
    val scope         = rememberCoroutineScope()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomNavRoutes = BottomNavItem.items.map { it.route }
    val showBottomNav = currentRoute in bottomNavRoutes

    // BONUS: Navigation Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Notes App", style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    icon  = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profil") },
                    selected = currentRoute == Screen.Profile.route,
                    onClick = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(8.dp, 4.dp)
                )
                NavigationDrawerItem(
                    icon  = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Pengaturan") },
                    selected = currentRoute == Screen.Settings.route,
                    onClick = {
                        navController.navigate(Screen.Settings.route)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(8.dp, 4.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (showBottomNav) {
                    TopAppBar(
                        title = { Text("Notes App", fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, "Menu")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer)
                    )
                }
            },
            bottomBar = {
                if (showBottomNav) BottomNavBar(navController)
            }
        ) { padding ->
            AppNavigation(navController)
        }
    }
}