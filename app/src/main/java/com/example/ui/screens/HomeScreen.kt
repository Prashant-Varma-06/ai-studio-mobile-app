package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Destination
import com.example.data.model.DestinationCategory
import com.example.data.model.RegionInfo
import com.example.data.repository.IndiaTravelDataset
import com.example.ui.components.OnlineOfflineBanner
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*
import com.example.viewmodel.TravelUiState
import com.example.viewmodel.TravelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TravelViewModel,
    uiState: TravelUiState,
    onNavigateToExplore: () -> Unit,
    onNavigateToPlanTrip: () -> Unit,
    onNavigateToPriceComparison: () -> Unit,
    onNavigateToAssistant: () -> Unit,
    onNavigateToOptimizer: () -> Unit,
    onNavigateToNearby: () -> Unit,
    onNavigateToWeather: () -> Unit,
    onSelectRegion: (RegionInfo) -> Unit,
    onSelectDestination: (Destination) -> Unit
) {
    val trendingDestinations = remember { IndiaTravelDataset.destinations }
    val featuredStates = remember { IndiaTravelDataset.statesAndUTs.take(6) }

    Scaffold(
        containerColor = SophisticatedDarkBg,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SophisticatedDarkBg)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                // Header with Discover India title and luxury avatar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DISCOVER INDIA",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Namaste, Arjun",
                            style = MaterialTheme.typography.headlineMedium.copy(fontFamily = FontFamily.Serif),
                            fontWeight = FontWeight.Bold,
                            color = TextSlatePrimary
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onNavigateToAssistant,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SophisticatedDarkSurfaceVariant)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = "Travel Assistant",
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Luxury avatar
                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(1.dp, SophisticatedDarkBorder),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            listOf(GoldPrimary, GoldSecondary)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "AS",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF0A0A0A)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar in Sophisticated Dark style
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SophisticatedDarkSurfaceVariant,
                    border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToExplore() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextSlateMuted,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Search destination, state or UT...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSlateMuted
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SophisticatedDarkBg)
                .padding(innerPadding)
        ) {
            // Online/Offline status banner
            item {
                OnlineOfflineBanner(
                    isOnline = uiState.isOnline,
                    onToggleSimulation = { viewModel.setNetworkStatus(!uiState.isOnline) }
                )
            }

            // Category Chips Row (Horizontal Scroll with All India, Goa, Kerala, Rajasthan...)
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        val isAllSelected = uiState.selectedCategory == DestinationCategory.ALL
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isAllSelected) GoldPrimary else SophisticatedDarkSurfaceVariant,
                            border = if (isAllSelected) null else BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                            modifier = Modifier.clickable {
                                viewModel.onCategorySelect(DestinationCategory.ALL)
                            }
                        ) {
                            Text(
                                text = "All India",
                                color = if (isAllSelected) Color(0xFF0A0A0A) else TextSlateSecondary,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }

                    items(DestinationCategory.values().filter { it != DestinationCategory.ALL }) { category ->
                        val isSelected = uiState.selectedCategory == category
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) GoldPrimary else SophisticatedDarkSurfaceVariant,
                            border = if (isSelected) null else BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                            modifier = Modifier.clickable {
                                viewModel.onCategorySelect(category)
                                onNavigateToExplore()
                            }
                        ) {
                            Text(
                                text = category.displayName,
                                color = if (isSelected) Color(0xFF0A0A0A) else TextSlateSecondary,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Section: Active Itinerary Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Itinerary",
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                        fontWeight = FontWeight.SemiBold,
                        color = TextSlateSecondary
                    )

                    Surface(
                        color = EmeraldLive.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, EmeraldLive.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldLive)
                            )
                            Text(
                                text = "ONLINE",
                                color = EmeraldLive,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Hero Active Itinerary Card (Sophisticated Dark Glassmorphism)
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SophisticatedDarkSurface),
                    border = BorderStroke(1.dp, SophisticatedDarkBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Column {
                        // Hero Header with Gradient & Dates Badge
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF1E293B),
                                            Color(0xFF0F172A),
                                            SophisticatedDarkSurface
                                        )
                                    )
                                )
                        ) {
                            // Date pill badge
                            val trip = uiState.activeTrip
                            Surface(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, SophisticatedDarkBorder),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = if (trip != null) "${trip.numberOfDays} DAYS • ${trip.numberOfTravelers} PERS" else "3 DAYS • 2 PERS",
                                    color = TextSlatePrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            // Destination titles
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = trip?.destinationName ?: "Kerala",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = FontFamily.Serif),
                                    fontWeight = FontWeight.Bold,
                                    color = TextSlatePrimary
                                )
                                Text(
                                    text = if (trip != null) "${trip.startingPoint} → ${trip.destinationName} • ${trip.stateName}" else "Visakhapatnam → Kochi • Kerala",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSlateSecondary
                                )
                            }
                        }

                        // Inner details & stats
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val trip = uiState.activeTrip
                            // 2-Column Stat Grid
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Budget Card
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = SophisticatedDarkSurfaceVariant,
                                    border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "EST. BUDGET",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSlateMuted,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "₹${trip?.currentEstimatedCost ?: 8500}",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = TextSlatePrimary
                                            )
                                            if (trip?.isOptimized == true) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "SAVED",
                                                    color = EmeraldLive,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }

                                // Transport Card
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = SophisticatedDarkSurfaceVariant,
                                    border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "MODE",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSlateMuted,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = trip?.transportPref ?: "Express Rail / AC Flight",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = TextSlatePrimary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                            // Travel Assistant AI Insight Card with Gold Left Border
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = SophisticatedDarkSurfaceVariant,
                                border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min)
                                ) {
                                    // Gold accent indicator bar
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(4.dp)
                                            .background(GoldPrimary)
                                    )

                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "TRAVEL ASSISTANT",
                                                color = GoldPrimary,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 1.sp
                                            )
                                            Text(
                                                text = "Live RAG Verified",
                                                color = TextSlateMuted,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontSize = 10.sp
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        val insight = if (trip?.destinationName?.contains("Kerala", true) == true || trip?.destinationName?.contains("Munnar", true) == true) {
                                            "\"Direct Express trains (Dhanbad-Alappuzha #13351) & Kochi flights connect Visakhapatnam to Kerala backwaters seamlessly.\""
                                        } else if (trip?.destinationName?.contains("Goa", true) == true) {
                                            "\"Optimal time for Aguada Fort & Dudhsagar jeep trek. Recommended coastal ferry circuit.\""
                                        } else {
                                            "\"Weather is optimal for ${trip?.destinationName ?: "sightseeing"}. Route & attraction passes synced with state tourism directory.\""
                                        }

                                        Text(
                                            text = insight,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontStyle = FontStyle.Italic,
                                            color = TextSlateSecondary
                                        )
                                    }
                                }
                            }

                            // Action CTA Buttons: Gold Primary + Dark Frosted Secondary
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = onNavigateToPlanTrip,
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = GoldPrimary,
                                        contentColor = Color(0xFF0A0A0A)
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp)
                                        .testTag("home_plan_trip_button")
                                ) {
                                    Text(
                                        text = "Plan My Trip",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }

                                Button(
                                    onClick = onNavigateToOptimizer,
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0x1AFFFFFF),
                                        contentColor = TextSlatePrimary
                                    ),
                                    border = BorderStroke(1.dp, SophisticatedDarkBorder),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp)
                                        .testTag("home_smart_optimize_button")
                                ) {
                                    Text(
                                        text = "Smart Optimize",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Tool Shortcuts in Dark Luxury Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickToolCard(
                        title = "Prices",
                        icon = Icons.Default.PriceCheck,
                        color = GoldPrimary,
                        onClick = onNavigateToPriceComparison,
                        modifier = Modifier.weight(1f)
                    )
                    QuickToolCard(
                        title = "Assistant",
                        icon = Icons.Default.SmartToy,
                        color = CyanAccent,
                        onClick = onNavigateToAssistant,
                        modifier = Modifier.weight(1f)
                    )
                    QuickToolCard(
                        title = "Optimize",
                        icon = Icons.Default.AltRoute,
                        color = GoldSecondary,
                        onClick = onNavigateToOptimizer,
                        modifier = Modifier.weight(1f)
                    )
                    QuickToolCard(
                        title = "Nearby",
                        icon = Icons.Default.NearMe,
                        color = PriceCachedBlue,
                        onClick = onNavigateToNearby,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Smart Price Comparison Navigation Banner
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SophisticatedDarkSurfaceVariant),
                    border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clickable { onNavigateToPriceComparison() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0x1AFFFFFF),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Hotel,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = "Smart Price Comparison",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSlatePrimary
                                )
                                Text(
                                    text = "3 providers compared • Best Match: ₹2,100",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSlateSecondary
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Open Price Comparison",
                            tint = GoldPrimary
                        )
                    }
                }
            }

            // Trending Indian Destinations
            item {
                SectionHeader(
                    title = "Trending Indian Destinations",
                    actionText = "See All",
                    onActionClick = onNavigateToExplore
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trendingDestinations) { dest ->
                        DestinationCard(
                            destination = dest,
                            onClick = {
                                onSelectDestination(dest)
                            }
                        )
                    }
                }
            }

            // All 28 States & 8 UTs Directory
            item {
                Spacer(modifier = Modifier.height(10.dp))
                SectionHeader(
                    title = "Explore Indian States & UTs",
                    actionText = "Explore All 36",
                    onActionClick = onNavigateToExplore
                )

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    featuredStates.forEach { region ->
                        RegionSummaryCard(
                            region = region,
                            onClick = { onSelectRegion(region) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}

@Composable
fun QuickToolCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SophisticatedDarkSurfaceVariant),
        border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TextSlatePrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DestinationCard(
    destination: Destination,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, SophisticatedDarkBorder),
        colors = CardDefaults.cardColors(containerColor = SophisticatedDarkSurface),
        modifier = modifier
            .width(230.dp)
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF1E293B),
                                Color(0xFF0F172A)
                            )
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle)
                    ) {
                        Text(
                            text = destination.stateName,
                            color = GoldPrimary,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = destination.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                        fontWeight = FontWeight.Bold,
                        color = TextSlatePrimary
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${destination.rating}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextSlatePrimary
                        )
                    }

                    Text(
                        text = "₹${destination.estimatedBudgetPerDay}/day",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = destination.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSlateSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RegionSummaryCard(
    region: RegionInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SophisticatedDarkSurface),
        border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = CircleShape,
                    color = SophisticatedDarkSurfaceVariant,
                    border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = region.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                            fontWeight = FontWeight.Bold,
                            color = TextSlatePrimary
                        )
                        Surface(
                            color = Color(0x1AFFFFFF),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (region.type.name == "STATE") "State" else "UT",
                                color = GoldSecondary,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = "Capital: ${region.capital} • Best: ${region.bestTimeToVisit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSlateSecondary
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Details",
                tint = TextSlateMuted
            )
        }
    }
}
