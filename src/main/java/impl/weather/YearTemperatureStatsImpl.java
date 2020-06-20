package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthInfo> monthInfoMap = new HashMap<>();


    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();
        monthInfoMap.putIfAbsent(month, new MonthInfo());
        monthInfoMap.get(month).add(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo monthInfo = monthInfoMap.get(month);
        return monthInfo == null ? null : monthInfo.getAverage();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemp = new HashMap<>();
        monthInfoMap.forEach((key, value) -> maxTemp.put(key, value.getMaxTemperature()));
        return maxTemp;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        MonthInfo monthInfo = monthInfoMap.get(month);
        if (monthInfo != null) {
            List<DayTemperatureInfo> ofMonth = new ArrayList<>(monthInfo.getDayTemperatureMap().values());
            Comparator<DayTemperatureInfo> comparator = Comparator.comparingInt(DayTemperatureInfo::getTemperature);
            ofMonth.sort(comparator);
            return ofMonth;
        }
        return Collections.emptyList();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthInfo monthInfo = monthInfoMap.get(month);
        return monthInfo == null ? null : monthInfo.getTemperature(day);
    }
}
