package live.anonymespy.madassistant

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import live.anonymespy.madassistant.navigation.BottomNav
import live.anonymespy.madassistant.navigation.TopNav

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopNav(currentRoute = currentRoute)
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(Routes.HOME) { /* HomeScreen() */ }
                composable(Routes.CONTACTS) { /* ContactsScreen() */ }
                composable(Routes.FIRST_AID) { /* FirstAidScreen() */ }
                composable(Routes.SETTINGS) { /* SettingsScreen() */ }
            }
        }
        ,
        bottomBar = {
            BottomNav(
                currentRoute = currentRoute,
                navController = navController
            )
        }
    )
}
