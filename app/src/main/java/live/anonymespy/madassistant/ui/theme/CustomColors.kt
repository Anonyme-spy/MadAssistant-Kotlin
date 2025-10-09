package live.anonymespy.madassistant.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val bgIcons: Color = Color.Unspecified,
    val bgButton: Color = Color.Unspecified,
    val lgBg: Color = Color.Unspecified
)

val LocalCustomColors = staticCompositionLocalOf { CustomColors() }

val ColorScheme.bgIcons: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.bgIcons
val ColorScheme.bgButton: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.bgButton

val ColorScheme.LgBg: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.bgButton


