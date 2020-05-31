package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthTemperatureStats;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private Map<Month, MonthTemperatureStats> monthTemperatureStatsMap;

    @Override
    public void updateStats(DayTemperatureInfo info) {
        var monthTemperatureStats = monthTemperatureStatsMap.computeIfAbsent(
                info.getMonth(), month -> new MonthTemperatureStatsImpl(info.getMonth()));
        monthTemperatureStats.updateStats(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        return monthTemperatureStatsMap.get(month).getAverageTemperature();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatureMonthMap = new HashMap<>();
        maxTemperatureMonthMap.replaceAll((m, v) -> monthTemperatureStatsMap.get(m).getMaxTemperature());
        return maxTemperatureMonthMap;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        return monthTemperatureStatsMap.get(month).getSortedTemperature();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        return monthTemperatureStatsMap.get(month).getTemperature(day);
    }
}
