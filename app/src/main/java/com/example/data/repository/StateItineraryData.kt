package com.example.data.repository

import com.example.data.model.ActivityItem
import com.example.data.model.DayPlan

object StateItineraryData {

    fun getStateMetrics(stateName: String, startingPoint: String): Triple<Double, Int, Int> {
        val s = stateName.lowercase()
        val fromVizag = startingPoint.contains("vizag", true) || startingPoint.contains("visakhapatnam", true)

        return when {
            s.contains("kerala") -> if (fromVizag) Triple(1240.0, 220, 2800) else Triple(220.0, 180, 2400)
            s.contains("andhra") || s.contains("araku") -> if (fromVizag) Triple(85.0, 150, 1800) else Triple(380.0, 320, 2200)
            s.contains("goa") -> Triple(950.0, 190, 2600)
            s.contains("rajasthan") -> Triple(1400.0, 240, 2900)
            s.contains("himachal") || s.contains("manali") || s.contains("shimla") -> Triple(1650.0, 280, 3200)
            s.contains("kashmir") || s.contains("jammu") -> Triple(1850.0, 300, 3400)
            s.contains("ladakh") || s.contains("leh") -> Triple(2100.0, 340, 3800)
            s.contains("tamil nadu") || s.contains("ooty") -> Triple(890.0, 180, 2500)
            s.contains("karnataka") || s.contains("coorg") || s.contains("hampi") -> Triple(780.0, 170, 2400)
            s.contains("maharashtra") || s.contains("mumbai") -> Triple(920.0, 190, 2700)
            s.contains("west bengal") || s.contains("kolkata") || s.contains("darjeeling") -> Triple(620.0, 140, 2300)
            s.contains("delhi") -> Triple(1350.0, 210, 2500)
            s.contains("uttar pradesh") || s.contains("varanasi") || s.contains("agra") -> Triple(980.0, 190, 2400)
            s.contains("uttarakhand") || s.contains("rishikesh") || s.contains("nainital") -> Triple(1420.0, 250, 2800)
            s.contains("gujarat") || s.contains("kutch") -> Triple(1280.0, 230, 2700)
            s.contains("madhya pradesh") || s.contains("khajuraho") -> Triple(860.0, 180, 2300)
            s.contains("odisha") || s.contains("puri") -> Triple(410.0, 110, 1900)
            s.contains("punjab") || s.contains("amritsar") -> Triple(1520.0, 260, 2700)
            s.contains("telangana") || s.contains("hyderabad") -> Triple(580.0, 130, 2100)
            s.contains("assam") || s.contains("kaziranga") -> Triple(1150.0, 220, 2800)
            s.contains("meghalaya") || s.contains("shillong") -> Triple(1230.0, 230, 2900)
            s.contains("sikkim") || s.contains("gangtok") -> Triple(980.0, 200, 3100)
            s.contains("arunachal") || s.contains("tawang") -> Triple(1480.0, 290, 3500)
            s.contains("bihar") || s.contains("bodh gaya") -> Triple(820.0, 170, 2000)
            s.contains("chhattisgarh") || s.contains("bastar") -> Triple(480.0, 120, 1900)
            s.contains("jharkhand") || s.contains("ranchi") -> Triple(690.0, 150, 2000)
            s.contains("manipur") || s.contains("loktak") -> Triple(1550.0, 280, 3200)
            s.contains("mizoram") || s.contains("aizawl") -> Triple(1520.0, 280, 3100)
            s.contains("nagaland") || s.contains("kohima") -> Triple(1490.0, 270, 3200)
            s.contains("tripura") || s.contains("agartala") -> Triple(1290.0, 230, 2800)
            s.contains("haryana") || s.contains("kurukshetra") -> Triple(1380.0, 220, 2400)
            s.contains("andaman") -> Triple(1180.0, 210, 4200)
            s.contains("puducherry") -> Triple(760.0, 160, 2600)
            s.contains("chandigarh") -> Triple(1490.0, 240, 2500)
            s.contains("daman") || s.contains("diu") -> Triple(1180.0, 210, 2500)
            s.contains("lakshadweep") -> Triple(1350.0, 230, 4800)
            else -> Triple(450.0, 160, 2200)
        }
    }

    fun buildStateDayPlans(
        stateName: String,
        destinationName: String,
        startingPoint: String,
        days: Int,
        travelers: Int,
        foodPref: String,
        transportPref: String,
        hotelPref: String
    ): List<DayPlan> {
        val s = stateName.lowercase()
        val d = destinationName.lowercase()
        val plans = mutableListOf<DayPlan>()

        when {
            // 1. ANDHRA PRADESH / ARAKU / VIZAG
            s.contains("andhra") || d.contains("araku") || d.contains("vizag") || d.contains("visakhapatnam") || d.contains("tirupati") -> {
                // Day 1: Visakhapatnam Coastal City Highlights (6 places)
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Visakhapatnam Coastal Wonders & Naval Heritage",
                            theme = "Beaches, Submarine Museum & Hilltop Ropeway",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ap_1_1",
                                    timeSlot = "07:30 AM - 09:30 AM",
                                    title = "Ramakrishna (RK) Beach & Kali Temple Promenade",
                                    description = "Morning seaside walk along the Bay of Bengal, Kali Temple, and coastal statues.",
                                    location = "RK Beach Road, Vizag",
                                    estimatedCost = 0,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 17.7123,
                                    lng = 83.3182
                                ),
                                ActivityItem(
                                    id = "act_ap_1_2",
                                    timeSlot = "09:45 AM - 11:30 AM",
                                    title = "INS Kursura Submarine & TU 142 Aircraft Museum",
                                    description = "Tour inside South Asia's first submarine museum and Tupolev reconnaissance aircraft.",
                                    location = "Submarine Museum, Beach Road",
                                    estimatedCost = 120 * travelers,
                                    durationHours = 1.75,
                                    isIndoor = true,
                                    lat = 17.7169,
                                    lng = 83.3328
                                ),
                                ActivityItem(
                                    id = "act_ap_1_3",
                                    timeSlot = "12:00 PM - 01:30 PM",
                                    title = "Kailasagiri Hilltop Park & Cable Car Ropeway",
                                    description = "Ride scenic ropeway to 360-ft hilltop garden overlooking Vizag harbor and giant Shiva-Parvati monolith.",
                                    location = "Kailasagiri Hill, Vizag",
                                    estimatedCost = 100 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = false,
                                    lat = 17.7492,
                                    lng = 83.3422
                                ),
                                ActivityItem(
                                    id = "act_ap_1_4",
                                    timeSlot = "01:45 PM - 03:00 PM",
                                    title = "Traditional Andhra Thali & Royyala Iguru Lunch",
                                    description = "Authentic Andhra spicy prawns, Gongura pachadi, sambar, avakaya pickle, and curd rice.",
                                    location = "Daspalla Executive Diner, Vizag",
                                    estimatedCost = 350 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ap_1_5",
                                    timeSlot = "03:30 PM - 05:45 PM",
                                    title = "Tenneti Park Natural Sea Arch & Rishikonda Beach",
                                    description = "Explore natural rock cliff formations and relax on golden sands with jet skiing options.",
                                    location = "Rishikonda Beach, Vizag",
                                    estimatedCost = 80 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false,
                                    lat = 17.7828,
                                    lng = 83.3853
                                ),
                                ActivityItem(
                                    id = "act_ap_1_6",
                                    timeSlot = "06:30 PM - 08:30 PM",
                                    title = "Jagadamba Junction Bazaar & Pootharekulu Sweets",
                                    description = "Evening shopping for certified Andhra handlooms, wooden Kondapalli toys, and paper-sweet Pootharekulu.",
                                    location = "Jagadamba Center, Vizag",
                                    estimatedCost = 300,
                                    durationHours = 2.0,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5200 else 2800
                        )
                    )
                }

                // Day 2: Vistadome Train to Araku Valley, Borra Caves & Katiki (6 places)
                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Araku Valley Vistadome Train, Borra Caves & Waterfalls",
                            theme = "Eastern Ghats Mountain Pass & Geological Caverns",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ap_2_1",
                                    timeSlot = "06:45 AM - 10:15 AM",
                                    title = "Vistadome Glass-Dome Train Journey through 58 Tunnels",
                                    description = "Ride the iconic Vistadome coach (Train #58501) winding through mist-covered Ananthagiri mountain bridges.",
                                    location = "Vizag Railway Station to Borra Guhalu",
                                    estimatedCost = 350 * travelers,
                                    durationHours = 3.5,
                                    isIndoor = false,
                                    lat = 18.2798,
                                    lng = 83.0401
                                ),
                                ActivityItem(
                                    id = "act_ap_2_2",
                                    timeSlot = "10:30 AM - 12:45 PM",
                                    title = "Borra Caves 150-Million-Year Stalactite Formations",
                                    description = "Explore India's deepest limestone cavern illuminated with vibrant LED lights and natural Shiva Lingam rock.",
                                    location = "Borra Caves Complex, Ananthagiri",
                                    estimatedCost = 80 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = true,
                                    lat = 18.2804,
                                    lng = 83.0401
                                ),
                                ActivityItem(
                                    id = "act_ap_2_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Authentic Bamboo Chicken & Valley Thali Lunch",
                                    description = "Tender marinated chicken slow-cooked inside natural bamboo stalks over wood embers with zero oil.",
                                    location = "Valley Breeze Restaurant, Borra",
                                    estimatedCost = 320 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ap_2_4",
                                    timeSlot = "02:30 PM - 05:00 PM",
                                    title = "4x4 Jungle Jeep Safari to Katiki Waterfalls",
                                    description = "Off-road mountain trail through dense sal jungle and 20-min nature walk to 50-ft cascading waterfall pool.",
                                    location = "Katiki Waterfalls, Gosthani River",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 18.2912,
                                    lng = 83.0234
                                ),
                                ActivityItem(
                                    id = "act_ap_2_5",
                                    timeSlot = "05:30 PM - 07:00 PM",
                                    title = "Ananthagiri Hills Coffee Plantation & Viewpoint",
                                    description = "Walk through organic shade-grown Arabica coffee estates with pepper vines and orange orchards.",
                                    location = "Ananthagiri Coffee Estate",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = false,
                                    lat = 18.2432,
                                    lng = 83.0012
                                ),
                                ActivityItem(
                                    id = "act_ap_2_6",
                                    timeSlot = "07:30 PM - 09:30 PM",
                                    title = "Haritha Hill Resort Check-in & Tribal Dhimsa Dance",
                                    description = "Check in at $hotelPref, followed by campfire and live Dhimsa tribal folk dance performance.",
                                    location = "Haritha Hill Resort, Araku",
                                    estimatedCost = if (hotelPref.contains("Luxury", true)) 3800 else 1800,
                                    durationHours = 2.0,
                                    isIndoor = true
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5900 else 3200
                        )
                    )
                }

                // Day 3: Araku Valley Botanical & Cultural Discovery (5 places)
                if (days >= 3) {
                    plans.add(
                        DayPlan(
                            dayNumber = 3,
                            title = "Araku Botanical Gardens, Tribal Museum & Cascades",
                            theme = "Indigenous Culture & Forest Cascades",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ap_3_1",
                                    timeSlot = "08:30 AM - 10:30 AM",
                                    title = "Padmapuram Botanical Gardens & Tree Top Huts",
                                    description = "Historical WWII hanging cottages, rare exotic flora, rose nurseries, and charming toy train loop.",
                                    location = "Padmapuram Gardens, Araku",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 18.3245,
                                    lng = 82.8712
                                ),
                                ActivityItem(
                                    id = "act_ap_3_2",
                                    timeSlot = "10:45 AM - 12:30 PM",
                                    title = "Araku Tribal Heritage Museum & Artisan Crafts",
                                    description = "Explore mud huts, tribal archery dioramas, silver ornaments, and live pottery workshops.",
                                    location = "Tribal Museum, Araku Center",
                                    estimatedCost = 60 * travelers,
                                    durationHours = 1.75,
                                    isIndoor = true,
                                    lat = 18.3301,
                                    lng = 82.8722
                                ),
                                ActivityItem(
                                    id = "act_ap_3_3",
                                    timeSlot = "12:45 PM - 02:00 PM",
                                    title = "Araku Coffee Museum & Artisanal Brew Tasting",
                                    description = "Trace history of Araku coffee, sample freshly roasted single-origin espresso, and buy chocolate coffee beans.",
                                    location = "Araku Coffee House",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true,
                                    lat = 18.3312,
                                    lng = 82.8689
                                ),
                                ActivityItem(
                                    id = "act_ap_3_4",
                                    timeSlot = "02:30 PM - 05:00 PM",
                                    title = "Chaparai Natural Rock Water Cascades",
                                    description = "Wade through smooth natural stone rock bed with gently flowing clear mountain streams.",
                                    location = "Chaparai Cascades, Dumbriguda",
                                    estimatedCost = 40 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 18.3756,
                                    lng = 82.7845
                                ),
                                ActivityItem(
                                    id = "act_ap_3_5",
                                    timeSlot = "05:30 PM - 08:30 PM",
                                    title = "Galikonda Viewpoint & Return Transit to $startingPoint",
                                    description = "Catch highest mountain viewpoint sunset (3,800 ft) before scenic ghat descent back to $startingPoint.",
                                    location = "Galikonda Viewpoint, Araku Ghat",
                                    estimatedCost = if (transportPref.contains("Cab", true)) 1400 * travelers else 380 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 18.2612,
                                    lng = 82.9512
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 4800 else 2600
                        )
                    )
                }

                // Day 4: Tirupati Tirumala Sacred Heritage (5 places)
                if (days >= 4) {
                    plans.add(
                        DayPlan(
                            dayNumber = 4,
                            title = "Tirupati Tirumala Balaji & Seshachalam Hills",
                            theme = "Sacred Hill Shrines & Geological Wonders",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ap_4_1",
                                    timeSlot = "06:30 AM - 10:30 AM",
                                    title = "Sri Venkateswara Swamy Temple Tirumala Special Darshan",
                                    description = "Sacred darshan of Lord Balaji in the world's most visited holy shrine atop seven sacred hills.",
                                    location = "Tirumala Balaji Temple",
                                    estimatedCost = 300 * travelers,
                                    durationHours = 4.0,
                                    isIndoor = true,
                                    lat = 13.6833,
                                    lng = 79.3472
                                ),
                                ActivityItem(
                                    id = "act_ap_4_2",
                                    timeSlot = "11:00 AM - 12:30 PM",
                                    title = "Silathoranam Natural Rock Arch Geological Monument",
                                    description = "Rare 1,500-million-year-old natural arch rock formation found in only three places on Earth.",
                                    location = "Silathoranam, Tirumala",
                                    estimatedCost = 0,
                                    durationHours = 1.5,
                                    isIndoor = false,
                                    lat = 13.6912,
                                    lng = 79.3510
                                ),
                                ActivityItem(
                                    id = "act_ap_4_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Tirupati Laddu Prasadam & South Indian Banana Leaf Meal",
                                    description = "Relish the world-famous GI-tagged Tirupati Laddu and authentic Satvik temple meal.",
                                    location = "Sri Venkateswara Annaprasadam",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ap_4_4",
                                    timeSlot = "03:00 PM - 05:30 PM",
                                    title = "Kapila Theertham Holy Waterfall & Cave Shrine",
                                    description = "Only Shiva temple in Tirupati, situated at the foot of Seshachalam hills under a natural mountain waterfall.",
                                    location = "Kapila Theertham, Tirupati",
                                    estimatedCost = 20 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 13.6456,
                                    lng = 79.4210
                                ),
                                ActivityItem(
                                    id = "act_ap_4_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Chandragiri Historic Fort & Sound-and-Light Spectacle",
                                    description = "11th-century Vijayanagara fort with Raja Mahal palace and evening multimedia sound and light show.",
                                    location = "Chandragiri Fort, Tirupati",
                                    estimatedCost = 80 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 13.5833,
                                    lng = 79.3167
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 4900 else 2700
                        )
                    )
                }
            }

            // 2. KERALA
            s.contains("kerala") || d.contains("munnar") || d.contains("alleppey") || d.contains("kochi") -> {
                // Day 1: Fort Kochi Colonial & Art (5 places)
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Fort Kochi Colonial Coast, Chinese Nets & Kathakali",
                            theme = "Arabian Sea Coastline & Cultural Heritage",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ker_1_1",
                                    timeSlot = "08:00 AM - 10:30 AM",
                                    title = "Fort Kochi Promenade & Chinese Fishing Nets",
                                    description = "Watch fishermen operate 14th-century cantilevered fishing nets and explore Vasco da Gama Square.",
                                    location = "Fort Kochi Beach",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 9.9678,
                                    lng = 76.2404
                                ),
                                ActivityItem(
                                    id = "act_ker_1_2",
                                    timeSlot = "10:45 AM - 12:45 PM",
                                    title = "St. Francis Church & Mattancherry Dutch Palace",
                                    description = "Oldest European church in India (1503) and Dutch palace with exquisite Ramayana murals.",
                                    location = "Mattancherry, Kochi",
                                    estimatedCost = 60 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = true,
                                    lat = 9.9575,
                                    lng = 76.2592
                                ),
                                ActivityItem(
                                    id = "act_ker_1_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Jew Town Antique Walk & Spiced Seafood Lunch",
                                    description = "Visit 1568 Paradesi Synagogue, antique spice warehouses, and enjoy Appam with vegetable stew and fish curry.",
                                    location = "Jew Town, Mattancherry",
                                    estimatedCost = 380 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true,
                                    lat = 9.9572,
                                    lng = 76.2598
                                ),
                                ActivityItem(
                                    id = "act_ker_1_4",
                                    timeSlot = "03:00 PM - 05:00 PM",
                                    title = "Marine Drive Boating & Kochi Harbor Cruise",
                                    description = "Scenic sunset cruise across Kochi backwaters, Bolgatty Palace, and Vallarpadam container port.",
                                    location = "Marine Drive Jetty, Ernakulam",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 9.9812,
                                    lng = 76.2753
                                ),
                                ActivityItem(
                                    id = "act_ker_1_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Live Kathakali Classical Drama & Kalaripayattu Show",
                                    description = "Witness elaborate facial makeup demonstration and classical Kathakali dance storytelling followed by martial arts.",
                                    location = "Kerala Kathakali Centre",
                                    estimatedCost = 300 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 9.9641,
                                    lng = 76.2435
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6200 else 3400
                        )
                    )
                }

                // Day 2: Munnar Tea Plantations, Eravikulam & Waterfalls (5 places)
                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Munnar Tea Plantations, Eravikulam & Waterfalls",
                            theme = "Western Ghats Mountain Hills & Tea Trails",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ker_2_1",
                                    timeSlot = "07:30 AM - 10:00 AM",
                                    title = "Cheeyappara & Valara 7-Tier Waterfalls",
                                    description = "Scenic mountain stop along NH85 with cascading waterfalls and roadside fresh pineapple slices.",
                                    location = "Cheeyappara Falls, Munnar Road",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 10.0156,
                                    lng = 76.8921
                                ),
                                ActivityItem(
                                    id = "act_ker_2_2",
                                    timeSlot = "10:30 AM - 01:00 PM",
                                    title = "Eravikulam National Park (Nilgiri Tahr & Anamudi)",
                                    description = "Board eco-bus safari to spot endangered Nilgiri Tahr mountain goats near South India's highest peak (8,842 ft).",
                                    location = "Eravikulam National Park",
                                    estimatedCost = 220 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 10.1512,
                                    lng = 77.0610
                                ),
                                ActivityItem(
                                    id = "act_ker_2_3",
                                    timeSlot = "01:30 PM - 03:00 PM",
                                    title = "Tata Tea Museum & Tea Processing Masterclass",
                                    description = "Learn century-old artisanal orthodox tea manufacturing and sample premium cardamom tea.",
                                    location = "KDHP Tea Museum, Munnar",
                                    estimatedCost = 125 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = true,
                                    lat = 10.0889,
                                    lng = 77.0592
                                ),
                                ActivityItem(
                                    id = "act_ker_2_4",
                                    timeSlot = "03:30 PM - 05:30 PM",
                                    title = "Mattupetty Dam Speedboating & Echo Point",
                                    description = "Speedboating on emerald reservoir with misty tea slopes and natural voice echo phenomenon.",
                                    location = "Mattupetty Dam & Echo Point",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 10.1062,
                                    lng = 77.1245
                                ),
                                ActivityItem(
                                    id = "act_ker_2_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Top Station Cloudline Sunset & Munnar Spice Market",
                                    description = "Panoramic sunset over Western Ghats border followed by buying certified fresh green cardamom and vanilla pods.",
                                    location = "Munnar Spice Market",
                                    estimatedCost = 300,
                                    durationHours = 2.5,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5900 else 3200
                        )
                    )
                }

                // Day 3: Alleppey Backwaters & Houseboat Cruise (5 places)
                if (days >= 3) {
                    plans.add(
                        DayPlan(
                            dayNumber = 3,
                            title = "Alleppey Backwaters, Houseboat & Kuttanad",
                            theme = "Venice of the East & Floating Canal Life",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ker_3_1",
                                    timeSlot = "08:30 AM - 11:30 AM",
                                    title = "Scenic Transit to Alleppey Punnamada Jetty",
                                    description = "Drive through rubber and spice plantations down to the famous backwaters of Alappuzha.",
                                    location = "Punnamada Jetty, Alleppey",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 9.5012,
                                    lng = 76.3421
                                ),
                                ActivityItem(
                                    id = "act_ker_3_2",
                                    timeSlot = "12:00 PM - 02:00 PM",
                                    title = "Private Kettuvallam Houseboat Cruise on Vembanad Lake",
                                    description = "Board luxury wooden houseboat gliding past coconut palms, water lilies, and below-sea-level paddy fields.",
                                    location = "Vembanad Lake Backwaters",
                                    estimatedCost = 1500 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = true,
                                    lat = 9.5821,
                                    lng = 76.4123
                                ),
                                ActivityItem(
                                    id = "act_ker_3_3",
                                    timeSlot = "02:00 PM - 03:15 PM",
                                    title = "Traditional Kerala Sadya on Banana Leaf with Karimeen",
                                    description = "Multi-course feast served onboard featuring red rice, avial, sambar, payasam, and pearl spot fish fry.",
                                    location = "Houseboat Dining Deck",
                                    estimatedCost = 450 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ker_3_4",
                                    timeSlot = "03:45 PM - 05:45 PM",
                                    title = "Kuttanad Village Shikara Canoe Tour",
                                    description = "Narrow canal canoe ride to witness duck farming, toddy tapping, and coir yarn making.",
                                    location = "Kuttanad Waterways",
                                    estimatedCost = 300 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false
                                ),
                                ActivityItem(
                                    id = "act_ker_3_5",
                                    timeSlot = "06:15 PM - 08:30 PM",
                                    title = "Marari Beach Sunset Walk & Beachside Seafood Dinner",
                                    description = "Quiet white sand beach walk under swaying palms with fresh grilled prawns and lemon tea.",
                                    location = "Marari Beach Promenade",
                                    estimatedCost = 400 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false,
                                    lat = 9.6012,
                                    lng = 76.2987
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6800 else 3900
                        )
                    )
                }

                // Day 4: Thekkady Periyar Wildlife & Spices (5 places)
                if (days >= 4) {
                    plans.add(
                        DayPlan(
                            dayNumber = 4,
                            title = "Thekkady Periyar Wildlife, Spices & Ayurveda",
                            theme = "Cardamom Hills & Wildlife Sanctuary",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ker_4_1",
                                    timeSlot = "07:00 AM - 10:00 AM",
                                    title = "Periyar Tiger Reserve Boat Safari on Lake",
                                    description = "Morning boat safari spotting wild elephants, Indian gaur (bison), sambar deer, and pied hornbills.",
                                    location = "Periyar Tiger Reserve, Thekkady",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 9.4621,
                                    lng = 77.1420
                                ),
                                ActivityItem(
                                    id = "act_ker_4_2",
                                    timeSlot = "10:30 AM - 12:30 PM",
                                    title = "Organic Spice Plantation Guided Walking Tour",
                                    description = "Walk through aromatic plantations of black pepper, cinnamon, nutmeg, cloves, and allspice trees.",
                                    location = "Thekkady Organic Spice Garden",
                                    estimatedCost = 100 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 9.6010,
                                    lng = 77.1680
                                ),
                                ActivityItem(
                                    id = "act_ker_4_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Kerala Syrian Christian Stew & Appam Lunch",
                                    description = "Feast on soft lacy fermented rice pancakes with coconut milk vegetable stew and spiced beef/chicken roast.",
                                    location = "Spice Garden Heritage Restaurant",
                                    estimatedCost = 350 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ker_4_4",
                                    timeSlot = "03:00 PM - 05:15 PM",
                                    title = "Authentic Ayurvedic Abhyanga Massage Therapy",
                                    description = "Traditional 60-min herbal oil full-body wellness massage by certified therapists for complete relaxation.",
                                    location = "Kerala Ayurvedic Centre, Thekkady",
                                    estimatedCost = 850 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ker_4_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Kadathanadan Kalari Centre Martial Arts Arena",
                                    description = "Thrilling live demonstration of swords, shields, Urumi flexible blade, and leaping acrobatics.",
                                    location = "Kadathanadan Kalari Arena",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5400 else 3200
                        )
                    )
                }

                // Day 5: Kovalam & Trivandrum Padmanabhaswamy (5 places)
                if (days >= 5) {
                    plans.add(
                        DayPlan(
                            dayNumber = 5,
                            title = "Kovalam Beach & Trivandrum Padmanabhaswamy Temple",
                            theme = "Grand Dravidian Architecture & Ocean Sunset",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ker_5_1",
                                    timeSlot = "07:30 AM - 10:00 AM",
                                    title = "Sree Padmanabhaswamy Temple Sacred Heritage Darshan",
                                    description = "Visit the world's wealthiest temple shrine with 16th-century Dravidian gopuram and 365 granite pillars.",
                                    location = "Padmanabhaswamy Temple, Trivandrum",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 8.4828,
                                    lng = 76.9436
                                ),
                                ActivityItem(
                                    id = "act_ker_5_2",
                                    timeSlot = "10:30 AM - 12:30 PM",
                                    title = "Napier Museum & Raja Ravi Varma Art Gallery",
                                    description = "Admire Indo-Saracenic wooden architecture, ancient bronze artifacts, and original paintings.",
                                    location = "Napier Museum Grounds, Trivandrum",
                                    estimatedCost = 60 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = true,
                                    lat = 8.5089,
                                    lng = 76.9554
                                ),
                                ActivityItem(
                                    id = "act_ker_5_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Trivandrum Malabar Biryani & Pazham Pori Lunch",
                                    description = "Fragrant Jeerakasala rice biryani served with date pickle, raita, and sweet banana fritters.",
                                    location = "Mothers Veg Plaza / Paragon Trivandrum",
                                    estimatedCost = 320 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ker_5_4",
                                    timeSlot = "03:30 PM - 06:30 PM",
                                    title = "Kovalam Lighthouse Beach & 30m Tower Climb",
                                    description = "Climb the red-striped 30m lighthouse for 360-degree views of crescent beach and Arabian sunset.",
                                    location = "Kovalam Lighthouse Beach",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 8.3856,
                                    lng = 76.9782
                                ),
                                ActivityItem(
                                    id = "act_ker_5_5",
                                    timeSlot = "07:30 PM - 09:30 PM",
                                    title = "Return Transit to $startingPoint",
                                    description = "Transfer to Trivandrum / Kochi Airport or Railway Station for connecting transit back to $startingPoint.",
                                    location = "Trivandrum Central Station / Airport",
                                    estimatedCost = if (transportPref.contains("Flight", true)) 2400 * travelers else 750 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 4900 else 2900
                        )
                    )
                }
            }

            // 3. GOA
            s.contains("goa") -> {
                // Day 1: North Goa Forts, Beaches & Night Market (6 places)
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "North Goa Forts, Golden Beaches & Night Market",
                            theme = "Portuguese Bastions & Arabian Sea Watersports",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_goa_1_1",
                                    timeSlot = "08:30 AM - 10:30 AM",
                                    title = "Fort Aguada & 17th Century Portuguese Lighthouse",
                                    description = "Explore 1612 coastal fortress guarding the mouth of Mandovi River with freshwater spring reservoirs.",
                                    location = "Fort Aguada, Candolim",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 15.4925,
                                    lng = 73.7736
                                ),
                                ActivityItem(
                                    id = "act_goa_1_2",
                                    timeSlot = "10:45 AM - 01:00 PM",
                                    title = "Sinquerim & Calangute Beach Watersports",
                                    description = "Parasailing, jet ski rides, and banana boat rides over azure Arabian Sea waters.",
                                    location = "Calangute Beach Watersports Hub",
                                    estimatedCost = 600 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false,
                                    lat = 15.5430,
                                    lng = 73.7550
                                ),
                                ActivityItem(
                                    id = "act_goa_1_3",
                                    timeSlot = "01:15 PM - 02:30 PM",
                                    title = "Authentic Goan Fish Curry Thali & Poi Bread",
                                    description = "Kingfish fry with coconut curry, sol kadhi, dry prawn kishmoor, and fresh hot Goan poi.",
                                    location = "Fisherman's Wharf / Souza Lobo",
                                    estimatedCost = 420 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_goa_1_4",
                                    timeSlot = "03:00 PM - 05:00 PM",
                                    title = "Anjuna Beach Red Laterite Cliffs & Curlies Cove",
                                    description = "Walk past scenic red cliffs, beach shacks, and vibrant bohemian art stalls.",
                                    location = "Anjuna Beach Cliffs",
                                    estimatedCost = 0,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 15.5833,
                                    lng = 73.7410
                                ),
                                ActivityItem(
                                    id = "act_goa_1_5",
                                    timeSlot = "05:30 PM - 07:15 PM",
                                    title = "Sunset at Chapora Fort ('Dil Chahta Hai' Point)",
                                    description = "Hike up to the ancient red fort ramparts overlooking Vagator beach and sunset horizon.",
                                    location = "Chapora Fort, Vagator",
                                    estimatedCost = 0,
                                    durationHours = 1.75,
                                    isIndoor = false,
                                    lat = 15.6062,
                                    lng = 73.7389
                                ),
                                ActivityItem(
                                    id = "act_goa_1_6",
                                    timeSlot = "07:45 PM - 09:45 PM",
                                    title = "Arpora Saturday Night Bazaar & Live Music",
                                    description = "Live DJ, indie fashion designers, handmade leather goods, and international food stalls.",
                                    location = "Arpora Night Market",
                                    estimatedCost = 350,
                                    durationHours = 2.0,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6400 else 3500
                        )
                    )
                }

                // Day 2: Old Goa UNESCO Cathedrals & Latin Quarter (5 places)
                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Old Goa Cathedrals, Latin Quarter & Sunset Cruise",
                            theme = "UNESCO World Heritage & Portuguese Architecture",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_goa_2_1",
                                    timeSlot = "08:30 AM - 11:00 AM",
                                    title = "Basilica of Bom Jesus & Relics of St. Francis Xavier",
                                    description = "Baroque 1605 UNESCO basilica housing the sacred mortal remains of St. Francis Xavier.",
                                    location = "Old Goa World Heritage Complex",
                                    estimatedCost = 0,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 15.5009,
                                    lng = 73.9116
                                ),
                                ActivityItem(
                                    id = "act_goa_2_2",
                                    timeSlot = "11:15 AM - 12:45 PM",
                                    title = "Se Cathedral & Church of St. Francis of Assisi",
                                    description = "Largest church in Asia featuring the miraculous Golden Bell and Portuguese Manueline carvings.",
                                    location = "Se Cathedral, Old Goa",
                                    estimatedCost = 20 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = true,
                                    lat = 15.5036,
                                    lng = 73.9130
                                ),
                                ActivityItem(
                                    id = "act_goa_2_3",
                                    timeSlot = "01:00 PM - 02:30 PM",
                                    title = "Fontainhas Latin Quarter Heritage Walk & Cafe Alvorada",
                                    description = "Stroll past vivid yellow, blue, and terracotta Portuguese heritage villas and sample Bebinca pastry.",
                                    location = "Fontainhas, Panaji",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = false,
                                    lat = 15.4989,
                                    lng = 73.8312
                                ),
                                ActivityItem(
                                    id = "act_goa_2_4",
                                    timeSlot = "03:30 PM - 05:30 PM",
                                    title = "Mangueshi Temple 7-Tier Deepastambha & Spice Garden",
                                    description = "400-year-old temple dedicated to Lord Shiva with ornate silver doors and 7-tier lamp tower.",
                                    location = "Mangueshi Temple, Ponda",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = true,
                                    lat = 15.4328,
                                    lng = 73.9682
                                ),
                                ActivityItem(
                                    id = "act_goa_2_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Mandovi River Luxury Sunset Cruise & Folk Dance",
                                    description = "2-hour river cruise with live Goan Dekhni folk dance, Portuguese singing, and sunset skyline.",
                                    location = "Santa Monica Jetty, Panaji",
                                    estimatedCost = 450 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 15.4980,
                                    lng = 73.8260
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5800 else 3100
                        )
                    )
                }

                // Day 3: Dudhsagar Waterfalls & South Goa (5 places)
                if (days >= 3) {
                    plans.add(
                        DayPlan(
                            dayNumber = 3,
                            title = "Dudhsagar Waterfalls & South Goa Pristine Coves",
                            theme = "4-Tier Cascades & White Sand Lagoons",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_goa_3_1",
                                    timeSlot = "07:00 AM - 11:30 AM",
                                    title = "Dudhsagar 4-Tier Waterfalls Jungle Jeep Trek",
                                    description = "Ride 4x4 jungle jeep across Bhagwan Mahavir Wildlife Sanctuary to India's 5th tallest waterfall (1,017 ft).",
                                    location = "Dudhsagar Falls, Sonaulim",
                                    estimatedCost = 550 * travelers,
                                    durationHours = 4.5,
                                    isIndoor = false,
                                    lat = 15.3144,
                                    lng = 74.3143
                                ),
                                ActivityItem(
                                    id = "act_goa_3_2",
                                    timeSlot = "12:00 PM - 02:00 PM",
                                    title = "Sahakari Organic Spice Plantation & Traditional Buffet",
                                    description = "Guided spice trail smelling fresh vanilla, nutmeg, and cinnamon, with unlimited Goan village buffet.",
                                    location = "Sahakari Spice Farm, Curti",
                                    estimatedCost = 400 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 15.4120,
                                    lng = 74.0210
                                ),
                                ActivityItem(
                                    id = "act_goa_3_3",
                                    timeSlot = "03:00 PM - 05:00 PM",
                                    title = "Palolem Crescent Beach Kayaking & Dolphin Spotting",
                                    description = "Paddle along calm horseshoe bay and watch wild Indo-Pacific humpback dolphins.",
                                    location = "Palolem Beach, South Goa",
                                    estimatedCost = 300 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 15.0100,
                                    lng = 74.0232
                                ),
                                ActivityItem(
                                    id = "act_goa_3_4",
                                    timeSlot = "05:30 PM - 07:15 PM",
                                    title = "Cabo de Rama Historic Fortress Sunset Panorama",
                                    description = "Ancient cliffside citadel with panoramic views of the entire South Goan coastline.",
                                    location = "Cabo de Rama Fort, Canacona",
                                    estimatedCost = 0,
                                    durationHours = 1.75,
                                    isIndoor = false,
                                    lat = 15.0880,
                                    lng = 73.9215
                                ),
                                ActivityItem(
                                    id = "act_goa_3_5",
                                    timeSlot = "07:45 PM - 09:30 PM",
                                    title = "Candlelight Beach Shack Dinner & Return Transfer",
                                    description = "Beachside dining under tiki torches listening to ocean waves before return transfer.",
                                    location = "Palolem Beach Shacks",
                                    estimatedCost = 450 * travelers,
                                    durationHours = 1.75,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6100 else 3300
                        )
                    )
                }
            }

            // 4. RAJASTHAN
            s.contains("rajasthan") || d.contains("jaipur") || d.contains("udaipur") || d.contains("jodhpur") || d.contains("jaisalmer") -> {
                // Day 1: Jaipur Pink City & Forts (5 places)
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Jaipur Royal Palaces, Amber Fort & Stepwell",
                            theme = "Rajput Fortresses & Architectural Splendor",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_raj_1_1",
                                    timeSlot = "08:30 AM - 11:30 AM",
                                    title = "Amber Fort & Sheesh Mahal (Palace of Mirrors)",
                                    description = "Marvel at Rajput hill palace with thousand-mirror ceiling reflection and panoramic Maota Lake view.",
                                    location = "Amer Fort Complex, Jaipur",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 26.9855,
                                    lng = 75.8513
                                ),
                                ActivityItem(
                                    id = "act_raj_1_2",
                                    timeSlot = "11:45 AM - 01:00 PM",
                                    title = "Panna Meena ka Kund Geometric Stepwell & Jal Mahal",
                                    description = "16th-century symmetrical stepwell and stop for photography of the water palace Jal Mahal.",
                                    location = "Amer Stepwell & Jal Mahal",
                                    estimatedCost = 0,
                                    durationHours = 1.25,
                                    isIndoor = false,
                                    lat = 26.9880,
                                    lng = 75.8540
                                ),
                                ActivityItem(
                                    id = "act_raj_1_3",
                                    timeSlot = "01:15 PM - 02:30 PM",
                                    title = "Authentic Rajasthani Dal Baati Churma Feast",
                                    description = "Crispy baked wheat baatis dipped in desi ghee with five-lentil dal, gatte ki sabzi, and sweet churma.",
                                    location = "LMB Heritage Diner, Johari Bazaar",
                                    estimatedCost = 420 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_raj_1_4",
                                    timeSlot = "03:00 PM - 05:30 PM",
                                    title = "Hawa Mahal (953 Windows) & City Palace of Jaipur",
                                    description = "Iconic honeycomb pink facade and grand Chandra Mahal royal pavilions with weapons museum.",
                                    location = "City Palace Complex, Jaipur",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 26.9239,
                                    lng = 75.8267
                                ),
                                ActivityItem(
                                    id = "act_raj_1_5",
                                    timeSlot = "06:00 PM - 09:30 PM",
                                    title = "Chokhi Dhani Ethnic Cultural Village & Folk Dance",
                                    description = "Puppet shows, Kalbelia fire dancers, camel rides, and traditional royal floor thali dining.",
                                    location = "Chokhi Dhani Resort, Tonk Road",
                                    estimatedCost = 800 * travelers,
                                    durationHours = 3.5,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6500 else 3600
                        )
                    )
                }

                // Day 2: Jodhpur Blue City & Mehrangarh (5 places)
                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Jodhpur Blue City, Mehrangarh Fort & Stepwells",
                            theme = "Cliffside Citadels & Cobalt Blue Alleys",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_raj_2_1",
                                    timeSlot = "08:30 AM - 12:00 PM",
                                    title = "Mehrangarh Fort & Flying Fox Zipline Tour",
                                    description = "Explore 400-ft cliff fortress with cannon bastions, royal palanquins, and zipline over desert moats.",
                                    location = "Mehrangarh Fort, Jodhpur",
                                    estimatedCost = 350 * travelers,
                                    durationHours = 3.5,
                                    isIndoor = false,
                                    lat = 26.2980,
                                    lng = 73.0189
                                ),
                                ActivityItem(
                                    id = "act_raj_2_2",
                                    timeSlot = "12:15 PM - 01:30 PM",
                                    title = "Jaswant Thada White Marble Royal Cenotaphs",
                                    description = "Intricate carved marble sheets that glow warmly in sunlight, nestled next to a peaceful lake.",
                                    location = "Jaswant Thada, Jodhpur",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = false,
                                    lat = 26.3039,
                                    lng = 73.0245
                                ),
                                ActivityItem(
                                    id = "act_raj_2_3",
                                    timeSlot = "01:45 PM - 03:00 PM",
                                    title = "Jodhpur Pyaaz Kachori & Mirchi Vada Trail",
                                    description = "Tasting famous spicy onion kachoris, crispy mirchi vadas, and thick creamy Makhaniya lassi.",
                                    location = "Janta Sweet Home / Clock Tower",
                                    estimatedCost = 180 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_raj_2_4",
                                    timeSlot = "03:30 PM - 05:30 PM",
                                    title = "Toorji Ka Jhalra Stepwell & Navchowkiya Blue Alleys",
                                    description = "Ancient 1740s stepped water reservoir and guided photography walk through indigo-painted Brahmin streets.",
                                    location = "Toorji Ka Jhalra, Jodhpur",
                                    estimatedCost = 0,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 26.2970,
                                    lng = 73.0230
                                ),
                                ActivityItem(
                                    id = "act_raj_2_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Umaid Bhawan Palace & Vintage Car Museum",
                                    description = "Golden Chittar sandstone royal palace, private museum, and Maharaja's vintage automobile collection.",
                                    location = "Umaid Bhawan Palace",
                                    estimatedCost = 100 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 26.2809,
                                    lng = 73.0475
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6200 else 3400
                        )
                    )
                }

                // Day 3: Udaipur City of Lakes & Sunset Boat (5 places)
                if (days >= 3) {
                    plans.add(
                        DayPlan(
                            dayNumber = 3,
                            title = "Udaipur City of Lakes, Lake Pichola & Palaces",
                            theme = "Venice of the East & Floating Marble Islands",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_raj_3_1",
                                    timeSlot = "09:00 AM - 12:30 PM",
                                    title = "Udaipur City Palace & Crystal Gallery",
                                    description = "Rajasthan's largest royal palace complex overlooking Lake Pichola with mirrored courtyards and mosaic peacocks.",
                                    location = "City Palace Complex, Udaipur",
                                    estimatedCost = 300 * travelers,
                                    durationHours = 3.5,
                                    isIndoor = true,
                                    lat = 24.5764,
                                    lng = 73.6835
                                ),
                                ActivityItem(
                                    id = "act_raj_3_2",
                                    timeSlot = "12:45 PM - 02:00 PM",
                                    title = "Jagdish Temple & Lakeside Rajasthani Lunch",
                                    description = "1651 Indo-Aryan temple with carved stone elephants followed by lunch overlooking the lake ghats.",
                                    location = "Jagdish Chowk, Udaipur",
                                    estimatedCost = 380 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true,
                                    lat = 24.5794,
                                    lng = 73.6841
                                ),
                                ActivityItem(
                                    id = "act_raj_3_3",
                                    timeSlot = "02:30 PM - 04:30 PM",
                                    title = "Saheliyon-ki-Bari Fountains & Royal Rose Garden",
                                    description = "Historic garden crafted for 48 royal maidens featuring marble elephant fountains and lotus pools.",
                                    location = "Saheliyon-ki-Bari, Udaipur",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 24.6044,
                                    lng = 73.6845
                                ),
                                ActivityItem(
                                    id = "act_raj_3_4",
                                    timeSlot = "05:00 PM - 06:45 PM",
                                    title = "Lake Pichola Sunset Boat Cruise to Jag Mandir Island",
                                    description = "Romantic boat ride past Taj Lake Palace to the 17th-century marble palace on Jag Mandir island.",
                                    location = "Rameshwar Ghat Jetty",
                                    estimatedCost = 450 * travelers,
                                    durationHours = 1.75,
                                    isIndoor = false,
                                    lat = 24.5710,
                                    lng = 73.6790
                                ),
                                ActivityItem(
                                    id = "act_raj_3_5",
                                    timeSlot = "07:00 PM - 08:45 PM",
                                    title = "Bagore Ki Haveli Dharohar Rajasthani Folk & Puppet Show",
                                    description = "18th-century waterfront mansion hosting 9-pot balance dance, puppetry, and Ghoomar performance.",
                                    location = "Gangaur Ghat, Udaipur",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 1.75,
                                    isIndoor = true,
                                    lat = 24.5795,
                                    lng = 73.6800
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6700 else 3500
                        )
                    )
                }
            }

            // 5. HIMACHAL PRADESH
            s.contains("himachal") || d.contains("manali") || d.contains("shimla") || d.contains("dharamshala") -> {
                // Day 1: Manali Cedar Forests & Solang Valley (5 places)
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Manali Cedar Woods, Solang Valley & Hot Springs",
                            theme = "Himalayan Valleys & Adventure Sports",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_hp_1_1",
                                    timeSlot = "08:30 AM - 10:30 AM",
                                    title = "Hadimba Devi Cedar Forest Temple",
                                    description = "Ancient 1553 wooden pagoda temple nestled among towering deodar cedar trees in Dhungri forest.",
                                    location = "Hadimba Temple, Manali",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 32.2483,
                                    lng = 77.1695
                                ),
                                ActivityItem(
                                    id = "act_hp_1_2",
                                    timeSlot = "11:00 AM - 02:00 PM",
                                    title = "Solang Valley Paragliding & Zipline Arena",
                                    description = "High-altitude valley offering tandem paragliding, ATV rides, and zorbing with snow peak vistas.",
                                    location = "Solang Valley, Manali",
                                    estimatedCost = 800 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 32.3167,
                                    lng = 77.1583
                                ),
                                ActivityItem(
                                    id = "act_hp_1_3",
                                    timeSlot = "02:15 PM - 03:30 PM",
                                    title = "Traditional Himachali Siddu & Trout Fish Lunch",
                                    description = "Steamed wheat bread stuffed with poppy seeds and walnut paste, with grilled Beas river trout.",
                                    location = "Old Manali Cafe Trail",
                                    estimatedCost = 350 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_hp_1_4",
                                    timeSlot = "04:00 PM - 06:00 PM",
                                    title = "Vashisht Natural Sulphur Hot Springs & Jogini Falls Trek",
                                    description = "Dip in 4,000-year-old hot mineral springs and gentle 30-min trek to Jogini waterfall cascade.",
                                    location = "Vashisht Village, Manali",
                                    estimatedCost = 0,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 32.2639,
                                    lng = 77.1878
                                ),
                                ActivityItem(
                                    id = "act_hp_1_5",
                                    timeSlot = "06:30 PM - 09:00 PM",
                                    title = "Mall Road Manali & Tibetan Monastery Walk",
                                    description = "Shop for authentic Kullu shawls, wooden crafts, dry fruits, and visit Gadhan Thekchhokling Gompa.",
                                    location = "Mall Road, Manali",
                                    estimatedCost = 300,
                                    durationHours = 2.5,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5800 else 3200
                        )
                    )
                }

                // Day 2: Atal Tunnel & Lahaul Valley Sissu (5 places)
                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Atal Tunnel (10,000 ft), Sissu Waterfall & Lahaul",
                            theme = "Trans-Himalayan Crossing & Glacial Peaks",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_hp_2_1",
                                    timeSlot = "08:00 AM - 10:00 AM",
                                    title = "Drive through Atal Tunnel (World's Longest Above 10,000 ft)",
                                    description = "Drive through 9.02 km engineering wonder connecting lush Kullu to barren Lahaul valley.",
                                    location = "Atal Tunnel North Portal",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 32.4410,
                                    lng = 77.1640
                                ),
                                ActivityItem(
                                    id = "act_hp_2_2",
                                    timeSlot = "10:15 AM - 12:30 PM",
                                    title = "Sissu Glacial Waterfall & Chandra River Suspension Bridge",
                                    description = "Cross the suspension wooden bridge to stand beneath the roaring glacial waterfall in Sissu.",
                                    location = "Sissu Waterfall, Lahaul Valley",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false,
                                    lat = 32.4745,
                                    lng = 77.1235
                                ),
                                ActivityItem(
                                    id = "act_hp_2_3",
                                    timeSlot = "12:45 PM - 02:00 PM",
                                    title = "Lahauli Thukpa, Momos & Butter Tea Lunch",
                                    description = "Warm up with piping hot Tibetan noodles soup, steamed momos, and traditional salty butter tea.",
                                    location = "Sissu Riverside Camp Cafe",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_hp_2_4",
                                    timeSlot = "02:30 PM - 05:00 PM",
                                    title = "Gondhla Historic 8-Story Wooden Fort Tower",
                                    description = "Explore 300-year-old royal tower fort built of timber and stone overlooking snowcapped glaciers.",
                                    location = "Gondhla Castle, Lahaul",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 32.5020,
                                    lng = 77.0180
                                ),
                                ActivityItem(
                                    id = "act_hp_2_5",
                                    timeSlot = "05:30 PM - 08:30 PM",
                                    title = "Solang Ropeway Viewpoint Sunset & Bonfire Dinner",
                                    description = "Ride the gondola to 10,500 ft for sunset over snow peaks, followed by bonfire dinner at hotel.",
                                    location = "Solang Ropeway Summit",
                                    estimatedCost = 450 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 6100 else 3400
                        )
                    )
                }
            }

            // 6. UTTAR PRADESH
            s.contains("uttar pradesh") || d.contains("varanasi") || d.contains("agra") || d.contains("lucknow") || d.contains("ayodhya") -> {
                // Day 1: Agra Taj Mahal & Fort (5 places)
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Agra Taj Mahal Sunrise, Red Fort & Mehtab Bagh",
                            theme = "Mughal Architecture & UNESCO World Wonder",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_up_1_1",
                                    timeSlot = "06:00 AM - 09:30 AM",
                                    title = "Taj Mahal Sunrise Tour (Wonder of the World)",
                                    description = "Witness white Makrana marble glow in morning light and explore intricate Pietra Dura stone inlay.",
                                    location = "Taj Mahal Complex, Agra",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 3.5,
                                    isIndoor = false,
                                    lat = 27.1751,
                                    lng = 78.0421
                                ),
                                ActivityItem(
                                    id = "act_up_1_2",
                                    timeSlot = "10:00 AM - 12:30 PM",
                                    title = "Agra Fort & Diwan-i-Khas Royal Chambers",
                                    description = "16th-century red sandstone fortress where Shah Jahan was held captive overlooking the Taj Mahal.",
                                    location = "Agra Fort, Rakabganj",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 27.1795,
                                    lng = 78.0211
                                ),
                                ActivityItem(
                                    id = "act_up_1_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Mughlai Bedmi Puri, Kachori & Agra Petha Tasting",
                                    description = "Spicy lentil puris with aloo sabzi, Jalebi, and tasting world-famous varieties of Agra Petha sweets.",
                                    location = "Deviram Sweets / Panchhi Petha",
                                    estimatedCost = 220 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_up_1_4",
                                    timeSlot = "03:00 PM - 04:45 PM",
                                    title = "Itmad-ud-Daulah (Baby Taj Marble Jewel Box)",
                                    description = "First Mughal structure built completely from white marble with delicate lattice screens.",
                                    location = "Itmad-ud-Daulah, Yamuna Bank",
                                    estimatedCost = 80 * travelers,
                                    durationHours = 1.75,
                                    isIndoor = true,
                                    lat = 27.1929,
                                    lng = 78.0310
                                ),
                                ActivityItem(
                                    id = "act_up_1_5",
                                    timeSlot = "05:15 PM - 07:30 PM",
                                    title = "Mehtab Bagh Sunset Reflection of Taj across Yamuna",
                                    description = "Watch the silhouette of Taj Mahal across Yamuna River during magical golden hour.",
                                    location = "Mehtab Bagh Gardens",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false,
                                    lat = 27.1800,
                                    lng = 78.0418
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5700 else 3100
                        )
                    )
                }

                // Day 2: Varanasi Holy Ghats & Sarnath (5 places)
                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Varanasi Subah-e-Banaras, Ganga Aarti & Sarnath",
                            theme = "Spiritual Capital of India & Sacred River Ghats",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_up_2_1",
                                    timeSlot = "05:30 AM - 08:30 AM",
                                    title = "Sunrise Rowboat Cruise on Holy River Ganga",
                                    description = "Glide past 84 historic ghats from Assi to Manikarnika watching morning rituals and holy bathing.",
                                    location = "Assi Ghat to Dashashwamedh",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 25.2970,
                                    lng = 83.0070
                                ),
                                ActivityItem(
                                    id = "act_up_2_2",
                                    timeSlot = "09:00 AM - 11:30 AM",
                                    title = "Kashi Vishwanath Jyotirlinga Golden Corridor Darshan",
                                    description = "Sacred darshan at the magnificent Kashi Vishwanath Golden Temple and modern heritage corridor.",
                                    location = "Kashi Vishwanath Corridor",
                                    estimatedCost = 100 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 25.3109,
                                    lng = 83.0107
                                ),
                                ActivityItem(
                                    id = "act_up_2_3",
                                    timeSlot = "12:00 PM - 01:30 PM",
                                    title = "Banarasi Kachori Jalebi, Malaiyo & Banarasi Paan",
                                    description = "Savor legendary Banarasi crispy kachori with aloo curry, frothy saffron Malaiyo, and sweet Maghai Paan.",
                                    location = "Chowk Street Food Alley",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_up_2_4",
                                    timeSlot = "02:30 PM - 05:00 PM",
                                    title = "Sarnath Dhamek Stupa (Lord Buddha's First Sermon)",
                                    description = "Explore 5th-century Buddhist stupa, Ashoka Lion Capital (India's national emblem), and deer park.",
                                    location = "Sarnath Archaeological Park",
                                    estimatedCost = 80 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 25.3811,
                                    lng = 83.0229
                                ),
                                ActivityItem(
                                    id = "act_up_2_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Grand Evening Dashashwamedh Maha Ganga Aarti",
                                    description = "Witness world-famous 45-min synchronized fire lamp ritual with conch shells and sacred chants from boat.",
                                    location = "Dashashwamedh Ghat, Varanasi",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 25.3075,
                                    lng = 83.0105
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5200 else 2800
                        )
                    )
                }
            }

            // 7. TAMIL NADU
            s.contains("tamil nadu") || d.contains("ooty") || d.contains("madurai") || d.contains("chennai") || d.contains("kanyakumari") -> {
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Madurai Meenakshi Amman & Dravidian Heritage",
                            theme = "Towering Gopurams & Sangam Architecture",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_tn_1_1",
                                    timeSlot = "07:30 AM - 10:30 AM",
                                    title = "Meenakshi Amman Temple 14 Towering Gopurams",
                                    description = "Explore thousand-pillar hall with musical pillars and ornate Dravidian gopuram sculptures.",
                                    location = "Meenakshi Temple, Madurai",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = true,
                                    lat = 9.9195,
                                    lng = 78.1194
                                ),
                                ActivityItem(
                                    id = "act_tn_1_2",
                                    timeSlot = "11:00 AM - 01:00 PM",
                                    title = "Thirumalai Nayakkar Mahal Palace",
                                    description = "17th-century Italian-Dravidian palace with giant 82-ft stucco pillars and throne chamber.",
                                    location = "Thirumalai Nayakkar Mahal",
                                    estimatedCost = 60 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = true,
                                    lat = 9.9150,
                                    lng = 78.1239
                                ),
                                ActivityItem(
                                    id = "act_tn_1_3",
                                    timeSlot = "01:15 PM - 02:30 PM",
                                    title = "Madurai Jigarthanda & Chettinad Banana Leaf Feast",
                                    description = "Spicy Chettinad curry with parotta and the iconic cold sweet almond gum beverage Jigarthanda.",
                                    location = "Famous Jigarthanda Shop & Murugan Idli",
                                    estimatedCost = 280 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_tn_1_4",
                                    timeSlot = "03:00 PM - 05:30 PM",
                                    title = "Gandhi Memorial Museum & Khadi Craft Studio",
                                    description = "Historical palace where Mahatma Gandhi's bloodstained dhoti is preserved, and handloom crafts.",
                                    location = "Tamukkam Grounds, Madurai",
                                    estimatedCost = 20 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = true,
                                    lat = 9.9328,
                                    lng = 78.1415
                                ),
                                ActivityItem(
                                    id = "act_tn_1_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Vandiyur Mariamman Teppakulam & Temple Night Aarti",
                                    description = "Huge 16-acre temple tank reservoir with central floating mandapam illuminated in evening.",
                                    location = "Teppakulam, Madurai",
                                    estimatedCost = 0,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 9.9056,
                                    lng = 78.1512
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5100 else 2700
                        )
                    )
                }

                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Ooty Nilgiri Toy Train, Doddabetta Peak & Botanical Gardens",
                            theme = "Queen of Hill Stations & Pine Forests",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_tn_2_1",
                                    timeSlot = "08:00 AM - 10:30 AM",
                                    title = "Nilgiri Mountain Railway UNESCO Toy Train Ride",
                                    description = "Ride the steam-hauled heritage rack railway through 16 tunnels and 250 bridges across misty Nilgiri hills.",
                                    location = "Ooty Railway Station to Coonoor",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 11.4102,
                                    lng = 76.7031
                                ),
                                ActivityItem(
                                    id = "act_tn_2_2",
                                    timeSlot = "11:00 AM - 01:00 PM",
                                    title = "Doddabetta Peak (8,650 ft) 360-Degree Telescope View",
                                    description = "Stand at the highest summit in the Nilgiris with panoramic telescope views of Chamundi hills.",
                                    location = "Doddabetta Summit, Ooty",
                                    estimatedCost = 40 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 11.4010,
                                    lng = 76.7350
                                ),
                                ActivityItem(
                                    id = "act_tn_2_3",
                                    timeSlot = "01:15 PM - 02:30 PM",
                                    title = "Ooty Handmade Chocolates & Nilgiri Spiced Lunch",
                                    description = "Tasting homemade dark chocolates, fudge, and traditional South Indian meals.",
                                    location = "King Star Chocolates & Charing Cross",
                                    estimatedCost = 300 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_tn_2_4",
                                    timeSlot = "03:00 PM - 05:30 PM",
                                    title = "Government Botanical & 20-Million-Year Fossil Tree",
                                    description = "55-acre terraced garden established in 1848 with 1,000 species of exotic flora and fossil tree trunk.",
                                    location = "Ooty Botanical Garden",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 11.4180,
                                    lng = 76.7110
                                ),
                                ActivityItem(
                                    id = "act_tn_2_5",
                                    timeSlot = "06:00 PM - 08:30 PM",
                                    title = "Ooty Boathouse Lake Boating & Tibetan Market",
                                    description = "Pedal boating on peaceful Ooty Lake surrounded by eucalyptus trees and evening sweater shopping.",
                                    location = "Ooty Lake Boathouse",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 11.4080,
                                    lng = 76.6870
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5600 else 3000
                        )
                    )
                }
            }

            // 8. KARNATAKA
            s.contains("karnataka") || d.contains("hampi") || d.contains("mysore") || d.contains("coorg") || d.contains("bengaluru") -> {
                if (days >= 1) {
                    plans.add(
                        DayPlan(
                            dayNumber = 1,
                            title = "Mysuru Royal Palace, Chamundi Hills & Brindavan",
                            theme = "Wodeyar Royalty & Sandalwood City",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ka_1_1",
                                    timeSlot = "08:30 AM - 11:30 AM",
                                    title = "Mysore Palace (Amba Vilas) Royal Durbar & Golden Throne",
                                    description = "Indo-Saracenic palace masterpiece with stained glass domes, carved mahogany ceilings, and silver doors.",
                                    location = "Mysore Palace, Sayyaji Rao Road",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = true,
                                    lat = 12.3051,
                                    lng = 76.6551
                                ),
                                ActivityItem(
                                    id = "act_ka_1_2",
                                    timeSlot = "11:45 AM - 01:15 PM",
                                    title = "Chamundi Hill Temple & 16-ft Monolithic Nandi Bull",
                                    description = "Drive up to 3,489 ft summit temple and see the massive single-granite carved Nandi statue.",
                                    location = "Chamundi Hill, Mysuru",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 1.5,
                                    isIndoor = false,
                                    lat = 12.2750,
                                    lng = 76.6710
                                ),
                                ActivityItem(
                                    id = "act_ka_1_3",
                                    timeSlot = "01:30 PM - 02:45 PM",
                                    title = "Authentic Mysore Masala Dosa & Mysore Pak",
                                    description = "Crispy red chutney butter dosa with potato masala and melt-in-mouth hot ghee Mysore Pak.",
                                    location = "Mylari Hotel / Guru Sweets",
                                    estimatedCost = 250 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ka_1_4",
                                    timeSlot = "03:15 PM - 05:00 PM",
                                    title = "St. Philomena's Neo-Gothic Cathedral & Devaraja Market",
                                    description = "175-ft twin spire cathedral and 130-year-old traditional market filled with spices, flowers, and incense.",
                                    location = "Devaraja Market, Mysuru",
                                    estimatedCost = 50,
                                    durationHours = 1.75,
                                    isIndoor = false,
                                    lat = 12.3167,
                                    lng = 76.6583
                                ),
                                ActivityItem(
                                    id = "act_ka_1_5",
                                    timeSlot = "05:30 PM - 08:30 PM",
                                    title = "Brindavan Gardens Musical Fountains & KRS Dam",
                                    description = "Terraced Mughal-style garden with synchronized dancing fountains and colorful illumination.",
                                    location = "Brindavan Gardens, Mandya",
                                    estimatedCost = 100 * travelers,
                                    durationHours = 3.0,
                                    isIndoor = false,
                                    lat = 12.4256,
                                    lng = 76.5728
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5300 else 2800
                        )
                    )
                }

                if (days >= 2) {
                    plans.add(
                        DayPlan(
                            dayNumber = 2,
                            title = "Hampi UNESCO World Heritage & Stone Chariot",
                            theme = "Vijayanagara Empire Monoliths & Tungabhadra Boulders",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_ka_2_1",
                                    timeSlot = "08:00 AM - 10:45 AM",
                                    title = "Virupaksha Temple & Hemakuta Hill Sunset Ruins",
                                    description = "7th-century functioning Shiva shrine with 160-ft gopuram and sprawling monolithic boulder temples.",
                                    location = "Hampi Bazaar, Virupaksha",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.75,
                                    isIndoor = false,
                                    lat = 15.3350,
                                    lng = 76.4600
                                ),
                                ActivityItem(
                                    id = "act_ka_2_2",
                                    timeSlot = "11:00 AM - 01:30 PM",
                                    title = "Vittala Temple, Stone Chariot & 56 Musical Pillars",
                                    description = "Iconic UNESCO stone chariot carved from granite and pillars that produce musical notes when tapped.",
                                    location = "Vittala Temple Complex",
                                    estimatedCost = 60 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false,
                                    lat = 15.3364,
                                    lng = 76.4789
                                ),
                                ActivityItem(
                                    id = "act_ka_2_3",
                                    timeSlot = "01:45 PM - 03:00 PM",
                                    title = "Traditional Karnataka Bisi Bele Bath & Filter Coffee",
                                    description = "Spicy lentil rice with vegetables, ghee boondi, papad, and freshly brewed frothy filter coffee.",
                                    location = "Mango Tree Restaurant, Hampi",
                                    estimatedCost = 280 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_ka_2_4",
                                    timeSlot = "03:30 PM - 05:30 PM",
                                    title = "Lotus Mahal, Elephant Stables & Queen's Bath",
                                    description = "Indo-Islamic royal enclosure pavilions with arched domes and grand stable chambers.",
                                    location = "Royal Enclosure, Hampi",
                                    estimatedCost = 50 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false,
                                    lat = 15.3200,
                                    lng = 76.4710
                                ),
                                ActivityItem(
                                    id = "act_ka_2_5",
                                    timeSlot = "05:45 PM - 08:00 PM",
                                    title = "Tungabhadra Coracle Boat Ride & Matanga Hill Sunset",
                                    description = "Round circular wicker boat ride across Tungabhadra River rapids and sunset over boulder landscape.",
                                    location = "Chakratirtha Coracle Jetty",
                                    estimatedCost = 200 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5500 else 2900
                        )
                    )
                }
            }

            // Generic Comprehensive Generator for all remaining Indian States & UTs
            else -> {
                val matchingRegion = IndiaTravelDataset.statesAndUTs.find {
                    it.name.contains(destinationName, ignoreCase = true) ||
                            it.popularDestinations.any { dest -> dest.contains(destinationName, ignoreCase = true) }
                } ?: IndiaTravelDataset.statesAndUTs.first { it.id == "andhra_pradesh" }

                val attractions = matchingRegion.famousAttractions
                val foodList = matchingRegion.famousFood
                val destinations = matchingRegion.popularDestinations

                for (dayNum in 1..days) {
                    val dayDest = destinations.getOrElse((dayNum - 1) % destinations.size) { destinationName }
                    val att1 = attractions.getOrElse(((dayNum - 1) * 3) % attractions.size) { "$dayDest Heritage Site" }
                    val att2 = attractions.getOrElse(((dayNum - 1) * 3 + 1) % attractions.size) { "$dayDest Nature Reserve" }
                    val att3 = attractions.getOrElse(((dayNum - 1) * 3 + 2) % attractions.size) { "$dayDest Panorama Viewpoint" }
                    val food1 = foodList.getOrElse((dayNum - 1) % foodList.size) { "Local Culinary Thali" }
                    val food2 = foodList.getOrElse((dayNum) % foodList.size) { "Regional Specialties" }

                    plans.add(
                        DayPlan(
                            dayNumber = dayNum,
                            title = "Day $dayNum: $dayDest & ${matchingRegion.name} Highlights",
                            theme = "${matchingRegion.culture} & Multi-Spot Sightseeing",
                            activities = listOf(
                                ActivityItem(
                                    id = "act_gen_${dayNum}_1",
                                    timeSlot = "08:00 AM - 10:30 AM",
                                    title = "Explore $att1",
                                    description = "Morning guided walking tour exploring iconic architecture, history, and photography spots.",
                                    location = att1,
                                    estimatedCost = 100 * travelers,
                                    durationHours = 2.5,
                                    isIndoor = false
                                ),
                                ActivityItem(
                                    id = "act_gen_${dayNum}_2",
                                    timeSlot = "10:45 AM - 12:45 PM",
                                    title = "Sightseeing at $att2",
                                    description = "Discover natural landscapes, cultural exhibits, and ancient craft traditions.",
                                    location = att2,
                                    estimatedCost = 80 * travelers,
                                    durationHours = 2.0,
                                    isIndoor = false
                                ),
                                ActivityItem(
                                    id = "act_gen_${dayNum}_3",
                                    timeSlot = "01:00 PM - 02:15 PM",
                                    title = "Authentic $food1 & Local Gastronomy",
                                    description = "Savor freshly cooked state culinary delicacies prepared in authentic traditional style.",
                                    location = "$dayDest Heritage Diner",
                                    estimatedCost = 320 * travelers,
                                    durationHours = 1.25,
                                    isIndoor = true
                                ),
                                ActivityItem(
                                    id = "act_gen_${dayNum}_4",
                                    timeSlot = "02:30 PM - 04:45 PM",
                                    title = "Excursion to $att3",
                                    description = "Panoramic vistas, local wildlife spotting, and cultural discovery trail.",
                                    location = att3,
                                    estimatedCost = 120 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false
                                ),
                                ActivityItem(
                                    id = "act_gen_${dayNum}_5",
                                    timeSlot = "05:00 PM - 07:15 PM",
                                    title = "Sunset at $dayDest Promenade & $food2",
                                    description = "Golden hour photography, evening breeze, and sampling regional street food snacks.",
                                    location = "$dayDest Sunset Point",
                                    estimatedCost = 150 * travelers,
                                    durationHours = 2.25,
                                    isIndoor = false
                                ),
                                ActivityItem(
                                    id = "act_gen_${dayNum}_6",
                                    timeSlot = "07:30 PM - 09:30 PM",
                                    title = if (dayNum == days) "Return Transit to $startingPoint" else "Night Bazaar & $hotelPref Stay",
                                    description = if (dayNum == days) "Connecting transit back to $startingPoint." else "Explore handicrafts night market and unwind at $hotelPref hotel.",
                                    location = if (dayNum == days) "Transit Hub to $startingPoint" else "$hotelPref Resort",
                                    estimatedCost = if (dayNum == days) (if (transportPref.contains("Flight", true)) 2200 * travelers else 650 * travelers) else (if (hotelPref.contains("Luxury", true)) 3500 else 1800),
                                    durationHours = 2.0,
                                    isIndoor = true
                                )
                            ),
                            estimatedCost = if (hotelPref.contains("Luxury", true)) 5400 else 2900
                        )
                    )
                }
            }
        }

        return plans
    }
}
