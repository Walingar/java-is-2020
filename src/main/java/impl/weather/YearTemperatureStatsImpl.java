package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;


public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private final Collection<DayTemperatureInfo> dayTempInfo = new ArrayList<>();
    Double temperatureAverage = 0.0;
    Map<Month, Integer> temperatureMax;
    boolean newData = true;

    @Override
    public void updateStats(DayTemperatureInfo info) {
        dayTempInfo.add(info);
        newData = true;
    }

    @Override
    public Double getAverageTemperature(Month month) {
        if (!newData) {
            return temperatureAverage;
        }
        newData = false;
        double summaryTemp = 0;
        int count = 0;
        boolean found = false;
        for (DayTemperatureInfo dti : dayTempInfo) {
            if (dti.getMonth().equals(month)) {
                summaryTemp += dti.getTemperature();
                count++;
                found = true;
            }
        }
        if (found) {
            temperatureAverage = summaryTemp / count;
            return temperatureAverage;
        } else {
            temperatureAverage = null;
            return null;
        }
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        if (!newData) {
            return temperatureMax;
        }
        newData = false;
        temperatureMax = new HashMap<>();
        for (DayTemperatureInfo dti : dayTempInfo) {
            var curValue = temperatureMax.putIfAbsent(dti.getMonth(), dti.getTemperature());
            if (curValue != null) {
                temperatureMax.replace(dti.getMonth(), Math.max(curValue, dti.getTemperature()));
            }
        }
        return temperatureMax;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        newData = false;
        List<DayTemperatureInfo> result = new ArrayList<>();
        for (DayTemperatureInfo dti : dayTempInfo) {
            if (dti.getMonth().equals(month)) {
                result.add(dti);
            }
        }
        Collections.sort(result, new Comparator<DayTemperatureInfo>() {
            @Override
            public int compare(DayTemperatureInfo o1, DayTemperatureInfo o2) {
                return o1.getTemperature() - o2.getTemperature();
            }
        });
        return result;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        newData = false;
        for (DayTemperatureInfo dti : dayTempInfo) {
            if (dti.getDay() == day && dti.getMonth().equals(month)) {
                return dti;
            }
        }
        return null;
    }
}
