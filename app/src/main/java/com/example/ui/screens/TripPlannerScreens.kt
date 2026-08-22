package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ActivityItem
import com.example.data.model.DayPlan
import com.example.data.model.TripPlan
import com.example.ui.components.IndiaMapRouteView
import com.example.ui.components.OnlineOfflineBanner
import com.example.ui.theme.IndiaTeal
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronTertiary
import com.example.viewmodel.TravelUiState
import com.example.viewmodel.TravelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripPlannerScreen(
    viewModel: TravelViewModel,
    uiState: TravelUiState,
    initialDestination: String? = null,
    onNavigateToItinerary: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var startingPoint by remember { mutableStateOf("Visakhapatnam") }
    var destinationName by remember { mutableStateOf(initialDestination ?: uiState.activeTrip?.destinationName ?: "Kerala") }
    var days by remember { mutableStateOf(if (destinationName.contains("Kerala", true)) 4 else 3) }
    var travelers by remember { mutableStateOf(2) }
    var budget by remember { mutableStateOf(if (destinationName.contains("Kerala", true)) 18000f else 10000f) }

    val isKerala = destinationName.contains("Kerala", true) || destinationName.contains("Munnar", true) || destinationName.contains("Kochi", true)

    val interestOptions = if (isKerala) {
        listOf("Backwaters", "Tea Plantations", "Heritage", "Waterfalls", "Ayurveda", "Houseboat", "Kathakali", "Wildlife", "Beaches")
    } else {
        listOf("Nature", "Caves", "Waterfalls", "Coffee", "Tribal Culture", "Heritage", "Trekking", "Photography", "Temples")
    }
    val selectedInterests = remember(isKerala) {
        if (isKerala) {
            mutableStateListOf("Backwaters", "Tea Plantations", "Heritage", "Waterfalls")
        } else {
            mutableStateListOf("Nature", "Caves", "Waterfalls", "Coffee")
        }
    }

    val foodOptions = if (isKerala) {
        listOf("Authentic Kerala Sadya & Seafood", "Malabar Parotta & Stew", "South Indian Veg Thali", "Jain Food")
    } else {
        listOf("Andhra Local Special", "South Indian Veg", "North Indian Thali", "Coastal Seafood", "Jain Food")
    }
    var selectedFood by remember(isKerala) { mutableStateOf(foodOptions.first()) }

    val transportOptions = if (isKerala) {
        listOf("Express Train (Dhanbad-Alappuzha) / Air", "KSRTC Low-Floor AC / Private Cab", "SWTD Public Backwater Ferry", "Self Drive Rental")
    } else {
        listOf("Vistadome Glass Train / Cab", "Private AC Cab", "APSRTC Express Bus", "Self Drive Rental")
    }
    var selectedTransport by remember(isKerala) { mutableStateOf(transportOptions.first()) }

    val hotelOptions = if (isKerala) {
        listOf("Heritage Backwater Homestay & Resort", "KTDC Tamarind / Samudra Stay", "Luxury Tea Estate Villa", "Traditional Houseboat")
    } else {
        listOf("Haritha Hill Resort (AP Tourism)", "Eco Nature Retreat", "Budget Homestay", "Luxury Plantation Villa")
    }
    var selectedHotel by remember(isKerala) { mutableStateOf(hotelOptions.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plan My Trip", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = {
                            viewModel.generateTrip(
                                startingPoint = startingPoint,
                                destinationName = destinationName,
                                days = days,
                                travelers = travelers,
                                budget = budget.toInt(),
                                interests = selectedInterests.toList(),
                                foodPref = selectedFood,
                                transportPref = selectedTransport,
                                hotelPref = selectedHotel
                            )
                            onNavigateToItinerary()
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("generate_itinerary_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generate Personalized Itinerary", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Personalized Trip Generator",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Craft an optimized journey tailored to your schedule and budget.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Origin and Destination inputs
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = startingPoint,
                            onValueChange = { startingPoint = it },
                            label = { Text("Starting Location / City") },
                            leadingIcon = { Icon(Icons.Default.MyLocation, contentDescription = null, tint = IndiaTeal) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().testTag("starting_location_input")
                        )

                        OutlinedTextField(
                            value = destinationName,
                            onValueChange = { destinationName = it },
                            label = { Text("Destination / Region") },
                            leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = SaffronPrimary) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().testTag("destination_name_input")
                        )

                        Text(
                            text = "Popular Destinations:",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("Kerala", "Araku Valley", "Goa", "Rajasthan").forEach { dest ->
                                val isSelected = destinationName.equals(dest, ignoreCase = true)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { destinationName = dest },
                                    label = { Text(dest, fontSize = 11.sp) }
                                )
                            }
                        }
                    }
                }
            }

            // Duration and Travelers
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Duration Counter
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Duration (Days)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("Day-by-day customized schedule", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = { if (days > 1) days-- },
                                    modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                                }
                                Text("$days Days", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { if (days < 10) days++ },
                                    modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase")
                                }
                            }
                        }

                        Divider()

                        // Travelers Counter
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Travelers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("Adults & Companions", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = { if (travelers > 1) travelers-- },
                                    modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                                }
                                Text("$travelers Travelers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { if (travelers < 10) travelers++ },
                                    modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase")
                                }
                            }
                        }

                        Divider()

                        // Target Budget Slider
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Target Total Budget", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("₹${budget.toInt()}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SaffronPrimary)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Slider(
                                value = budget,
                                onValueChange = { budget = it },
                                valueRange = 3000f..30000f,
                                steps = 27,
                                colors = SliderDefaults.colors(
                                    thumbColor = SaffronPrimary,
                                    activeTrackColor = SaffronPrimary
                                ),
                                modifier = Modifier.testTag("budget_slider")
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("₹3,000 (Budget)", style = MaterialTheme.typography.labelSmall)
                                Text("₹30,000 (Luxury)", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }

            // Travel Interests Selection
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Travel Interests & Focus", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(interestOptions) { interest ->
                                val isSelected = selectedInterests.contains(interest)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        if (isSelected) selectedInterests.remove(interest) else selectedInterests.add(interest)
                                    },
                                    label = { Text(interest) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = IndiaTeal,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Food & Transport Preferences
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text("Travel Style & Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        // Food
                        Column {
                            Text("Food & Dining Style:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(foodOptions) { f ->
                                    FilterChip(
                                        selected = selectedFood == f,
                                        onClick = { selectedFood = f },
                                        label = { Text(f) }
                                    )
                                }
                            }
                        }

                        // Transport
                        Column {
                            Text("Preferred Transport:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(transportOptions) { t ->
                                    FilterChip(
                                        selected = selectedTransport == t,
                                        onClick = { selectedTransport = t },
                                        label = { Text(t) }
                                    )
                                }
                            }
                        }

                        // Stay
                        Column {
                            Text("Stay Preference:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(hotelOptions) { h ->
                                    FilterChip(
                                        selected = selectedHotel == h,
                                        onClick = { selectedHotel = h },
                                        label = { Text(h) }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    viewModel: TravelViewModel,
    uiState: TravelUiState,
    onNavigateBack: () -> Unit,
    onNavigateToOptimizer: () -> Unit,
    onNavigateToPrices: () -> Unit,
    onNavigateToBudget: () -> Unit,
    onNavigateToMap: () -> Unit
) {
    val trip = uiState.activeTrip

    if (trip == null) {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("No active itinerary found")
            }
        }
        return
    }

    var selectedDayIndex by remember { mutableStateOf(0) }
    var showReplanSheet by remember { mutableStateOf(false) }
    var replanInputText by remember { mutableStateOf("") }
    var isTripSavedToDb by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(trip.title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveCurrentTripToRoom()
                        isTripSavedToDb = true
                    }) {
                        Icon(
                            imageVector = if (isTripSavedToDb) Icons.Filled.BookmarkAdded else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Save Trip",
                            tint = if (isTripSavedToDb) SaffronPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { showReplanSheet = true },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).testTag("replan_trip_button")
                    ) {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null, modifier = Modifier.size(16.dp), tint = SaffronPrimary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Replan Trip")
                    }

                    Button(
                        onClick = onNavigateToOptimizer,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        modifier = Modifier.weight(1.2f).testTag("optimize_trip_route_button")
                    ) {
                        Icon(Icons.Default.AltRoute, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Optimize Route")
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Replan Success notification banner
            if (uiState.replanMessage != null) {
                item {
                    Surface(
                        color = IndiaTeal.copy(alpha = 0.12f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = IndiaTeal, modifier = Modifier.size(20.dp))
                            Text(
                                text = uiState.replanMessage,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = IndiaTeal
                            )
                        }
                    }
                }
            }

            // Summary Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${trip.startingPoint} ➔ ${trip.destinationName}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${trip.startDate} • ${trip.numberOfDays} Days • ${trip.numberOfTravelers} Travelers",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "₹${trip.currentEstimatedCost}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IndiaTeal
                                )
                                Text(
                                    text = "Budget: ₹${trip.budget}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (trip.currentEstimatedCost <= trip.budget) Color(0xFF2E7D32) else Color(0xFFC62828),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))

                        // Quick tool buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            TextButton(onClick = onNavigateToPrices) {
                                Icon(Icons.Default.PriceCheck, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Compare Prices", fontSize = 12.sp)
                            }
                            TextButton(onClick = onNavigateToBudget) {
                                Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Budget Optimizer", fontSize = 12.sp)
                            }
                            TextButton(onClick = onNavigateToMap) {
                                Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Route Map", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Route Map Preview Canvas
            item {
                IndiaMapRouteView(
                    tripPlan = trip,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Day Selector Tabs
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedDayIndex.coerceIn(0, trip.dayPlans.size - 1),
                    edgePadding = 16.dp,
                    divider = {}
                ) {
                    trip.dayPlans.forEachIndexed { index, day ->
                        Tab(
                            selected = selectedDayIndex == index,
                            onClick = { selectedDayIndex = index },
                            text = {
                                Text(
                                    text = "Day ${day.dayNumber}",
                                    fontWeight = if (selectedDayIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Day Schedule & Activities
            val activeDay = trip.dayPlans.getOrNull(selectedDayIndex) ?: trip.dayPlans.firstOrNull()
            if (activeDay != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = activeDay.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = activeDay.theme,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = "Est. ₹${activeDay.estimatedCost}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = IndiaTeal
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                itemsIndexed(activeDay.activities) { index, activity ->
                    ActivityTimelineCard(
                        activity = activity,
                        index = index,
                        isLast = index == activeDay.activities.size - 1,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    // Natural Language Replanning Bottom Sheet Modal
    if (showReplanSheet) {
        ModalBottomSheet(
            onDismissRequest = { showReplanSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null, tint = SaffronPrimary)
                        Text("Smart Trip Replanner", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = { showReplanSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = "Describe your desired changes in natural language, or pick a fast modification below:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Quick Transformation Suggestion Chips
                Text("Quick Modification Prompts:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                val quickSuggestions = listOf(
                    "I only have 2 days",
                    "Make the trip cheaper",
                    "Add a waterfall to the plan",
                    "It is raining, suggest indoor places",
                    "Change Day 2 schedule"
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(quickSuggestions) { prompt ->
                        SuggestionChip(
                            onClick = {
                                replanInputText = prompt
                                viewModel.replanTripWithInstruction(prompt)
                                showReplanSheet = false
                            },
                            label = { Text(prompt) }
                        )
                    }
                }

                OutlinedTextField(
                    value = replanInputText,
                    onValueChange = { replanInputText = it },
                    placeholder = { Text("e.g. 'Shorten to 2 days', 'Make it cheaper', 'Add Katiki falls'...") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("replan_custom_input")
                )

                Button(
                    onClick = {
                        if (replanInputText.isNotBlank()) {
                            viewModel.replanTripWithInstruction(replanInputText)
                            showReplanSheet = false
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("apply_replan_button")
                ) {
                    Text("Apply AI Trip Modification", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ActivityTimelineCard(
    activity: ActivityItem,
    index: Int,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        // Timeline node
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = SaffronPrimary,
                modifier = Modifier.size(20.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("${index + 1}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(90.dp)
                        .background(SaffronPrimary.copy(alpha = 0.3f))
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Activity Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = IndiaTeal, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = activity.timeSlot,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IndiaTeal
                        )
                    }

                    Surface(
                        color = if (activity.isIndoor) IndiaTeal.copy(alpha = 0.12f) else SaffronPrimary.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (activity.isIndoor) "Indoor" else "Outdoor",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (activity.isIndoor) IndiaTeal else SaffronPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = activity.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(activity.location, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(
                        text = "₹${activity.estimatedCost}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                }
            }
        }
    }
}
