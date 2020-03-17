package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;
import kotlin.Pair;

import java.awt.*;
import java.time.Month;
import java.util.*;
import java.util.List;

public class YearTemperatureStatsimpl implements YearTemperatureStats {
    private HashMap<Month, HashMap<Integer, DayTemperatureInfo>> status = new HashMap<>();
    private HashMap<Month, Pair<Double, Integer>> averageTemperature = new HashMap<>();
    private HashMap<Month, Integer> maximumTemperature = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int day = info.getDay();
        Month month = info.getMonth();
        int temperature = info.getTemperature();
        if (status.containsKey(month) == false) {
            status.put(month, new HashMap<>());
        }
        status.get(month).put(day, info);
        var average = averageTemperature.getOrDefault(month, null);
        if (average != null) {
            double numOfDays = (average.component2());
            var oldaverage = average.component1();
            double newAverage = ((oldaverage * numOfDays) + temperature) / (numOfDays + 1);
            int counter = (int) numOfDays + 1;
            averageTemperature.replace(month, average, new Pair(newAverage, counter));
        } else {
            averageTemperature.put(month, new Pair((double) temperature, 1));
        }
        var max = maximumTemperature.getOrDefault(month, null);
        if (max != null) {
            if (max < temperature) {
                maximumTemperature.replace(month, max, temperature);
            }
        } else {
            maximumTemperature.put(month, temperature);
        }
    }

    public boolean cmp(int a, int b) {
        return true;
    }

    @Override
    public Double getAverageTemperature(Month month) {
        if (averageTemperature.getOrDefault(month, null) == null) {
            return null;
        }
        var information = averageTemperature.getOrDefault(month, null);
        return information.component1();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        return maximumTemperature;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (status.getOrDefault(month, null) == null) {
            return new ArrayList<>();
        }
        HashMap<Integer, DayTemperatureInfo> temporary = status.getOrDefault(month, null);
        ArrayList<DayTemperatureInfo> List = new ArrayList<DayTemperatureInfo>(temporary.values());
        Collections.sort(List, new tempInfoComp());
        return List;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        HashMap<Integer, DayTemperatureInfo> temporary = status.getOrDefault(month, null);
        if (temporary == null) {
            return null;
        }
        return temporary.getOrDefault(day, null);
    }
}
