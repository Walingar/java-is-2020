package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;


public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final LinkedHashMap<Month, LinkedHashMap<Integer, DayTemperatureInfo>> _yearData = new LinkedHashMap<Month, LinkedHashMap<Integer, DayTemperatureInfo>>();
    private final HashMap<Month, Double> _averageData = new HashMap<Month, Double>();

    public void UpdateAverage(Month month, Integer newTemperature) {
        var average = _averageData.get(month);
        average = (average * (_yearData.get(month).size() - 1) + newTemperature) / _yearData.get(month).size();
        _averageData.replace(month, average);
    }

    public HashMap<Integer, DayTemperatureInfo> AddMonth(Month month) {
        LinkedHashMap<Integer, DayTemperatureInfo> monthData = new LinkedHashMap<Integer, DayTemperatureInfo>();
        _yearData.put(month, monthData);

        return monthData;
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        if (_yearData.containsKey(info.getMonth())) {
            var monthData = _yearData.get(info.getMonth());


            if (monthData.containsKey(info.getDay())) {
                throw new IllegalArgumentException(String.format("Day is already in stats"));
            } else {
                monthData.put(info.getDay(), info);
                UpdateAverage(info.getMonth(), info.getTemperature());
            }
        } else {
            var monthData = AddMonth(info.getMonth());
            monthData.put(info.getDay(), info);
            _averageData.put(info.getMonth(), (double) info.getTemperature());
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {

        if (_yearData.containsKey(month)) {
            return _averageData.get(month);
        }
        return null;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatureData = new HashMap<Month, Integer>();
        for (Month month : _yearData.keySet()) {
            var monthData = _yearData.get(month);
            DayTemperatureInfo max = monthData.values().stream().max(Comparator.comparing(DayTemperatureInfo::getTemperature)).orElseThrow(NoSuchElementException::new);
            maxTemperatureData.put(month, max.getTemperature());
        }
        return maxTemperatureData;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {

        if (!_yearData.containsKey(month)) return Collections.emptyList();
        var monthData = _yearData.get(month);
        return monthData.values().stream().sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature)).collect(toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (_yearData.containsKey(month)) {
            var monthData = _yearData.get(month);
            if (monthData.containsKey(day)) {
                return monthData.get(day);
            }
        }
        return null;
    }

}
