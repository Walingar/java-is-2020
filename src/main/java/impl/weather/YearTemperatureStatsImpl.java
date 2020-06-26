package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    List<DayTemperatureInfo> list = new ArrayList<>();
    private Double avgTemperature = null;
    Map<Month, Integer> map = new HashMap<>();
    private boolean dataHasChanged = false;

    @Override
    public void updateStats(DayTemperatureInfo info) {
        list.add(info);
        dataHasChanged = true;
    }

    @Override
    public Double getAverageTemperature(Month month) {

        if (dataHasChanged) {
            double sumTemperature = 0;
            int numberOfDays = 0;

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMonth() == month) {
                    sumTemperature += list.get(i).getTemperature();
                    numberOfDays++;
                }
            }

            avgTemperature = (numberOfDays != 0) ? (sumTemperature / numberOfDays) : null;
            dataHasChanged = false;
        }

        return avgTemperature;

    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {

        if (dataHasChanged) {

            for (int i = 0; i < list.size(); i++) {

                int temperature = map.getOrDefault(list.get(i).getMonth(), Integer.MIN_VALUE);
                if (temperature < list.get(i).getTemperature()) {
                    map.put(list.get(i).getMonth(), list.get(i).getTemperature());
                }
            }

            dataHasChanged = false;
        }

        return map;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        List<DayTemperatureInfo> listTemperature = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (month == list.get(i).getMonth()) {
                listTemperature.add(list.get(i));
            }
        }

        Comparator<DayTemperatureInfo> comparator = new Comparator<DayTemperatureInfo>() {
            @Override
            public int compare(DayTemperatureInfo object1, DayTemperatureInfo object2) {
                return object1.getTemperature() - object2.getTemperature();
            }
        };
        listTemperature.sort(comparator);
        return listTemperature;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {

        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getMonth() == month) && (list.get(i).getDay() == day)) {
                return list.get(i);
            }
        }
        return null;
    }
};
