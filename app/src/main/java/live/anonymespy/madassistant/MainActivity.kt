package live.anonymespy.madassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import live.anonymespy.madassistant.ui.theme.MadAssistantTheme
import live.anonymespy.madassistant.ui.theme.ThemeMode

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentTheme by remember { mutableStateOf(ThemeMode.SYSTEM) }
            MadAssistantTheme(themeMode = currentTheme) {
               App(selectededTheme = currentTheme, onThemeChange = {
                   currentTheme = it
               } )
            }
        }
    }
}