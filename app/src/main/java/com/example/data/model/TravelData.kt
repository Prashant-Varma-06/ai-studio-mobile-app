package com.example.data.model

enum class RegionType {
    STATE,
    UNION_TERRITORY
}

enum class DestinationCategory(val displayName: String) {
    ALL("All"),
    BEACHES("Beaches"),
    HILL_STATIONS("Hill Stations"),
    HISTORICAL("Historical"),
    CULTURAL("Cultural"),
    RELIGIOUS("Religious"),
    WILDLIFE("Wildlife"),
    ADVENTURE("Adventure"),
    FAMILY("Family"),
    BUDGET("Budget"),
    FOOD("Food & Culture"),
    SHOPPING("Shopping")
}

enum class PriceStatus(val label: String) {
    LIVE("LIVE"),
    ESTIMATED("ESTIMATED"),
    CACHED("CACHED"),
    DEMO("DEMO")
}

enum class RecommendationType(val label: String) {
    BEST_MATCH("Best Match For You"),
    CHEAPEST("Cheapest"),
    BEST_VALUE("Best Value"),
    BEST_RATED("Best Rated"),
    CLOSEST("Closest")
}

data class RegionInfo(
    val id: String,
    val name: String,
    val type: RegionType,
    val capital: String,
    val description: String,
    val popularDestinations: List<String>,
    val famousAttractions: List<String>,
    val famousFood: List<String>,
    val culture: String,
    val festivals: List<String>,
    val transportation: String,
    val bestTimeToVisit: String,
    val estimatedDailyBudget: Int,
    val travelTips: List<String>,
    val category: DestinationCategory = DestinationCategory.HISTORICAL
)

data class Destination(
    val id: String,
    val name: String,
    val stateName: String,
    val category: DestinationCategory,
    val description: String,
    val rating: Double,
    val reviewCount: Int,
    val bestTimeToVisit: String,
    val estimatedBudgetPerDay: Int,
    val tags: List<String>,
    val popularSpots: List<String>,
    val isPopular: Boolean = false,
    val isTrending: Boolean = false,
    val isWeekend: Boolean = false,
    val isBudgetFriendly: Boolean = false,
    val lat: Double = 17.6868,
    val lng: Double = 83.2185
)

data class Attraction(
    val id: String,
    val destinationId: String,
    val name: String,
    val category: String,
    val description: String,
    val entryFee: Int,
    val openingHours: String,
    val rating: Double,
    val estimatedHours: Double,
    val isIndoor: Boolean = false,
    val lat: Double = 18.3273,
    val lng: Double = 82.8775
)

data class PriceComparisonItem(
    val id: String,
    val title: String,
    val category: String, // Hotel, Bus, Train, Flight, Cab, Attraction
    val provider: String, // MakeMyTrip, Booking.com, IRCTC, RedBus, Uber, Yatra
    val price: Int,
    val rating: Double,
    val distanceOrDuration: String,
    val availability: String,
    val status: PriceStatus,
    val recommendationType: RecommendationType = RecommendationType.BEST_VALUE,
    val matchReason: String = "Balanced price, high rating and central accessibility."
)

data class WeatherInfo(
    val location: String,
    val tempC: Int,
    val condition: String,
    val rainProbabilityPercent: Int,
    val humidityPercent: Int,
    val recommendation: String,
    val isLive: Boolean = true
)

data class NearbyPlace(
    val id: String,
    val name: String,
    val category: String, // Hospital, Pharmacy, Restaurant, ATM, Railway, Bus, Tourist Center
    val distanceKm: Double,
    val rating: Double,
    val address: String,
    val phone: String = "+91 1800-111-363",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)

data class ActivityItem(
    val id: String,
    val timeSlot: String,
    val title: String,
    val description: String,
    val location: String,
    val estimatedCost: Int,
    val durationHours: Double,
    val isIndoor: Boolean = false,
    val lat: Double = 0.0,
    val lng: Double = 0.0
)

data class DayPlan(
    val dayNumber: Int,
    val title: String,
    val theme: String,
    val activities: List<ActivityItem>,
    val estimatedCost: Int
)

data class BudgetBreakdown(
    val transportation: Int,
    val accommodation: Int,
    val food: Int,
    val attractions: Int,
    val localTravel: Int,
    val miscellaneous: Int
) {
    val total: Int get() = transportation + accommodation + food + attractions + localTravel + miscellaneous
}

data class OptimizationSuggestion(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val savingsAmount: Int,
    var isApplied: Boolean = false,
    val beforeText: String,
    val afterText: String
)

data class InformationSource(
    val documentName: String,
    val section: String,
    val lastUpdated: String,
    val category: String
)

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "assistant"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sources: List<InformationSource> = emptyList(),
    val isReplanningProposal: Boolean = false,
    val replanSummary: String? = null
)

data class TravelDocument(
    val id: String,
    val title: String,
    val format: String, // PDF, TXT, DOCX, CSV, JSON
    val category: String,
    val sizeKb: Int,
    val status: String, // Processed, Chunked, Embedded
    val chunkCount: Int,
    val lastUpdated: String
)

data class UserProfile(
    val userId: String = "user_in_01",
    val name: String = "Rajesh Sharma",
    val email: String = "rajesh.travel@example.com",
    val preferredLanguage: String = "English",
    val interests: List<String> = listOf("Mountains", "Historical", "Food & Culture"),
    val budgetPreference: String = "Moderate (₹3,000 - ₹5,000 / day)",
    val isAdmin: Boolean = false
)

data class TripPlan(
    val id: String,
    val title: String,
    val startingPoint: String,
    val destinationName: String,
    val stateName: String,
    val startDate: String,
    val endDate: String,
    val numberOfDays: Int,
    val numberOfTravelers: Int,
    val budget: Int,
    val currentEstimatedCost: Int,
    val interests: List<String>,
    val foodPref: String,
    val transportPref: String,
    val hotelPref: String,
    val dayPlans: List<DayPlan>,
    val isOptimized: Boolean = false,
    val distanceKm: Double = 85.0,
    val travelTimeMinutes: Int = 260,
    val localTransportCost: Int = 2100
)

