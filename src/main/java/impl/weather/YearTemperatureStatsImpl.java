package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.List;
import java.util.Map;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    @Override
    public void updateStats(DayTemperatureInfo info) {

    }

    @Override
    public Double getAverageTemperature(Month month) {
        return null;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return null;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        return null;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        return null;
    }
}
