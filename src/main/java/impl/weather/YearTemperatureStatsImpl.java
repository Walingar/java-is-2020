package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthInfo> yearTemperatureInfo = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();
        MonthInfo monthInfo = yearTemperatureInfo.get(month);
        if (yearTemperatureInfo.get(month) != null) {
            monthInfo.updateMonthInfo(info);
        } else {
            yearTemperatureInfo.put(month, new MonthInfo(info));
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo monthInfo = yearTemperatureInfo.get(month);
        if (monthInfo == null) {
            return null;
        } else {
            return yearTemperatureInfo.get(month).getTemperatureAverage();
        }
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatures = new LinkedHashMap<>();
        yearTemperatureInfo.forEach((key, value) -> maxTemperatures.put(key, value.getTemperatureMax()));
        return maxTemperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        List<DayTemperatureInfo> sortedTemperature = new ArrayList<>();
        MonthInfo monthInfo = yearTemperatureInfo.get(month);
        if (monthInfo != null) {
            sortedTemperature = monthInfo.sortTemperature(month);
        }
        return sortedTemperature;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthInfo monthInfo = yearTemperatureInfo.get(month);
        if (monthInfo == null) {
            return null;
        } else {
            return yearTemperatureInfo.get(month).getTemperature(day);
        }
    }
}
