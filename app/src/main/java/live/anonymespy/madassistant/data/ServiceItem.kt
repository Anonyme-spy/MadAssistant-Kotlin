package live.anonymespy.madassistant.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class ServiceItem(
    val id: String,
    val titleResId: Int,
    @DrawableRes val iconResId: Int,
    val descriptionResId: Int,
    val color1: Color,
    val color2: Color,
    val tabIndex: Int
)
