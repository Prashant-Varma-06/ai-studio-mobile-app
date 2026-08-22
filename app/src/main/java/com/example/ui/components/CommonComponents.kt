package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PriceStatus
import com.example.data.model.RecommendationType
import com.example.ui.theme.*

@Composable
fun OnlineOfflineBanner(
    isOnline: Boolean,
    onToggleSimulation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SophisticatedDarkSurface,
        border = BorderStroke(1.dp, SophisticatedDarkBorderSubtle),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isOnline) EmeraldLive else PriceEstimatedOrange)
                )
                Text(
                    text = if (isOnline) "ONLINE — Grounded Knowledge Active" else "OFFLINE — Cached Travel Intelligence",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isOnline) EmeraldLive else PriceEstimatedOrange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp
                )
            }

            AssistChip(
                onClick = onToggleSimulation,
                label = {
                    Text(
                        text = if (isOnline) "Test Offline" else "Go Live",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSlatePrimary
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = if (isOnline) Icons.Default.WifiOff else Icons.Default.Wifi,
                        contentDescription = "Toggle Network Status",
                        tint = GoldPrimary,
                        modifier = Modifier.size(13.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = SophisticatedDarkSurfaceVariant,
                    labelColor = TextSlatePrimary
                ),
                border = BorderStroke(1.dp, SophisticatedDarkBorder),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(28.dp)
            )
        }
    }
}

@Composable
fun PriceStatusBadge(status: PriceStatus, modifier: Modifier = Modifier) {
    val (bgColor, textColor) = when (status) {
        PriceStatus.LIVE -> Pair(EmeraldLive.copy(alpha = 0.15f), EmeraldLive)
        PriceStatus.ESTIMATED -> Pair(PriceEstimatedOrange.copy(alpha = 0.15f), PriceEstimatedOrange)
        PriceStatus.CACHED -> Pair(PriceCachedBlue.copy(alpha = 0.15f), PriceCachedBlue)
        PriceStatus.DEMO -> Pair(PriceDemoPurple.copy(alpha = 0.15f), PriceDemoPurple)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Text(
            text = status.label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun RecommendationBadge(type: RecommendationType, modifier: Modifier = Modifier) {
    val (bgColor, textColor, icon) = when (type) {
        RecommendationType.BEST_MATCH -> Triple(GoldPrimary, Color(0xFF0A0A0A), Icons.Default.Star)
        RecommendationType.CHEAPEST -> Triple(EmeraldLive, Color(0xFF0A0A0A), Icons.Default.Savings)
        RecommendationType.BEST_VALUE -> Triple(CyanAccent, Color(0xFF0A0A0A), Icons.Default.ThumbUp)
        RecommendationType.BEST_RATED -> Triple(Color(0xFFF472B6), Color(0xFF0A0A0A), Icons.Default.Grade)
        RecommendationType.CLOSEST -> Triple(PriceCachedBlue, Color(0xFF0A0A0A), Icons.Default.NearMe)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = type.label,
                color = textColor,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
            fontWeight = FontWeight.SemiBold,
            color = TextSlatePrimary
        )
        if (actionText != null && onActionClick != null) {
            TextButton(
                onClick = onActionClick,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelMedium,
                    color = GoldPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun EmptyStateCard(
    title: String,
    subtitle: String,
    icon: ImageVector = Icons.Outlined.TravelExplore,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, SophisticatedDarkBorder),
        colors = CardDefaults.cardColors(
            containerColor = SophisticatedDarkSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                fontWeight = FontWeight.Bold,
                color = TextSlatePrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSlateSecondary,
                textAlign = TextAlign.Center
            )
            if (actionLabel != null && onAction != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onAction,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = Color(0xFF0A0A0A)
                    )
                ) {
                    Text(actionLabel, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
