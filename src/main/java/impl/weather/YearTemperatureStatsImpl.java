package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.MonthTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Map<Month, MonthTemperatureInfo> yearData = new LinkedHashMap<>();
    private final Map<Month, Integer> yearMaxTemperature = new HashMap<Month, Integer>();

    private MonthTemperatureInfo addMonth(Month month) {
        var monthData = new MonthTemperatureInfoImpl();
        yearData.put(month, monthData);
        return monthData;
    }

    private void updateYearMaxTemperature(Month month, Integer newTemperature) {
        if (yearMaxTemperature.containsKey(month)) {
            if (yearMaxTemperature.get(month) < newTemperature) {
                yearMaxTemperature.remove(month);
                yearMaxTemperature.put(month, newTemperature);
            }
        } else {
            yearMaxTemperature.put(month, newTemperature);
        }
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        if (yearData.containsKey(info.getMonth())) {
            yearData.get(info.getMonth()).updateStats(info);
        } else {
            addMonth(info.getMonth()).updateStats(info);
        }
        updateYearMaxTemperature(info.getMonth(), info.getTemperature());
    }

    @Override
    public Double getAverageTemperature(Month month) {
        if (yearData.containsKey(month)) {
            return yearData.get(month).getAverageTemperature();
        }
        return null;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return yearMaxTemperature;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (!yearData.containsKey(month)) {
            return Collections.emptyList();
        }
        return yearData.get(month)
                .getMonthData()
                .values()
                .stream()
                .sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature))
                .collect(toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (yearData.containsKey(month)) {
            return yearData.get(month).getDayInfo(day);
        }
        return null;
    }
}
