package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TripPlan
import com.example.ui.theme.*

data class MapWaypoint(
    val name: String,
    val description: String,
    val normX: Float, // 0.0 to 1.0
    val normY: Float, // 0.0 to 1.0
    val isMajorStop: Boolean = false,
    val dayNumber: Int = 1
)

@Composable
fun IndiaMapRouteView(
    tripPlan: TripPlan,
    onStopSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val destLower = tripPlan.destinationName.lowercase()
    val isKerala = destLower.contains("kerala") || destLower.contains("munnar") || destLower.contains("alleppey") || destLower.contains("kochi")
    val isGoa = destLower.contains("goa")
    val isRajasthan = destLower.contains("rajasthan") || destLower.contains("jaipur") || destLower.contains("udaipur")

    val waypoints = remember(tripPlan.destinationName, tripPlan.isOptimized, tripPlan.dayPlans) {
        when {
            isKerala -> {
                if (tripPlan.isOptimized) {
                    listOf(
                        MapWaypoint("Vizag / Kochi Gateway", "Origin & Coastal Transit", 0.12f, 0.20f, true, 1),
                        MapWaypoint("Fort Kochi & Nets", "Historic Heritage", 0.28f, 0.35f, true, 1),
                        MapWaypoint("Munnar Tea Hills", "High-Altitude Estates", 0.50f, 0.42f, true, 2),
                        MapWaypoint("Eravikulam Park", "Nilgiri Tahr Safari", 0.65f, 0.32f, true, 2),
                        MapWaypoint("Alleppey Backwaters", "Kettuvallam Houseboat", 0.78f, 0.68f, true, 3),
                        MapWaypoint("Thekkady Periyar", "Spice & Tiger Reserve", 0.90f, 0.55f, true, 4)
                    )
                } else {
                    listOf(
                        MapWaypoint("Vizag / Kochi Gateway", "Origin & Coastal Transit", 0.12f, 0.20f, true, 1),
                        MapWaypoint("Fort Kochi & Nets", "Historic Heritage", 0.28f, 0.35f, true, 1),
                        MapWaypoint("Munnar Tea Hills", "High-Altitude Estates", 0.50f, 0.42f, true, 2),
                        MapWaypoint("Alleppey Backwaters", "Kettuvallam Houseboat", 0.78f, 0.68f, true, 3),
                        MapWaypoint("Thekkady Periyar", "Spice & Tiger Reserve", 0.90f, 0.55f, true, 4)
                    )
                }
            }
            isGoa -> {
                listOf(
                    MapWaypoint("Goa Gateway / Airport", "Coastal Transit Hub", 0.15f, 0.80f, true, 1),
                    MapWaypoint("Fort Aguada", "Portuguese Bastion", 0.35f, 0.60f, true, 1),
                    MapWaypoint("Old Goa Basilicas", "UNESCO Heritage", 0.55f, 0.45f, true, 2),
                    MapWaypoint("Dudhsagar Falls", "Forest Waterfall Trek", 0.75f, 0.25f, true, 2),
                    MapWaypoint("Palolem Beach", "South Goa Tranquility", 0.88f, 0.85f, true, 3)
                )
            }
            isRajasthan -> {
                listOf(
                    MapWaypoint("Jaipur Pink City", "Royal Capital Arrival", 0.15f, 0.30f, true, 1),
                    MapWaypoint("Amer Fort", "Hilltop Bastion & Palace", 0.35f, 0.20f, true, 1),
                    MapWaypoint("Jodhpur Blue City", "Mehrangarh Citadel", 0.55f, 0.50f, true, 2),
                    MapWaypoint("Udaipur Lake City", "City Palace & Pichola", 0.80f, 0.75f, true, 3)
                )
            }
            else -> {
                if (tripPlan.isOptimized) {
                    listOf(
                        MapWaypoint("${tripPlan.startingPoint} Hub", "Trip Origin", 0.15f, 0.85f, true, 1),
                        MapWaypoint("Ananthagiri Viewpoint", "Ghat Scenic Stop", 0.35f, 0.65f, false, 1),
                        MapWaypoint("Borra Caves", "Limestone Wonders", 0.50f, 0.45f, true, 1),
                        MapWaypoint("Katiki Falls", "Cascading Waterfalls", 0.58f, 0.35f, true, 2),
                        MapWaypoint("Araku Center", "Coffee & Culture", 0.75f, 0.25f, true, 2),
                        MapWaypoint("Chaparai Streams", "Natural Cascades", 0.88f, 0.18f, true, 3)
                    )
                } else {
                    listOf(
                        MapWaypoint("${tripPlan.startingPoint} Hub", "Trip Origin", 0.15f, 0.85f, true, 1),
                        MapWaypoint("Araku Center", "Coffee & Culture", 0.75f, 0.25f, true, 2),
                        MapWaypoint("Borra Caves", "Limestone Wonders", 0.50f, 0.45f, true, 1),
                        MapWaypoint("Chaparai Streams", "Natural Cascades", 0.88f, 0.18f, true, 3),
                        MapWaypoint("Katiki Falls", "Cascading Waterfalls", 0.58f, 0.35f, true, 2)
                    )
                }
            }
        }
    }

    var selectedStop by remember(waypoints) { mutableStateOf(waypoints.firstOrNull()?.name) }

    val distStr = if (tripPlan.distanceKm >= 100) "${tripPlan.distanceKm.toInt()} km" else "${String.format(java.util.Locale.US, "%.0f", tripPlan.distanceKm)} km"

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SophisticatedDarkSurface
        ),
        border = BorderStroke(1.dp, SophisticatedDarkBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (tripPlan.isOptimized) "Optimized Route Map ($distStr)" else "Standard Route Map ($distStr)",
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                        fontWeight = FontWeight.Bold,
                        color = TextSlatePrimary
                    )
                    Text(
                        text = if (tripPlan.isOptimized) "Shortest geographic waypoint sequencing" else "Sequential itinerary order",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSlateSecondary
                    )
                }

                Surface(
                    color = if (tripPlan.isOptimized) EmeraldLive.copy(alpha = 0.15f) else GoldPrimary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, if (tripPlan.isOptimized) EmeraldLive.copy(alpha = 0.3f) else GoldPrimary.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = if (tripPlan.isOptimized) "ROUTE OPTIMIZED" else "STANDARD",
                        color = if (tripPlan.isOptimized) EmeraldLive else GoldPrimary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Canvas Map Representation (Sophisticated Obsidian / Gold Radar Map)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F141C))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Draw subtle radar terrain contours
                    drawCircle(
                        color = Color(0x1F38444D),
                        radius = w * 0.45f,
                        center = Offset(w * 0.5f, h * 0.5f)
                    )
                    drawCircle(
                        color = Color(0x14D4AF37),
                        radius = w * 0.25f,
                        center = Offset(w * 0.5f, h * 0.5f)
                    )

                    // Draw Route Polyline
                    val routePath = Path()
                    waypoints.forEachIndexed { index, pt ->
                        val x = pt.normX * w
                        val y = pt.normY * h
                        if (index == 0) {
                            routePath.moveTo(x, y)
                        } else {
                            routePath.lineTo(x, y)
                        }
                    }

                    drawPath(
                        path = routePath,
                        color = if (tripPlan.isOptimized) CyanAccent else GoldPrimary,
                        style = Stroke(
                            width = 6f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                        )
                    )

                    // Draw Waypoint nodes
                    waypoints.forEach { pt ->
                        val x = pt.normX * w
                        val y = pt.normY * h
                        val isSelected = pt.name == selectedStop

                        // Outer ring
                        drawCircle(
                            color = if (isSelected) GoldPrimary else Color(0xFF64748B),
                            radius = if (isSelected) 14f else 10f,
                            center = Offset(x, y)
                        )
                        // Inner node
                        drawCircle(
                            color = if (isSelected) GoldSecondary else Color(0xFF1E293B),
                            radius = if (isSelected) 8f else 6f,
                            center = Offset(x, y)
                        )
                    }
                }

                // Interactive Waypoint Buttons overlay
                waypoints.forEach { pt ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = (pt.normX * 280).dp,
                                top = (pt.normY * 160).dp
                            )
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (selectedStop == pt.name) GoldPrimary else Color(0xFF1E293B),
                            border = BorderStroke(1.dp, if (selectedStop == pt.name) GoldSecondary else SophisticatedDarkBorder),
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    selectedStop = pt.name
                                    onStopSelected(pt.name)
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${pt.dayNumber}",
                                    color = if (selectedStop == pt.name) Color(0xFF0A0A0A) else TextSlatePrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Selected waypoint info banner
            val activePt = waypoints.find { it.name == selectedStop } ?: waypoints.first()
            Surface(
                color = SophisticatedDarkSurfaceVariant,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "${activePt.name} (Day ${activePt.dayNumber})",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextSlatePrimary
                        )
                        Text(
                            text = activePt.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSlateSecondary
                        )
                    }
                }
            }
        }
    }
}
