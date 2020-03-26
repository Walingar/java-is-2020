package weather

import api.weather.DayTemperatureInfo
import api.weather.YearTemperatureStats
import impl.weather.YearTemperatureStatsFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test
import java.time.Month

internal class YearTemperatureStatsTest {
    private fun updateStats(
        info: Collection<DayTemperatureInfo>,
        stats: YearTemperatureStats = YearTemperatureStatsFactory.getInstance()
    ) = stats.apply {
        info.forEach {
            updateStats(it)
        }
    }

    @Test
    fun update() {
        val day = 31
        val month = Month.DECEMBER
        val dayTempInfo = DayTemperatureInfoTestImpl(day, month, 2)
        val stats = updateStats(listOf(dayTempInfo))
        assertEquals(dayTempInfo, stats.getTemperature(day, month))
    }

    @Test
    fun `temperature unknown day`() {
        val stats = YearTemperatureStatsFactory.getInstance()
        assertNull(stats.getTemperature(31, Month.DECEMBER))
    }

    @Test
    fun `average temperature unknown month`() {
        val stats = YearTemperatureStatsFactory.getInstance()
        assertNull(stats.getAverageTemperature(Month.DECEMBER))
    }

    @Test
    fun `average temperature`() {
        val month = Month.DECEMBER
        val info = listOf(
            DayTemperatureInfoTestImpl(1, month, -1),
            DayTemperatureInfoTestImpl(2, month, 10),
            DayTemperatureInfoTestImpl(5, month, 0)
        )
        val stats = updateStats(info)
        assertEquals(
            "Incorrect average temperature for month $month for data $info",
            3.0,
            stats.getAverageTemperature(month)
        )
    }

    @Test
    fun `max temperature`() {
        val info = listOf(
            DayTemperatureInfoTestImpl(1, Month.DECEMBER, -1),
            DayTemperatureInfoTestImpl(2, Month.DECEMBER, 3),
            DayTemperatureInfoTestImpl(5, Month.DECEMBER, 0),
            DayTemperatureInfoTestImpl(1, Month.APRIL, -1),
            DayTemperatureInfoTestImpl(8, Month.MARCH, 30)
        )
        val stats = updateStats(info)
        val maxTempMap = stats.maxTemperature
        assertEquals("Incorrect months count in maxTemperature result for data $info", 3, maxTempMap.size)
        val expectedTemperature = listOf(
            Month.DECEMBER to 3,
            Month.APRIL to -1,
            Month.MARCH to 30
        )
        expectedTemperature.forEach { (month, expectedMaxTemperature) ->
            assertEquals(
                "Incorrect max temperature result for month $month for data $info",
                expectedMaxTemperature,
                maxTempMap[month]
            )
        }
    }

    @Test
    fun `sorted temperature unknown month`() {
        val stats = YearTemperatureStatsFactory.getInstance()
        assertEquals(listOf<DayTemperatureInfo>(), stats.getSortedTemperature(Month.DECEMBER))
    }

    @Test
    fun `sorted temperature`() {
        val month = Month.DECEMBER
        val info = listOf(
            DayTemperatureInfoTestImpl(30, month, -1),
            DayTemperatureInfoTestImpl(1, month, -1),
            DayTemperatureInfoTestImpl(2, month, 3),
            DayTemperatureInfoTestImpl(5, month, 0),
            DayTemperatureInfoTestImpl(4, month, 30)
        )
        val expectedInfo = info.sortedBy { it.temperature }
        val stats = updateStats(info)
        val sortedTemperature = stats.getSortedTemperature(month)
        assertEquals(
            "Incorrect days count in sortedTemperature result for data $info",
            info.size,
            sortedTemperature.size
        )
        assertEquals(expectedInfo, sortedTemperature)
    }

    private class DayTemperatureInfoTestImpl(
        private val _day: Int,
        private val _month: Month,
        private val _temperature: Int
    ) : DayTemperatureInfo {
        override fun getMonth() = _month

        override fun getDay() = _day

        override fun getTemperature() = _temperature
    }
}