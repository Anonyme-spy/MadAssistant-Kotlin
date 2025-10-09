package live.anonymespy.madassistant

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import live.anonymespy.madassistant.navigation.BottomNav
import live.anonymespy.madassistant.navigation.TopNav
import live.anonymespy.madassistant.screens.SettingsScreen
import live.anonymespy.madassistant.screens.stack.TermsScreen
import live.anonymespy.madassistant.ui.theme.ThemeMode

@Composable
fun App(modifier: Modifier = Modifier, selectededTheme: ThemeMode, onThemeChange: (ThemeMode) -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(Routes.HOME, Routes.CONTACTS, Routes.FIRST_AID, Routes.SETTINGS)

    Scaffold(
        topBar = {
            if (currentRoute != Routes.TERMS) {
                TopNav(currentRoute = currentRoute)
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = modifier.padding(if (currentRoute == Routes.TERMS) PaddingValues(0.dp) else innerPadding)
            ) {
                composable(Routes.HOME) { /* HomeScreen() */ }
                composable(Routes.CONTACTS) { /* ContactsScreen() */ }
                composable(Routes.FIRST_AID) { /* FirstAidScreen() */ }
                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        selectedTheme = selectededTheme,
                        onThemeChange = onThemeChange,
                        onNavigateToTerms = { navController.navigate(Routes.TERMS) }
                    )
                }
                composable(Routes.TERMS) {
                    TermsScreen(onNavigateBack = { navController.popBackStack() })
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNav(
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        }
    )
}
