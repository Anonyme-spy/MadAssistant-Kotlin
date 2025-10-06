package live.anonymespy.madassistant.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.Routes

@Composable
fun BottomNav(
    currentRoute: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentRoute == Routes.HOME,
            onClick = { navController.navigate(Routes.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(stringResource(R.string.tabs_home)) }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.CONTACTS,
            onClick = { navController.navigate(Routes.CONTACTS) },
            icon = { Icon(painterResource(R.drawable.phone_outgoing), contentDescription = "phone outgoing") },
            label = { Text(stringResource(R.string.tabs_contacts)) }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.FIRST_AID,
            onClick = { navController.navigate(Routes.FIRST_AID) },
            icon = { Icon(painterResource(R.drawable.heart_pulse), contentDescription = "Heatbeat") },
            label = { Text(stringResource(R.string.tabs_firstAid)) }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.SETTINGS,
            onClick = { navController.navigate(Routes.SETTINGS) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text(stringResource(R.string.tabs_settings)) }
        )
    }
}
