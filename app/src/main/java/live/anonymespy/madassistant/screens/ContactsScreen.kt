package live.anonymespy.madassistant.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import live.anonymespy.madassistant.R
import live.anonymespy.madassistant.constant.EmergencyCall
import live.anonymespy.madassistant.data.EmergencyContact
import live.anonymespy.madassistant.data.Repository

// Category colors
private val HealthColor = Color(0xFFFF6B6B)
private val SecurityColor = Color(0xFF4DABF7)
private val FireColor = Color(0xFFFF922B)
private val InsuranceColor = Color(0xFF9775FA)
private val DefaultColor = Color(0xFF6C757D)

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    initialTabIndex: Int = 0
) {
    val context = LocalContext.current
    val repository = remember { Repository(context) }
    val contacts = remember { repository.getEmergencyContacts() }

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("all") }
    var selectedContact by remember { mutableStateOf<EmergencyContact?>(null) }

    // Set initial category based on tab index
    LaunchedEffect(initialTabIndex) {
        selectedCategory = when (initialTabIndex) {
            1 -> "health"
            2 -> "security"
            3 -> "fire"
            4 -> "insurance"
            else -> "all"
        }
    }

    val filteredContacts = remember(searchText, selectedCategory, contacts) {
        filterContacts(contacts, selectedCategory, searchText)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Bar
            ContactSearchBar(
                searchText = searchText,
                onSearchChange = { searchText = it },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Category Tabs
            ContactCategoryTabs(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            // Contacts List
            if (filteredContacts.isEmpty()) {
                NoContactsView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredContacts, key = { it.id }) { contact ->
                        ContactCard(
                            contact = contact,
                            onClick = { selectedContact = contact },
                            onCallClick = { phoneNumber ->
                                EmergencyCall.dialNumber(context, phoneNumber)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        // Contact Detail Modal
        if (selectedContact != null) {
            ContactDetailModal(
                contact = selectedContact!!,
                onDismiss = { selectedContact = null },
                onCall = { phoneNumber ->
                    EmergencyCall.dialNumber(context, phoneNumber)
                }
            )
        }
    }
}

@Composable
private fun ContactSearchBar(
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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
                            text = stringResource(R.string.contacts_search_placeholder),
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
private fun ContactCategoryTabs(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ContactCategoryTab(
            title = stringResource(R.string.contacts_category_all),
            isSelected = selectedCategory == "all",
            onClick = { onCategorySelected("all") }
        )
        ContactCategoryTab(
            title = stringResource(R.string.contacts_category_health),
            isSelected = selectedCategory == "health",
            onClick = { onCategorySelected("health") }
        )
        ContactCategoryTab(
            title = stringResource(R.string.contacts_category_security),
            isSelected = selectedCategory == "security",
            onClick = { onCategorySelected("security") }
        )
        ContactCategoryTab(
            title = stringResource(R.string.contacts_category_fire),
            isSelected = selectedCategory == "fire",
            onClick = { onCategorySelected("fire") }
        )
        ContactCategoryTab(
            title = stringResource(R.string.contacts_category_insurance),
            isSelected = selectedCategory == "insurance",
            onClick = { onCategorySelected("insurance") }
        )
    }
}

@Composable
private fun ContactCategoryTab(
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
private fun NoContactsView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
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
            text = stringResource(R.string.contacts_no_results),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ContactCard(
    contact: EmergencyContact,
    onClick: () -> Unit,
    onCallClick: (String) -> Unit
) {
    val categoryColor = getCategoryColor(contact.category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(categoryColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(getCategoryIcon(contact.category)),
                    contentDescription = null,
                    tint = categoryColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Category Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(categoryColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = contact.category.replaceFirstChar { it.uppercase() },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = categoryColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Call Button
            contact.tel?.let { phoneNumber ->
                IconButton(
                    onClick = { onCallClick(phoneNumber) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF51CF66))
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = stringResource(R.string.contacts_call),
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactDetailModal(
    contact: EmergencyContact,
    onDismiss: () -> Unit,
    onCall: (String) -> Unit
) {
    val categoryColor = getCategoryColor(contact.category)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    categoryColor,
                                    categoryColor.copy(alpha = 0.8f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = contact.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                CategoryBadge(
                                    text = contact.category.replaceFirstChar { it.uppercase() },
                                    icon = getCategoryIcon(contact.category)
                                )
                                contact.subcategory?.let {
                                    CategoryBadge(text = it)
                                }
                            }
                        }
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.contacts_close),
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                ) {
                    // Description
                    item {
                        ModalSection(
                            icon = Icons.Default.Info,
                            iconColor = SecurityColor,
                            title = stringResource(R.string.contacts_description)
                        ) {
                            Text(
                                text = contact.description,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 20.sp
                            )
                        }
                    }

                    // Phone Numbers
                    item {
                        val phoneNumbers = buildList {
                            contact.tel?.let { add(stringResource(R.string.contacts_primary) to it) }
                            contact.alternativeTel?.let { add(stringResource(R.string.contacts_alternative) to it) }
                            contact.thirdTel?.let { add(stringResource(R.string.contacts_third) to it) }
                            contact.fourthTel?.let { add(stringResource(R.string.contacts_fourth) to it) }
                            contact.emergencyTel?.let { add(stringResource(R.string.contacts_emergency) to it) }
                        }

                        if (phoneNumbers.isNotEmpty()) {
                            ModalSection(
                                icon = Icons.Default.Call,
                                iconColor = Color(0xFF51CF66),
                                title = stringResource(R.string.contacts_phone_numbers)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    phoneNumbers.forEach { (label, number) ->
                                        PhoneNumberItem(
                                            label = label,
                                            number = number,
                                            onCallClick = { onCall(number) }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Email
                    contact.email?.let { email ->
                        item {
                            ModalSection(
                                icon = Icons.Default.Email,
                                iconColor = SecurityColor,
                                title = stringResource(R.string.contacts_email)
                            ) {
                                Text(
                                    text = email,
                                    fontSize = 14.sp,
                                    color = SecurityColor,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Location
                    contact.location?.let { location ->
                        item {
                            ModalSection(
                                icon = Icons.Default.LocationOn,
                                iconColor = HealthColor,
                                title = stringResource(R.string.contacts_location)
                            ) {
                                Text(
                                    text = location,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    // Availability
                    contact.availability?.let { availability ->
                        item {
                            ModalSection(
                                icon = painterResource(R.drawable.clock_outline),
                                iconColor = InsuranceColor,
                                title = stringResource(R.string.contacts_availability)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color(0xFF51CF66))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = availability,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Footer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ID: ${contact.id}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.contacts_close),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryBadge(
    text: String,
    icon: Int? = null
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icon?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
private fun ModalSection(
    icon: Any,
    iconColor: Color,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            when (icon) {
                is androidx.compose.ui.graphics.vector.ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                is androidx.compose.ui.graphics.painter.Painter -> {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        content()
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp
    )
}

@Composable
private fun PhoneNumberItem(
    label: String,
    number: String,
    onCallClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = label.uppercase(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = number,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Button(
            onClick = onCallClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF51CF66)
            ),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.contacts_call),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// Helper functions
private fun filterContacts(
    contacts: List<EmergencyContact>,
    category: String,
    searchText: String
): List<EmergencyContact> {
    var filtered = contacts

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
        "health" -> HealthColor
        "security" -> SecurityColor
        "fire" -> FireColor
        "insurance" -> InsuranceColor
        else -> DefaultColor
    }
}

private fun getCategoryIcon(category: String): Int {
    return when (category) {
        "health" -> R.drawable.heart_pulse
        "security" -> R.drawable.shield_half_full
        "fire" -> R.drawable.fire_alert
        "insurance" -> R.drawable.file_document_multiple_outline
        else -> R.drawable.account_badge
    }
}
