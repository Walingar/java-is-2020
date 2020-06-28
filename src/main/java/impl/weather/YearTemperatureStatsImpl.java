package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    HashMap<Month, MonthInfo> months = new HashMap<>();

    @Override
    public void updateStats(DayTemperatureInfo info) {
        Month currentMonth = info.getMonth();

        if (months.get(currentMonth) == null){
            months.put(currentMonth, new MonthInfo());
        }

        months.get(currentMonth).updateMonth(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        MonthInfo currentMonth = months.get(month);
        return (currentMonth == null) ? null : currentMonth.avgTemperature;
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemperatures = new HashMap<>();

        for (Month month : months.keySet()){
            int maxTemperature = months.get(month).maxTemperature;
            maxTemperatures.put(month, maxTemperature);
        }

        return maxTemperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        ArrayList<DayTemperatureInfo> result = new ArrayList<>();

        if (months.get(month) != null){
            result = (ArrayList<DayTemperatureInfo>) months.get(month).days.clone();

            result.sort(new Comparator<DayTemperatureInfo>() {
                @Override
                public int compare(DayTemperatureInfo dayTemperatureInfo, DayTemperatureInfo t1) {
                    return dayTemperatureInfo.getTemperature() - t1.getTemperature();
                }
            });
        }

        return result;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        MonthInfo currentMonth = months.get(month);

        if (currentMonth != null) {
            for (DayTemperatureInfo currentDay : currentMonth.days) {
                if (currentDay.getDay() == day)
                    return currentDay;
            }
        }

        return null;
    }


    private class MonthInfo {
        Double avgTemperature;
        Integer maxTemperature;
        ArrayList<DayTemperatureInfo> days;

        public MonthInfo(){
            avgTemperature = null;
            maxTemperature = null;
            days = new ArrayList<>();
        }

        private void updateMonth(DayTemperatureInfo info){
            int currentTemperature = info.getTemperature();

            if (days.isEmpty()){
                maxTemperature = currentTemperature;
                avgTemperature = (double)currentTemperature;
            } else {
                if (currentTemperature > maxTemperature) {
                    maxTemperature = currentTemperature;
                }

                avgTemperature = (avgTemperature * days.size() + currentTemperature) / (days.size() + 1);
            }

            days.add(info);
        }
    }

};
