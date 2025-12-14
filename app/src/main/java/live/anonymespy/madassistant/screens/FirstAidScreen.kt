package live.anonymespy.madassistant.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.constant.EmergencyCall
import live.anonymespy.madassistant.data.FirstAidProcedure
import live.anonymespy.madassistant.data.FirstAidResponse
import live.anonymespy.madassistant.data.Repository

// Category colors matching the JSON
private val RespiratoryColor = Color(0xFF3498DB)
private val CardiacColor = Color(0xFFE74C3C)
private val EmergencyColor = Color(0xFFF39C12)

@Composable
fun FirstAidScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val repository = remember { Repository(context) }
    val firstAidData = remember { repository.getFirstAidProcedures() }

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("all") }

    val filteredProcedures = remember(searchText, selectedCategory, firstAidData) {
        filterProcedures(firstAidData.firstAidProcedures, selectedCategory, searchText)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Hero Section
        item {
            HeroSection()
        }

        // Search Bar
        item {
            SearchBar(
                searchText = searchText,
                onSearchChange = { searchText = it },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Category Tabs
        item {
            CategoryTabs(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                categories = firstAidData.categories,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Procedures List or No Results
        if (filteredProcedures.isEmpty()) {
            item {
                NoResultsView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        } else {
            items(filteredProcedures, key = { it.id }) { procedure ->
                EmergencyCard(
                    procedure = procedure,
                    firstAidData = firstAidData,
                    onCallEmergency = { number ->
                        EmergencyCall.dialNumber(context, number)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}


@Composable
private fun HeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        // Background Image with gradient overlay
        Image(
            painter = painterResource(id = R.drawable.first_aid_hero),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.firstaid_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.firstaid_subtitle),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun SearchBar(
    searchText: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                value = searchText,
                onValueChange = onSearchChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (searchText.isEmpty()) {
                        Text(
                            text = stringResource(R.string.firstaid_search_placeholder),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun CategoryTabs(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    categories: List<live.anonymespy.madassistant.data.FirstAidCategory>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryTab(
            title = stringResource(R.string.firstaid_category_all),
            isSelected = selectedCategory == "all",
            onClick = { onCategorySelected("all") }
        )

        CategoryTab(
            title = stringResource(R.string.firstaid_category_respiratory),
            isSelected = selectedCategory == "Respiratory",
            onClick = { onCategorySelected("Respiratory") }
        )

        CategoryTab(
            title = stringResource(R.string.firstaid_category_cardiac),
            isSelected = selectedCategory == "Cardiac",
            onClick = { onCategorySelected("Cardiac") }
        )

        CategoryTab(
            title = stringResource(R.string.firstaid_category_emergency),
            isSelected = selectedCategory == "Emergency",
            onClick = { onCategorySelected("Emergency") }
        )
    }
}

@Composable
private fun CategoryTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.Transparent,
        label = "tabBackground"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "tabText"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
private fun NoResultsView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.firstaid_no_results),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EmergencyCard(
    procedure: FirstAidProcedure,
    firstAidData: FirstAidResponse,
    onCallEmergency: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = getCategoryColor(procedure.category)
    val categoryIcon = getCategoryIcon(procedure.category)
    val categoryName = firstAidData.categories.find { it.id == procedure.category }?.name ?: procedure.category
    val emergencyNumber = getEmergencyNumber(procedure.category, firstAidData)
    val steps = formatDescription(procedure.description)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(categoryColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(categoryIcon),
                        contentDescription = null,
                        tint = categoryColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = procedure.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(categoryColor)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = categoryName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Steps
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(categoryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = step,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Emergency Button
            Button(
                onClick = { onCallEmergency(emergencyNumber) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = categoryColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${stringResource(R.string.firstaid_call_emergency)} - $emergencyNumber",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Helper functions

private fun filterProcedures(
    procedures: List<FirstAidProcedure>,
    category: String,
    searchText: String
): List<FirstAidProcedure> {
    var filtered = procedures

    if (category != "all") {
        filtered = filtered.filter { it.category == category }
    }

    if (searchText.isNotBlank()) {
        filtered = filtered.filter {
            it.title.contains(searchText, ignoreCase = true) ||
                    it.description.contains(searchText, ignoreCase = true)
        }
    }

    return filtered
}

private fun getCategoryColor(category: String): Color {
    return when (category) {
        "Respiratory" -> RespiratoryColor
        "Cardiac" -> CardiacColor
        "Emergency" -> EmergencyColor
        else -> Color.Gray
    }
}

private fun getCategoryIcon(category: String): Int {
    return when (category) {
        "Respiratory" -> R.drawable.phonendoscope_svgrepo_com
        "Cardiac" -> R.drawable.heart_pulse
        "Emergency" -> R.drawable.first_aid_kit_svgrepo_com
        else -> R.drawable.first_aid_kit_svgrepo_com
    }
}

private fun getEmergencyNumber(category: String, data: FirstAidResponse): String {
    return when (category) {
        "Cardiac" -> data.emergencyNumbers.hospitalCHU
        "Respiratory" -> data.emergencyNumbers.medicalEmergency
        else -> data.emergencyNumbers.police
    }
}

private fun formatDescription(description: String): List<String> {
    return description
        .split(Regex("\\d+\\."))
        .filter { it.isNotBlank() }
        .map { it.trim() }
}
