package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class YearTemperatureStatsFactory {
    public static YearTemperatureStats getInstance() {

        List<DayTemperatureInfo> tempList = new ArrayList();

        var yearTemperatureStats= new YearTemperatureStats(){
            @Override
            public void updateStats(DayTemperatureInfo info) {
                tempList.add(info);
            }

            @Override
            public Double getAverageTemperature(Month month) {
                if (tempList.size() == 0) {
                    return null;
                }
                AtomicInteger tempSum = new AtomicInteger();
                AtomicInteger count = new AtomicInteger();
                tempList.forEach(temp -> {
                    if (month == temp.getMonth()){
                        tempSum.addAndGet(temp.getTemperature());
                        count.getAndIncrement();
                    }
                });
                return tempSum.doubleValue()/count.doubleValue();
            }

            @Override
            public Map<Month, Integer> getMaxTemperature() {
                Map<Month, Integer> maxTemp = new HashMap();
                for (DayTemperatureInfo temp : tempList){
                    var currentValue = maxTemp.getOrDefault(temp.getMonth(), -100000);
                    var max = Math.max(currentValue, temp.getTemperature());
                    maxTemp.put(temp.getMonth(), max);
                }
                return maxTemp;
            }

            @Override
            public List<DayTemperatureInfo> getSortedTemperature(Month month) {
                List<DayTemperatureInfo> ofMonth = new ArrayList();
                Comparator<DayTemperatureInfo> comparator = (o1, o2) -> o1.getTemperature()-o2.getTemperature(); //Comparator.comparing(obj -> obj.getTemperature());
                for (DayTemperatureInfo temp : tempList) {
                    if (month == temp.getMonth()) {
                        ofMonth.add(temp);
                    }
                }
                ofMonth.sort(comparator);
                return ofMonth;
            }

            @Override
            public DayTemperatureInfo getTemperature(int day, Month month) {
                for (DayTemperatureInfo temp : tempList) {
                    if ((temp.getDay() == day) && (temp.getMonth() == month)) {
                        return temp;
                    }
                }
                return null;
            }
        };
        return yearTemperatureStats;
    }
}