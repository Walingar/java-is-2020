package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;


public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private Collection<DayTemperatureInfo> _dayTempInfo = new ArrayList<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        _dayTempInfo.add(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        double summaryTemp = 0;
        int count = 0;
        boolean found = false;
        for (DayTemperatureInfo dti : _dayTempInfo) {
            if (dti.getMonth().equals(month)) {
                summaryTemp += dti.getTemperature();
                count++;
                found = true;
            }
        }
        if (found) {
            return summaryTemp / count;
        } else {
            return null;
        }
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> result = new HashMap<>();
        for (DayTemperatureInfo dti : _dayTempInfo) {
            var curValue = result.putIfAbsent(dti.getMonth(), dti.getTemperature());
            if (curValue != null) {
                result.replace(dti.getMonth(), Math.max(curValue, dti.getTemperature()));
            }
        }
        return result;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        List<DayTemperatureInfo> result = new ArrayList<>();
        for (DayTemperatureInfo dti : _dayTempInfo) {
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
        for (DayTemperatureInfo dti : _dayTempInfo) {
            if (dti.getDay() == day && dti.getMonth().equals(month)) {
                return dti;
            }
        }
        return null;
    }
//
//    @Override
//    public void print() {
//        for (DayTemperatureInfo dti : _dayTempInfo) {
//            System.out.println("day " + dti.getDay() + ", month " + dti.getMonth() + "; temp-re = " + dti.getTemperature());
//        }
//        System.out.println();
//    }
}
