package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val id: String,
    val title: String,
    val startingPoint: String,
    val destinationName: String,
    val stateName: String,
    val startDate: String,
    val endDate: String,
    val numberOfDays: Int,
    val numberOfTravelers: Int,
    val budget: Int,
    val estimatedCost: Int,
    val interestsJson: String,
    val foodPreference: String,
    val transportPreference: String,
    val hotelPreference: String,
    val itineraryJson: String,
    val isOptimized: Boolean = false,
    val totalDistanceKm: Double = 62.0,
    val totalTravelTimeMinutes: Int = 185,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_places")
data class SavedPlaceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val stateOrDestination: String,
    val category: String,
    val rating: Double,
    val approximateCost: Int,
    val description: String,
    val isOfflineAvailable: Boolean = true,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String,
    val text: String,
    val sourcesJson: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
