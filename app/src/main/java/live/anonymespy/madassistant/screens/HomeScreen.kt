package live.anonymespy.madassistant.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.constant.EmergencyCall
import live.anonymespy.madassistant.data.ServiceItem
import live.anonymespy.madassistant.ui.theme.*
import kotlin.div
import kotlin.times

// ==================== MAIN SCREEN ====================

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            HeroSection()
            Spacer(Modifier.height(32.dp))

            StatsSection()
            Spacer(Modifier.height(32.dp))

            ServicesSection()
            Spacer(Modifier.height(32.dp))

            SOSSection()
        }
    }
}

// ==================== AMBIENT BACKGROUND ====================

@Composable
private fun AmbientBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient")

    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset1"
    )

    val offset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -80f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset2"
    )

    Box(Modifier.fillMaxSize()) {
        // Orb 1
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 200.dp, y = offset1.dp)
                .align(Alignment.TopEnd)
                .blur(80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Orb 2
        Box(
            modifier = Modifier
                .size(250.dp)
                .offset(x = (-100).dp, y = offset2.dp)
                .align(Alignment.BottomStart)
                .blur(70.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ServicePurple1.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}

// ==================== HERO SECTION ====================

@Composable
private fun HeroSection() {
    val scale = remember { Animatable(0.92f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .scale(scale.value)
    ) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = MaterialTheme.colorScheme.heroGradient,
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 1000f)
                        )
                    )
            )

            FloatingParticles()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HeroIcon()
                HeroContent()
                Hero247Badge()
            }
        }
    }
}

@Composable
private fun HeroIcon() {
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.15f)
                    )
                )
            )
            .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.shield_half_full),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(42.dp)
        )
    }
}

@Composable
private fun HeroContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.home_heroTitle),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.home_heroSubtitle),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.92f),
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun Hero247Badge() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White.copy(alpha = 0.25f))
            .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(28.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.clock_outline),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.home_hero_availability_247),
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.home_hero_availability_text),
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun FloatingParticles() {
    val particles = remember {
        listOf(
            Triple(-10.dp, 60.dp, 20.dp),
            Triple(40.dp, 120.dp, 26.dp),
            Triple(90.dp, 180.dp, 22.dp),
            Triple(140.dp, 240.dp, 28.dp),
            Triple(190.dp, 300.dp, 24.dp)
        )
    }

    particles.forEachIndexed { index, (x, y, size) ->
        val infiniteTransition = rememberInfiniteTransition(label = "particle$index")
        val offsetY by infiniteTransition.animateFloat(
            initialValue = -15f,
            targetValue = 15f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000 + index * 400, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particleY$index"
        )

        Box(
            modifier = Modifier
                .offset(x = x, y = y + offsetY.dp)
                .size(size)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
        )
    }
}

// ==================== STATS SECTION ====================

@Composable
private fun StatsSection() {
    Column {
        SectionHeader(title = stringResource(R.string.home_section_stats))
        Spacer(Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                StatCard(
                    icon = R.drawable.account_badge,
                    title = stringResource(R.string.home_stats_responders),
                    value = stringResource(R.string.home_stats_responders_count),
                    color = ServiceBlue2
                )
            }
            item {
                StatCard(
                    icon = R.drawable.clock_outline,
                    title = stringResource(R.string.home_stats_response),
                    value = stringResource(R.string.home_stats_response_time),
                    color = ServiceOrange2
                )
            }
            item {
                StatCard(
                    icon = R.drawable.shield_check,
                    title = stringResource(R.string.home_stats_coverage),
                    value = stringResource(R.string.home_stats_coverage_percent),
                    color = ServicePurple2
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: Int,
    title: String,
    value: String,
    color: Color
) {
    val scale = remember { Animatable(0.85f) }

    LaunchedEffect(Unit) {
        delay(100)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    }

    GlassCard(
        modifier = Modifier
            .width(180.dp)
            .height(140.dp)
            .scale(scale.value),
        borderColor = color.copy(alpha = 0.3f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.cardOverlay,
                            MaterialTheme.colorScheme.cardOverlay.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f))
                    .border(1.dp, color.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = value,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    lineHeight = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}


// ==================== SERVICES SECTION ====================

@Composable
private fun ServicesSection() {
    val services = remember { getServiceData() }
    val rows = (services.size + 1) / 2

    Column {
        SectionHeader(title = stringResource(R.string.home_emergencyServices))
        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height((rows * 185).dp),
            userScrollEnabled = false
        ) {
            items(services, key = { it.id }) { service ->
                ServiceCard(service = service)
            }
        }
    }
}

@Composable
private fun ServiceCard(service: ServiceItem) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "serviceScale"
    )

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isPressed = !isPressed
            },
        borderColor = service.color1.copy(alpha = 0.4f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            service.color1,
                            service.color2
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(500f, 500f)
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.08f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.White.copy(alpha = 0.18f)
                            )
                        )
                    )
                    .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(service.iconResId),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column {
                Text(
                    text = stringResource(service.titleResId),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = stringResource(service.descriptionResId),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.93f),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// ==================== SOS SECTION ====================

@Composable
private fun SOSSection() {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "sos")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sosScale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sosGlow"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sosRotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        SectionHeader(title = stringResource(R.string.home_section_sos))
        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Outer rotating ring
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(pulseScale)
                    .drawBehind {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                0f to SOSRed1.copy(alpha = glowAlpha * 0.4f),
                                0.5f to SOSRed2.copy(alpha = glowAlpha * 0.6f),
                                1f to SOSRed1.copy(alpha = glowAlpha * 0.4f)
                            ),
                            radius = size.minDimension / 2,
                            center = center
                        )
                    }
                    .blur(50.dp)
            )

            // Main SOS button
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                SOSRed1,
                                SOSRed2,
                                SOSRed3
                            )
                        )
                    )
                    .border(
                        width = 4.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.6f),
                                Color.White.copy(alpha = 0.3f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        EmergencyCall.dialSOS(context)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.alert_circle),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.home_sos_button),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 4.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.home_sos_button_subtitle),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.95f)
                    )
                }
            }

            // Pulse rings
            repeat(3) { index ->
                val delay = index * 400
                val ringScale by infiniteTransition.animateFloat(
                    initialValue = 0.8f,
                    targetValue = 1.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing, delayMillis = delay),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "ring$index"
                )

                val ringAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.6f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing, delayMillis = delay),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "ringAlpha$index"
                )

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .scale(ringScale)
                        .alpha(ringAlpha)
                        .border(
                            width = 2.dp,
                            color = SOSRed1,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Info card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = SOSRed1.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(SOSRed1.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = SOSRed1,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.home_sos_description),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }
    }
}


// ==================== REUSABLE COMPONENTS ====================

@Composable
private fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.glassBorder,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.glassBackground.copy(alpha = 0.4f))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        content()
    }
}

// ==================== DATA ====================

private fun getServiceData(): List<ServiceItem> {
    return listOf(
        ServiceItem(
            id = "health",
            titleResId = R.string.home_services_health_title,
            iconResId = R.drawable.plus_circle,
            descriptionResId = R.string.home_services_health_description,
            color1 = ServiceRed1,
            color2 = ServiceRed2,
            tabIndex = 1
        ),
        ServiceItem(
            id = "security",
            titleResId = R.string.home_services_security_title,
            iconResId = R.drawable.shield_half_full,
            descriptionResId = R.string.home_services_security_description,
            color1 = ServiceBlue1,
            color2 = ServiceBlue2,
            tabIndex = 2
        ),
        ServiceItem(
            id = "fire",
            titleResId = R.string.home_services_fire_title,
            iconResId = R.drawable.fire_alert,
            descriptionResId = R.string.home_services_fire_description,
            color1 = ServiceOrange1,
            color2 = ServiceOrange2,
            tabIndex = 3
        ),
        ServiceItem(
            id = "other",
            titleResId = R.string.home_services_other_title,
            iconResId = R.drawable.dots_vertical_circle_outline,
            descriptionResId = R.string.home_services_other_description,
            color1 = ServicePurple1,
            color2 = ServicePurple2,
            tabIndex = 0
        )
    )
}
