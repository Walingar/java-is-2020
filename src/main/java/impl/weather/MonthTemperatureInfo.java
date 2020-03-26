package impl.weather;

import api.weather.DayTemperatureInfo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingDouble;

public class MonthTemperatureInfo {

    private LinkedHashMap<Integer, DayTemperatureInfo> dayData = new LinkedHashMap<>();

    public void updateMonthStats(DayTemperatureInfo dayInfo) {
        dayData.putIfAbsent(dayInfo.getDay(), dayInfo);
    }

    public double getAverageTemperature() {
        return dayData.values().stream().mapToDouble(x-> x.getTemperature()).sum() / dayData.size();
    }

    public Integer getMaxTemperature() {
        if (!dayData.isEmpty())
        {
            return (int)dayData.values().stream().mapToDouble(x-> x.getTemperature()).max().getAsDouble();
        }
        return 0;
    }

    public List<DayTemperatureInfo> getSortedTemperature() {
        return dayData.values().stream().sorted(comparingDouble(DayTemperatureInfo::getTemperature)).collect(Collectors.toList());
    }

    public DayTemperatureInfo getTemperature(int day) {
        return dayData.get(day);
    }
}