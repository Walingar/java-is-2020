package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.Comparator;

public class DayTemperatureInfoComperator implements Comparator<DayTemperatureInfo> {
    @Override
    public int compare(DayTemperatureInfo o1, DayTemperatureInfo o2) {
        if (o1.getTemperature() == o2.getTemperature()) {
            return o2.getDay() - o1.getDay();
        }
        return o1.getTemperature() - o2.getTemperature();
    }
}
