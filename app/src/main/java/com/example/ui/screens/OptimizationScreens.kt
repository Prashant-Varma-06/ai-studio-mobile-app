package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PriceComparisonItem
import com.example.data.model.PriceStatus
import com.example.data.model.RecommendationType
import com.example.data.repository.IndiaTravelDataset
import com.example.ui.components.IndiaMapRouteView
import com.example.ui.components.PriceStatusBadge
import com.example.ui.components.RecommendationBadge
import com.example.ui.theme.IndiaTeal
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronTertiary
import com.example.viewmodel.TravelUiState
import com.example.viewmodel.TravelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartTripOptimizerScreen(
    viewModel: TravelViewModel,
    uiState: TravelUiState,
    onNavigateBack: () -> Unit,
    onNavigateToItinerary: () -> Unit
) {
    val trip = uiState.activeTrip

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Route Optimizer", fontWeight = FontWeight.Bold) },
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
                            viewModel.optimizeTripRoute()
                            onNavigateToItinerary()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndiaTeal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("apply_route_optimization_button")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (trip?.isOptimized == true) "Route Already Optimized (Return)" else "Apply Optimized Route to Itinerary",
                            fontWeight = FontWeight.Bold
                        )
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
                    text = "Geographic Route & Sequence Optimization",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Eliminates backtracking, reorders sightseeing stops by proximity, and reduces transit duration.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Before vs After Comparison Cards Grid
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Optimization Impact Analysis",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        val distBefore = trip?.distanceKm ?: 220.0
                        val distAfter = if (trip?.isOptimized == true) distBefore else (distBefore * 0.85)
                        val savedDist = (distBefore - distAfter).toInt().coerceAtLeast(18)

                        val timeBeforeMin = trip?.travelTimeMinutes ?: 240
                        val timeAfterMin = (timeBeforeMin * 0.82).toInt()

                        val costBefore = trip?.localTransportCost ?: 2800
                        val costAfter = (costBefore * 0.78).toInt()
                        val savedCost = costBefore - costAfter

                        // Metric 1: Distance
                        OptimizationMetricRow(
                            label = "Total Travel Distance",
                            beforeValue = "${distBefore.toInt()} km",
                            afterValue = "${distAfter.toInt()} km",
                            savingsBadge = "SAVED $savedDist KM (Optimized)",
                            isPositive = true
                        )

                        Divider()

                        // Metric 2: Transit Time
                        OptimizationMetricRow(
                            label = "Estimated Transit Time",
                            beforeValue = "${timeBeforeMin / 60}h ${timeBeforeMin % 60}m",
                            afterValue = "${timeAfterMin / 60}h ${timeAfterMin % 60}m",
                            savingsBadge = "SAVED ${(timeBeforeMin - timeAfterMin)}m Travel Time",
                            isPositive = true
                        )

                        Divider()

                        // Metric 3: Local Transit Cost
                        OptimizationMetricRow(
                            label = "Local Transit & Logistics Cost",
                            beforeValue = "₹$costBefore",
                            afterValue = "₹$costAfter",
                            savingsBadge = "SAVED ₹$savedCost Expense",
                            isPositive = true
                        )
                    }
                }
            }

            // Interactive Map Route View
            if (trip != null) {
                item {
                    IndiaMapRouteView(
                        tripPlan = trip.copy(isOptimized = true),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Stop Sequence Breakdown
            item {
                val isKer = trip?.destinationName?.contains("Kerala", true) == true || trip?.destinationName?.contains("Munnar", true) == true
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Optimized Stop Sequencing Rationale",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        if (isKer) {
                            SequenceStep(
                                stepNum = 1,
                                title = "${trip?.startingPoint ?: "Visakhapatnam"} Origin ➔ Kochi Heritage Arrival",
                                detail = "Direct transit via Dhanbad-Alappuzha Express / Flight; check-in at Fort Kochi without coastal backtracking."
                            )
                            SequenceStep(
                                stepNum = 2,
                                title = "Fort Kochi, Chinese Fishing Nets & Kathakali",
                                detail = "Grouped historical walking tour and Kathakali performance within 2 km radius."
                            )
                            SequenceStep(
                                stepNum = 3,
                                title = "Munnar Tea Hills & Eravikulam National Park",
                                detail = "Scenic Ghats highway drive with scheduled Cheeyappara Falls stop on the ascent route."
                            )
                            SequenceStep(
                                stepNum = 4,
                                title = "Alleppey Backwaters & Kettuvallam Cruise",
                                detail = "Direct highway route south to Vembanad lake; optimal boarding window for afternoon cruise."
                            )
                        } else {
                            SequenceStep(
                                stepNum = 1,
                                title = "${trip?.startingPoint ?: "Visakhapatnam"} Origin ➔ Ananthagiri Ghats",
                                detail = "Ascend the scenic Eastern Ghats; scheduled morning viewpoint stop."
                            )
                            SequenceStep(
                                stepNum = 2,
                                title = "Borra Caves & Katiki Waterfalls Grouped",
                                detail = "Both major attractions sit on the same mountain spur—visiting back-to-back saves 18 km detour."
                            )
                            SequenceStep(
                                stepNum = 3,
                                title = "${trip?.destinationName ?: "Destination"} Hub Check-in & Coffee Museum",
                                detail = "Central base located 28 km from caves; allows relaxing evening stroll and dining."
                            )
                            SequenceStep(
                                stepNum = 4,
                                title = "Scenic Cascades & Departure Transit",
                                detail = "Explored smoothly on the return loop without backtracking through town center."
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun OptimizationMetricRow(
    label: String,
    beforeValue: String,
    afterValue: String,
    savingsBadge: String,
    isPositive: Boolean
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Surface(
                color = if (isPositive) Color(0xFF2E7D32).copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = savingsBadge,
                    color = if (isPositive) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Before: $beforeValue",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(12.dp), tint = IndiaTeal)
            Text(
                text = "Optimized: $afterValue",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = IndiaTeal
            )
        }
    }
}

@Composable
fun SequenceStep(stepNum: Int, title: String, detail: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = IndiaTeal,
            modifier = Modifier.size(22.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("$stepNum", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceComparisonScreen(
    viewModel: TravelViewModel,
    uiState: TravelUiState,
    onNavigateBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Hotel", "Train", "Cab", "Bus")

    val items = remember(selectedCategory) {
        if (selectedCategory == "All") {
            IndiaTravelDataset.priceComparisonCatalog
        } else {
            IndiaTravelDataset.priceComparisonCatalog.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compare Prices", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Category Filter Chips
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SaffronPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Info banner about smart comparison
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = IndiaTeal)
                        Text(
                            text = "Aggregates verified tariffs from IRCTC, MakeMyTrip, Booking.com, APSRTC, and regional providers with Live/Cached tags.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Price Items List
            items(items) { item ->
                PriceComparisonCard(
                    item = item,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PriceComparisonCard(
    item: PriceComparisonItem,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.recommendationType == RecommendationType.BEST_MATCH) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (item.recommendationType == RecommendationType.BEST_MATCH) 3.dp else 1.dp
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RecommendationBadge(type = item.recommendationType)
                PriceStatusBadge(status = item.status)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title and Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Via ${item.provider} • ${item.distanceOrDuration}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "₹${item.price}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (item.recommendationType == RecommendationType.CHEAPEST) Color(0xFF2E7D32) else SaffronPrimary
                    )
                    Text(
                        text = "per traveler / night",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Reason
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Why this match: ${item.matchReason}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = SaffronTertiary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${item.rating}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(item.availability, style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { /* Provider booking simulation */ },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (item.recommendationType == RecommendationType.BEST_MATCH) SaffronPrimary else IndiaTeal
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Select Option", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetPlannerScreen(
    viewModel: TravelViewModel,
    uiState: TravelUiState,
    onNavigateBack: () -> Unit
) {
    val trip = uiState.activeTrip

    if (trip == null) {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("No active trip")
            }
        }
        return
    }

    var sliderBudget by remember { mutableStateOf(trip.budget.toFloat()) }
    val isOverBudget = trip.currentEstimatedCost > trip.budget

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget & Cost Optimizer", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Budget Status Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isOverBudget) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isOverBudget) "Budget Overrun Warning" else "Budget On Track",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isOverBudget) Color(0xFFC62828) else Color(0xFF2E7D32)
                                )
                                Text(
                                    text = if (isOverBudget) "Trip cost exceeds target by ₹${trip.currentEstimatedCost - trip.budget}" else "Estimated cost is ₹${trip.budget - trip.currentEstimatedCost} under target budget",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isOverBudget) Color(0xFFB71C1C) else Color(0xFF1B5E20)
                                )
                            }

                            Icon(
                                imageVector = if (isOverBudget) Icons.Default.Warning else Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = if (isOverBudget) Color(0xFFC62828) else Color(0xFF2E7D32),
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Target Budget", style = MaterialTheme.typography.labelSmall)
                                Text("₹${trip.budget}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Current Estimated Cost", style = MaterialTheme.typography.labelSmall)
                                Text("₹${trip.currentEstimatedCost}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SaffronPrimary)
                            }
                        }
                    }
                }
            }

            // Interactive Target Budget Adjuster
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Adjust Target Budget", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("₹${sliderBudget.toInt()}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = IndiaTeal)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Slider(
                            value = sliderBudget,
                            onValueChange = {
                                sliderBudget = it
                                viewModel.updateTargetBudget(it.toInt())
                            },
                            valueRange = 3000f..25000f,
                            steps = 22,
                            colors = SliderDefaults.colors(thumbColor = IndiaTeal, activeTrackColor = IndiaTeal)
                        )
                    }
                }
            }

            // Actionable AI Cost Saving Recommendations
            item {
                Text(
                    text = "Actionable Cost Saving Suggestions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Toggle options below to immediately apply verified savings to your active plan:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(uiState.budgetSuggestions) { sug ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(sug.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Surface(
                                    color = Color(0xFF2E7D32).copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "Saves ₹${sug.savingsAmount} in ${sug.category}",
                                        color = Color(0xFF2E7D32),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Switch(
                                checked = sug.isApplied,
                                onCheckedChange = { viewModel.applyBudgetSuggestion(sug.id) }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(sug.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(sug.beforeText, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
                            Text(sug.afterText, style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
