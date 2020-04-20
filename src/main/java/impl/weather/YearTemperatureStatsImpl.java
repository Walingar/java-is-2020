package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.*;
import java.util.List;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, Info> status = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        Month month = info.getMonth();
        int temperature = info.getTemperature();
        if (!status.containsKey(month)) {
            status.put(month, new Info());
        }
        Info current = status.get(month);
        current.updateAverage(temperature);
        current.updateMaxValue(temperature);
        status.get(month).set(day, info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Info current = status.get(month);
        if (current == null) {
            return null;
        }
        return this.status.get(month).getAverageTemp();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> ans = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            Month month = Month.of(i);
            Integer current;
            if (!status.containsKey(month)) {
                continue;
            }
            current = status.get(month).getMaxTemperature();
            ans.put(month, current);
        }
        return ans;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        Info current;
        current = status.get(month);
        if (current == null) {
            return new ArrayList<>();
        }
        List<DayTemperatureInfo> sortedTemperature = new ArrayList<>(current.getInfo().values());
        sortedTemperature.sort(new DayTemperatureInfoComperator());
        return sortedTemperature;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        Info current = status.get(month);
        if (current == null) {
            return null;
        }
        return current.getInfo().getOrDefault(day, null);
    }
}
