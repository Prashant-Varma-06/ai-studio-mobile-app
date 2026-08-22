package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.ui.screens.*
import com.example.viewmodel.TravelViewModel

data class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun AppNavigation(
    viewModel: TravelViewModel,
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val savedTrips by viewModel.savedTrips.collectAsStateWithLifecycle()
    val savedPlaces by viewModel.savedPlaces.collectAsStateWithLifecycle()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(Screen.Home.route, "Explore", Icons.Filled.Explore, Icons.Outlined.Explore, "nav_explore"),
        BottomNavItem(Screen.ExploreIndia.route, "Regions", Icons.Filled.Map, Icons.Outlined.Map, "nav_regions"),
        BottomNavItem(Screen.TripPlanner.route, "Plan Trip", Icons.Filled.AddLocationAlt, Icons.Outlined.AddLocationAlt, "nav_plan_trip"),
        BottomNavItem(Screen.PriceComparison.route, "Prices", Icons.Filled.PriceCheck, Icons.Outlined.PriceCheck, "nav_prices"),
        BottomNavItem(Screen.TravelAssistant.route, "Assistant", Icons.Filled.SmartToy, Icons.Outlined.SmartToy, "nav_assistant"),
        BottomNavItem(Screen.Profile.route, "Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile")
    )

    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = SophisticatedDarkSurface,
                    tonalElevation = 0.dp,
                    modifier = Modifier.drawBehind {
                        drawLine(
                            color = SophisticatedDarkBorder,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 10.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0A0A0A),
                                selectedTextColor = GoldPrimary,
                                indicatorColor = GoldPrimary,
                                unselectedIconColor = TextSlateMuted,
                                unselectedTextColor = TextSlateMuted
                            ),
                            modifier = Modifier.testTag(item.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onNavigateNext = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinishOnboarding = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Auth.route) {
                AuthScreen(
                    onAuthSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateToExplore = { navController.navigate(Screen.ExploreIndia.route) },
                    onNavigateToPlanTrip = { navController.navigate(Screen.TripPlanner.route) },
                    onNavigateToPriceComparison = { navController.navigate(Screen.PriceComparison.route) },
                    onNavigateToAssistant = { navController.navigate(Screen.TravelAssistant.route) },
                    onNavigateToOptimizer = { navController.navigate(Screen.SmartTripOptimizer.route) },
                    onNavigateToNearby = { navController.navigate(Screen.NearbyPlaces.route) },
                    onNavigateToWeather = { navController.navigate(Screen.Weather.route) },
                    onSelectRegion = { region ->
                        viewModel.selectRegion(region)
                        navController.navigate(Screen.StateDetails.route)
                    },
                    onSelectDestination = { dest ->
                        viewModel.selectDestination(dest)
                        navController.navigate(Screen.DestinationDetails.route)
                    }
                )
            }

            composable(Screen.ExploreIndia.route) {
                ExploreIndiaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onSelectRegion = { region ->
                        viewModel.selectRegion(region)
                        navController.navigate(Screen.StateDetails.route)
                    },
                    onSelectDestination = { dest ->
                        viewModel.selectDestination(dest)
                        navController.navigate(Screen.DestinationDetails.route)
                    }
                )
            }

            composable(Screen.StateDetails.route) {
                StateDetailsScreen(
                    region = uiState.selectedRegion,
                    onNavigateBack = { navController.popBackStack() },
                    onPlanTripHere = { destName ->
                        navController.navigate(Screen.TripPlanner.route)
                    },
                    onSelectDestination = { dest ->
                        viewModel.selectDestination(dest)
                        navController.navigate(Screen.DestinationDetails.route)
                    }
                )
            }

            composable(Screen.DestinationDetails.route) {
                DestinationDetailsScreen(
                    destination = uiState.selectedDestination,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onPlanTripHere = { destName ->
                        navController.navigate(Screen.TripPlanner.route)
                    },
                    onComparePrices = { navController.navigate(Screen.PriceComparison.route) },
                    onFindNearby = { navController.navigate(Screen.NearbyPlaces.route) }
                )
            }

            composable(Screen.TripPlanner.route) {
                TripPlannerScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    initialDestination = uiState.selectedDestination?.name,
                    onNavigateToItinerary = { navController.navigate(Screen.Itinerary.route) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Itinerary.route) {
                ItineraryScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToOptimizer = { navController.navigate(Screen.SmartTripOptimizer.route) },
                    onNavigateToPrices = { navController.navigate(Screen.PriceComparison.route) },
                    onNavigateToBudget = { navController.navigate(Screen.BudgetPlanner.route) },
                    onNavigateToMap = { navController.navigate(Screen.MapRoute.route) }
                )
            }

            composable(Screen.SmartTripOptimizer.route) {
                SmartTripOptimizerScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToItinerary = { navController.navigate(Screen.Itinerary.route) }
                )
            }

            composable(Screen.PriceComparison.route) {
                PriceComparisonScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.BudgetPlanner.route) {
                BudgetPlannerScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.TravelAssistant.route) {
                TravelAssistantScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.NearbyPlaces.route) {
                NearbyPlacesScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.MapRoute.route) {
                MapRouteScreen(
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Weather.route) {
                WeatherScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.SavedTrips.route) {
                SavedTripsScreen(
                    viewModel = viewModel,
                    savedTrips = savedTrips,
                    onNavigateBack = { navController.popBackStack() },
                    onOpenTrip = { tripEntity ->
                        navController.navigate(Screen.Itinerary.route)
                    }
                )
            }

            composable(Screen.SavedPlaces.route) {
                SavedPlacesScreen(
                    viewModel = viewModel,
                    savedPlaces = savedPlaces,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToSavedTrips = { navController.navigate(Screen.SavedTrips.route) },
                    onNavigateToSavedPlaces = { navController.navigate(Screen.SavedPlaces.route) },
                    onNavigateToAdmin = { navController.navigate(Screen.AdminKnowledge.route) }
                )
            }

            composable(Screen.AdminKnowledge.route) {
                AdminKnowledgeScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
