package impl.weather;

import api.weather.DayTemperatureInfo;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;


public class Info {
    private final Map<Integer, DayTemperatureInfo> info = new HashMap<>();
    private Pair averageInfo;
    private int maximumTemp;

    public Info() {
        this.averageInfo = new Pair(0, 0);
        this.maximumTemp = -1000;
    }

    public void set(Integer day, DayTemperatureInfo dayInfo) {
        info.put(day, dayInfo);
    }


    public Map<Integer, DayTemperatureInfo> getInfo() {
        return this.info;
    }

    public void updateAverage(int temperature) {
        double numOfDays = (this.averageInfo.getSecond());
        var oldAverage = this.averageInfo.getFirst();
        double newAverage = ((oldAverage * numOfDays) + temperature) / (numOfDays + 1);
        int counter = (int) numOfDays + 1;
        this.averageInfo = new Pair(newAverage, counter);
    }

    public Double getAverageTemp() {
        var information = this.averageInfo;
        return information.getFirst();
    }

    public void updateMaxValue(int temperature) {
        this.maximumTemp = Math.max(temperature, this.maximumTemp);
    }

    public Integer getMaxTemperature() {
        return this.maximumTemp;
    }
}
