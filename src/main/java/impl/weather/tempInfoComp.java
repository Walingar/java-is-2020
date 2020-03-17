package impl.weather;

import api.weather.DayTemperatureInfo;

import java.util.Comparator;

public class tempInfoComp implements Comparator<DayTemperatureInfo> {
    @Override
    public int compare(DayTemperatureInfo o1, DayTemperatureInfo o2) {
        return o1.getTemperature() - o2.getTemperature() - 1;
    }
}
