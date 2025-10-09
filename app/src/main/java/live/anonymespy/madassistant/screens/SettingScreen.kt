package live.anonymespy.madassistant.screens


import android.R.attr.fontWeight
import android.R.attr.onClick
import android.R.id.text1
import android.R.id.text2
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.launch
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.ui.theme.DarkTint
import live.anonymespy.madassistant.ui.theme.LocalCustomColors
import live.anonymespy.madassistant.ui.theme.MadAssistantTheme
import live.anonymespy.madassistant.ui.theme.ServiceBlue1
import live.anonymespy.madassistant.ui.theme.ServiceBlue2
import live.anonymespy.madassistant.ui.theme.ServiceOrange1
import live.anonymespy.madassistant.ui.theme.ServiceOrange2
import live.anonymespy.madassistant.ui.theme.ThemeMode
import live.anonymespy.madassistant.ui.theme.bgButton
import live.anonymespy.madassistant.ui.theme.bgIcons
import org.intellij.lang.annotations.Language
import java.util.Locale
import kotlin.compareTo

@Composable
fun SettingsScreen(
    selectedTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onNavigateToTerms: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val currentLanguage = Lingver.getInstance().getLocale().language
    val initialLocale = when (currentLanguage) {
        "fr" -> Locale.FRENCH
        else -> Locale.ENGLISH
    }
    var selectedLanguage by remember { mutableStateOf(initialLocale) }

    val scrollState = rememberScrollState()


    // Restore scroll position smoothly after layout
    LaunchedEffect(scrollState.maxValue) {
        val savedPosition = ScrollStateManager.getSavedScrollPosition()
        if (savedPosition > 0 && scrollState.maxValue > 0) {
            scrollState.animateScrollTo(savedPosition)
            ScrollStateManager.clearScrollPosition()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            stringResource(R.string.settings_title),
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        InfoCard()
        Column {
            Text(stringResource(R.string.settings_configuration).uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary
            )
            ThemeCardSwitch(selectedTheme, onThemeChange)
            LanguageCardSwitch(
                selectedLanguage = selectedLanguage,
                onLanguageChange = { newLocale ->
                    selectedLanguage = newLocale
                    ScrollStateManager.saveScrollPosition(scrollState.value)
                    Lingver.getInstance().setLocale(context, newLocale)
                    activity?.recreate()
                }
            )
        }
        Column(
            modifier.padding(top = 12.dp)
        ) {
            Text(stringResource(R.string.settings_legal).uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary
            )
            LegalCard ( onClick = onNavigateToTerms )
        }
    }
}






//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    MadAssistantTheme {
//        Column {
//            Text(stringResource(R.string.settings_title),
//                modifier = Modifier
//                    .padding(top = 24.dp)
//                    .fillMaxWidth(),
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center
//            )
//            InfoCard()
//        }
//    }
//}




@Composable
private fun InfoCard(modifier: Modifier = Modifier) {
    Card(
        modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .background(MaterialTheme.colorScheme.background)
            .shadow(shape = RoundedCornerShape(24.dp), elevation = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),  // Increased corner radius
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column (
           horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .background(
                        color = LocalCustomColors.current.bgIcons,
                        shape = CircleShape
                    )
                    .border(
                        shape = CircleShape,
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    .padding(20.dp)


            ) {
                Image(
                    painterResource(R.drawable.shield_check),
                    contentDescription = "shield",
                    modifier = Modifier
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(DarkTint)
                )
            }


            Text(text = stringResource(R.string.about_title),
                modifier = Modifier
                    .padding(top = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Text(text = stringResource(R.string.about_subtitle),
                modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 2.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary
                )

            Text(text = stringResource(R.string.about_description),
                modifier
                    .padding(18.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary

                )

            Column {
                IconText(painterResource(R.drawable.phone), stringResource(R.string.about_features_quickCall))
                IconText(painterResource(R.drawable.account_group))
                IconText(painterResource(R.drawable.translate), stringResource(R.string.about_features_multilingual))

            }

        }
    }
}


@Composable
private fun IconText(
    images: Painter = painterResource(R.drawable.heart_pulse),
    text: String = stringResource(R.string.about_features_contacts)
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(images , contentDescription = null, modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary)
            )
        Text(text, modifier = Modifier
            .padding(
                top = 5.dp,
                bottom = 3.dp,
                start = 8.dp
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}



@Composable
private fun ThemeCardSwitch(
    selectedTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconTextCard()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ThemeButton(
                    icon = painterResource(R.drawable.white_balance_sunny),
                    label = stringResource(R.string.settings_light),
                    isSelected = selectedTheme == ThemeMode.LIGHT,
                    onClick = { onThemeChange(ThemeMode.LIGHT) }
                )

                ThemeButton(
                    icon = painterResource(R.drawable.weather_night),
                    label = stringResource(R.string.settings_dark),
                    isSelected = selectedTheme == ThemeMode.DARK,
                    onClick = { onThemeChange(ThemeMode.DARK) }
                )

                ThemeButton(
                    icon = painterResource(R.drawable.auto_fix),
                    label = stringResource(R.string.settings_auto),
                    isSelected = selectedTheme == ThemeMode.SYSTEM,
                    onClick = { onThemeChange(ThemeMode.SYSTEM) }
                )
            }
        }
    }
}



@Composable
fun IconTextCard(
    image: Painter = painterResource(R.drawable.brush),
    text1: String = stringResource(R.string.settings_theme),
    text2: String = stringResource(R.string.settings_themeDescription),
    colours: Color = MaterialTheme.colorScheme.tertiary
    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Image(image, contentDescription = null, modifier = Modifier
            .size(44.dp)
            .padding(start = 12.dp),
            colorFilter = ColorFilter.tint(colours)
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = text1, modifier = Modifier.padding(2.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold)
            Text(text = text2, modifier = Modifier.padding(2.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.W300,
                style = MaterialTheme.typography.bodySmall
                )
        }

    }

}

@Composable
private fun ThemeButton(
    icon: Painter,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.bgButton
                    else MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ).border(
                    shape = CircleShape,
                    width = 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.bgButton
                    else MaterialTheme.colorScheme.onSurface
                )
        ) {
            Image(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(28.dp),
                colorFilter = ColorFilter.tint(
                    if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Text(
            text = label,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun LanguageCardSwitch(
    selectedLanguage: Locale,
    onLanguageChange: (Locale) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            IconTextCard(
                text1 = stringResource(R.string.settings_language),
                text2 = stringResource(R.string.settings_languageDescription),
                image = painterResource(R.drawable.translate_variant),
                colours = MaterialTheme.colorScheme.onTertiary
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LanguageOption(
                    label = "English",
                    flag = painterResource(R.drawable.flag_of_united_kingdom),
                    isSelected = selectedLanguage == Locale.ENGLISH,
                    onClick = { onLanguageChange(Locale.ENGLISH) }
                )

                LanguageOption(
                    label = "Français",
                    flag = painterResource(R.drawable.flag_of_france),
                    isSelected = selectedLanguage == Locale.FRENCH,
                    onClick = { onLanguageChange(Locale.FRENCH) }
                )
            }
        }
    }
}

@Composable
private fun LanguageOption(
    label: String,
    flag: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Image(
            painter = flag,
            contentDescription = label,
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .padding(2.dp)
        )

        Text(
            text = label,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            style = MaterialTheme.typography.titleMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//private fun LegalCardPreview(){
//    MadAssistantTheme {
//        LegalCard()
//    }
//}

@Composable
fun LegalCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card (
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier
                .fillMaxWidth().padding(16.dp),
            Arrangement.Center,
            Alignment.CenterVertically
        ) {
            Image(painterResource(R.drawable.file_document_multiple_outline), contentDescription = null,
                modifier.size(36.dp).padding(end = 6.dp),
                colorFilter = ColorFilter.tint(LocalCustomColors.current.lgBg)
                )
          Column(
                    modifier = Modifier.padding(start = 8.dp)
                    ) {
                Text(text = stringResource(R.string.settings_termsDescription), modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold)
                Text(text = stringResource(R.string.settings_termsDescription), modifier = Modifier.padding(2.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.W400,
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }

    }
}
