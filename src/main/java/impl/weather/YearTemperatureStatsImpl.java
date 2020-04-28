package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    private Map<Month, MonthInfo> monthInfoMap = new HashMap<>();


    @Override
    public void updateStats(DayTemperatureInfo info) {
        if (monthInfoMap.get(info.getMonth()) == null) {
            monthInfoMap.put(info.getMonth(), new MonthInfo());
        }
        monthInfoMap.get(info.getMonth()).add(info);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        if (monthInfoMap.get(month) == null) {
            return null;
        }
        return monthInfoMap.get(month).getAverage();
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> maxTemp = new HashMap<>();
        monthInfoMap.entrySet().forEach(temp -> {
            maxTemp.put(temp.getKey(), temp.getValue().getMaxTemperature());
        });
        return maxTemp;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        if (monthInfoMap.get(month) != null) {
            List<DayTemperatureInfo> ofMonth = new ArrayList<>(monthInfoMap.get(month).getDayTemperatureMap().values());
            Comparator<DayTemperatureInfo> comparator = Comparator.comparingInt(DayTemperatureInfo::getTemperature)
                    .thenComparing((o1, o2) -> o2.getDay() - o1.getDay());   // костыль, так как я не могу контролировать последовательность, иначе оно не совпадает, так как у меня дни не совпадают с вами
            ofMonth.sort(comparator);
            return ofMonth;
        }
        return Collections.emptyList();
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        if (monthInfoMap.get(month) != null) {
            return monthInfoMap.get(month).getTemperature(day);
        }
        return null;
    }
}
