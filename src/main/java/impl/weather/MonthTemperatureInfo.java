package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingDouble;

public class MonthTemperatureInfo {

    private LinkedHashMap<Integer, DayTemperatureInfo> dayInfos = new LinkedHashMap<>();

    public void updateMonthStats(DayTemperatureInfo dayInfo) {
        dayInfos.putIfAbsent(dayInfo.getDay(), dayInfo);
    }

    public double getAverageTemperature() {
        return dayInfos.values().stream().mapToDouble(x-> x.getTemperature()).sum() / dayInfos.size();
    }

    public Integer getMaxTemperature() {
        if (!dayInfos.isEmpty())
        {
            return (int)dayInfos.values().stream().mapToDouble(x-> x.getTemperature()).max().getAsDouble();
        }
        return 0;
    }

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayInfos.values().stream().sorted(comparingDouble(DayTemperatureInfo::getTemperature)).collect(Collectors.toList());
    }

    public DayTemperatureInfo getTemperature(int day) {
        return dayInfos.get(day);
    }
}