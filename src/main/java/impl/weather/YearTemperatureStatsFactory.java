package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import javax.swing.*;
import java.time.Month;
import java.util.*;

public class YearTemperatureStatsFactory {
    public static YearTemperatureStats getInstance() {
        YearTemperatureStats temperature = new YearTemperatureStats() {

            List<DayTemperatureInfo> list = new ArrayList<>();

            @Override
            public void updateStats(DayTemperatureInfo info) {
                list.add(info);
            }

            @Override
            public Double getAverageTemperature(Month month) {

                double sumTemperature = 0;
                int countDay = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getMonth() == month) {
                        sumTemperature += list.get(i).getTemperature();
                        countDay += 1;
                    }
                }

                if (sumTemperature != 0) {
                    return (sumTemperature / countDay);
                }
                return null;
            }

            @Override
            public Map<Month, Integer> getMaxTemperature() {

                Map<Month, Integer> map = new HashMap<>();

                for (int i = 0; i < list.size(); i++) {

                    int temperature = map.getOrDefault(list.get(i).getMonth(), -1000000);
                    if (temperature < list.get(i).getTemperature()) {
                        map.put(list.get(i).getMonth(), list.get(i).getTemperature());
                    }
                }
                return map;
            }

            @Override
            public List<DayTemperatureInfo> getSortedTemperature(Month month) {
                List<DayTemperatureInfo> listTemperature = new ArrayList<>();

                for (int i=0; i<list.size(); i++) {
                    if (month == list.get(i).getMonth()) {
                        listTemperature.add(list.get(i));
                    }
                }

                Comparator <DayTemperatureInfo> comparator = new Comparator<DayTemperatureInfo>() {
                    @Override
                    public int compare(DayTemperatureInfo object1, DayTemperatureInfo object2) {
                        return object1.getTemperature()-object2.getTemperature();
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
        return temperature;
    }
}