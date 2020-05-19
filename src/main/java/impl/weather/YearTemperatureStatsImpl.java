package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthInfo> status = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        Month month = info.getMonth();
        int temperature = info.getTemperature();
        status.putIfAbsent(month,new MonthInfo());
        MonthInfo current = status.get(month);
        current.updateAverage(temperature);
        current.updateMaxValue(temperature);
        status.get(month).set(day, info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo current = status.get(month);
        if (current == null) {
            return null;
        }
        return this.status.get(month).getAverageInfo().getAverage();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> ans = new HashMap<>();
        status.forEach((Month, MonthInfo) -> ans.put(Month, MonthInfo.getMaxTemperatureOnMonth()));
        return ans;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        MonthInfo current;
        current = status.get(month);
        if (current == null) {
            return new ArrayList<>();
        }
        return current.getInfo().values().stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature).thenComparing(DayTemperatureInfo::getDay,Comparator.reverseOrder()))
                .collect(Collectors.toUnmodifiableList());
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
