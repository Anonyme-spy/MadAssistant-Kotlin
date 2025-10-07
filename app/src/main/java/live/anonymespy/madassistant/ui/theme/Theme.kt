package live.anonymespy.madassistant.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkTint,
    secondary = DarkTextSecondary,
    tertiary = ServiceBlue1,
    background = DarkBackground,
    surface = DarkCardBackground,
    onPrimary = DarkTextPrimary,
    onSecondary = DarkTextSecondary,
    onTertiary = DarkTabIconDefault,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    outline = DarkDivider,

)

private val LightColorScheme = lightColorScheme(
    primary = LightTint,
    secondary = LightTextSecondary,
    tertiary = ServiceBlue2,
    background = LightBackground,
    surface = LightCardBackground,
    onPrimary = LightTextPrimary,
    onSecondary = LightTextSecondary,
    onTertiary = LightTabIconDefault,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    outline = LightDivider
)

@Composable
fun MadAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColors = if (darkTheme) {
        CustomColors(bgIcons = ServiceBlue3)
    } else {
        CustomColors(bgIcons = ServiceBlue4)
    }

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}
