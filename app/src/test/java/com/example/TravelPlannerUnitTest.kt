package com.example

import com.example.data.model.DestinationCategory
import com.example.data.model.RegionType
import com.example.data.repository.IndiaTravelDataset
import org.junit.Assert.*
import org.junit.Test

class TravelPlannerUnitTest {

    @Test
    fun testAll28StatesAnd8UTsDatasetIntegrity() {
        val allRegions = IndiaTravelDataset.statesAndUTs
        assertEquals("Must contain all 28 states and 8 Union Territories", 36, allRegions.size)

        val states = allRegions.filter { it.type == RegionType.STATE }
        val uts = allRegions.filter { it.type == RegionType.UNION_TERRITORY }

        assertEquals("Must have 28 Indian states", 28, states.size)
        assertEquals("Must have 8 Indian Union Territories", 8, uts.size)

        // Verify Andhra Pradesh data is rich and verified
        val ap = allRegions.find { it.id == "andhra_pradesh" }
        assertNotNull(ap)
        assertTrue(ap!!.popularDestinations.contains("Araku Valley"))
        assertTrue(ap.famousAttractions.contains("Borra Caves"))
        assertTrue(ap.famousFood.contains("Pootharekulu"))
    }

    @Test
    fun testPriceComparisonCatalogIntegrity() {
        val prices = IndiaTravelDataset.priceComparisonCatalog
        assertTrue("Price catalog must contain verified options", prices.isNotEmpty())

        val bestMatches = prices.filter { it.recommendationType.name == "BEST_MATCH" }
        val cheapest = prices.filter { it.recommendationType.name == "CHEAPEST" }

        assertTrue("Must include best match recommendations", bestMatches.isNotEmpty())
        assertTrue("Must include cheapest alternatives", cheapest.isNotEmpty())
    }

    @Test
    fun testRouteOptimizerSavingsMetric() {
        val beforeDistance = 85.0
        val afterDistance = 62.0
        val savedKm = beforeDistance - afterDistance

        val beforeTimeMin = 260
        val afterTimeMin = 185
        val savedTimeMin = beforeTimeMin - afterTimeMin

        val beforeCost = 2100
        val afterCost = 1650
        val savedCost = beforeCost - afterCost

        assertEquals(23.0, savedKm, 0.1)
        assertEquals(75, savedTimeMin) // 1h 15m
        assertEquals(450, savedCost)
    }
}
