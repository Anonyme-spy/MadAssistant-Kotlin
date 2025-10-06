package live.anonymespy.madassistant.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    val title = when (currentRoute) {
        Routes.HOME -> stringResource(R.string.tabs_home)
        Routes.CONTACTS -> stringResource(R.string.tabs_contacts)
        Routes.FIRST_AID -> stringResource(R.string.tabs_firstAid)
        Routes.SETTINGS -> stringResource(R.string.tabs_settings)
        else -> stringResource(R.string.app_name)
    }

    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        modifier = modifier
    )
}
