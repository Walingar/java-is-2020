package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.List;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, monthInfo> status = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        Month month = info.getMonth();
        int temperature = info.getTemperature();
        if (!status.containsKey(month)) {
            status.put(month, new monthInfo());
        }
        monthInfo current = status.get(month);
        current.updateAverage(temperature);
        current.updateMaxValue(temperature);
        status.get(month).set(day, info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        monthInfo current = status.get(month);
        if (current == null) {
            return null;
        }
        return this.status.get(month).getAverageTemp();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> ans = new HashMap<>();
        status.forEach((Month, monthInfo) -> ans.put(Month, monthInfo.getMaxTemperature()));
        return ans;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        monthInfo current;
        current = status.get(month);
        if (current == null) {
            return new ArrayList<>();
        }
        List<DayTemperatureInfo> sortedTemperature = new ArrayList<>(current.getInfo().values());
        sortedTemperature.sort(Comparator.comparing(DayTemperatureInfo::getTemperature).thenComparing(DayTemperatureInfo::getDay, Comparator.reverseOrder()));
        return sortedTemperature;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        monthInfo current = status.get(month);
        if (current == null) {
            return null;
        }
        return current.getInfo().getOrDefault(day, null);
    }
}
