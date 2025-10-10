package live.anonymespy.madassistant.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val bgIcons: Color = Color.Unspecified,
    val bgButton: Color = Color.Unspecified,
    val lgBg: Color = Color.Unspecified,
    val heroGradient: List<Color> = listOf(Color.Unspecified, Color.Unspecified, Color.Unspecified),
    val statsCardBg: Color = Color.Unspecified,
    val statsIconBg: Color = Color.Unspecified,
    val glassBorder: Color = Color.Unspecified,
    val glassBackground: Color = Color.Unspecified,
    val cardOverlay: Color = Color.Unspecified
)

val ColorScheme.statsCardBg: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.statsCardBg

val ColorScheme.statsIconBg: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.statsIconBg


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
    get() = LocalCustomColors.current.lgBg

val ColorScheme.heroGradient: List<Color>
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.heroGradient


val ColorScheme.glassBorder: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.glassBorder

val ColorScheme.glassBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.glassBackground

val ColorScheme.cardOverlay: Color
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current.cardOverlay