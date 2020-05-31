package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.toList;


public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final LinkedHashMap<Month, LinkedHashMap<Integer, DayTemperatureInfo>> yearData = new LinkedHashMap<>();
    private final HashMap<Month, Double> averageData = new HashMap<Month, Double>();
    private final  Map<Month, Integer> maxTemperatureData = new HashMap<Month, Integer>();


    private void UpdateMax(Month month, Integer newTemperature)
    {
            var monthData = yearData.get(month);
            if(maxTemperatureData.containsKey(month)) {
                if (maxTemperatureData.get(month) < newTemperature) {
                    maxTemperatureData.remove(month);
                    maxTemperatureData.put(month, newTemperature);
                }
            }
            else
            {
                maxTemperatureData.put(month, newTemperature);
            }

    }
    private void UpdateAverage(Month month, Integer newTemperature) {
        var average = averageData.get(month);
        average = (average * (yearData.get(month).size() - 1) + newTemperature) / yearData.get(month).size();
        averageData.replace(month, average);
    }

    private HashMap<Integer, DayTemperatureInfo> AddMonth(Month month) {
        LinkedHashMap<Integer, DayTemperatureInfo> monthData = new LinkedHashMap<Integer, DayTemperatureInfo>();
        yearData.put(month, monthData);

        return monthData;
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        if (yearData.containsKey(info.getMonth())) {
            var monthData = yearData.get(info.getMonth());


            if (monthData.containsKey(info.getDay())) {
                throw new IllegalArgumentException("Day is already in stats");
            } else {
                monthData.put(info.getDay(), info);
                UpdateAverage(info.getMonth(), info.getTemperature());
                UpdateMax(info.getMonth(), info.getTemperature());
            }
        } else {
            var monthData = AddMonth(info.getMonth());
            monthData.put(info.getDay(), info);
            UpdateMax(info.getMonth(), info.getTemperature());
            averageData.put(info.getMonth(), (double) info.getTemperature());
        }
    }

    @Override
    public Double getAverageTemperature(Month month) {

        if (yearData.containsKey(month)) {
            return averageData.get(month);
        }
        return null;
    }




    @Override
    public Map<Month, Integer> getMaxTemperature()
    {
        return maxTemperatureData;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {

        if (!yearData.containsKey(month)) {
            return Collections.emptyList();
        }
        var monthData = yearData.get(month);
        return monthData.values().stream().sorted(Comparator.comparingInt(DayTemperatureInfo::getTemperature)).collect(toList());
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (yearData.containsKey(month)) {
            var monthData = yearData.get(month);

            return monthData.get(day);

        }
        return null;
    }

}
