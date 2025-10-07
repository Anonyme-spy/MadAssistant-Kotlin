package live.anonymespy.madassistant.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.ui.theme.DarkTint
import live.anonymespy.madassistant.ui.theme.LocalCustomColors
import live.anonymespy.madassistant.ui.theme.MadAssistantTheme
import live.anonymespy.madassistant.ui.theme.ServiceBlue1
import live.anonymespy.madassistant.ui.theme.ServiceBlue2

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column {
        InfoCard()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MadAssistantTheme {
        InfoCard()
    }
}




@Composable
private fun InfoCard(modifier: Modifier = Modifier) {
    Card(
        modifier
            .padding(24.dp)
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
                        width = 1.dp,
                        color = LocalCustomColors.current.bgIcons
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
        verticalAlignment = Alignment.CenterVertically,
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