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
import kotlin.text.compareTo

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
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColors = if (darkTheme) {
        CustomColors(bgIcons = ServiceBlue3, bgButton = ServiceBlue4, lgBg = ServiceOrange1)
    } else {
        CustomColors(bgIcons = ServiceBlue4, bgButton = ServiceBlue3, lgBg = ServiceOrange2)
    }

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

