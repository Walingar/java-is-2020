package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.List;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthInfo> status = new EnumMap<>(Month.class);

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month month = info.getMonth();
        status.computeIfAbsent(month, key -> new MonthInfo()).set(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo current = status.get(month);
        if (current == null) {
            return null;
        }
        return status.get(month).getAverage();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> ans = new HashMap<>();
        status.forEach((Month, MonthInfo) -> ans.put(Month, MonthInfo.getMaxTemperatureOnMonth()));
        return ans;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        MonthInfo current = status.get(month);
        if (current == null) {
            return new ArrayList<>();
        }
        return current.getSortedTemp();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthInfo current = status.get(month);
        if (current == null) {
            return null;
        }
        return current.getInfo().get(day);
    }
}