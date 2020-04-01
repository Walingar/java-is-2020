package impl.weather;


import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Average {
    private final Map<Month, Pair> averageTemperature = new HashMap<>();

    public void updateAverage(Month month, int temperature) {
        var average = averageTemperature.getOrDefault(month, null);
        if (average != null) {
            double numOfDays = (average.getSecond());
            var oldaverage = average.getFirst();
            double newAverage = ((oldaverage * numOfDays) + temperature) / (numOfDays + 1);
            int counter = (int) numOfDays + 1;
            averageTemperature.replace(month, average, new Pair(newAverage, counter));
        } else {
            averageTemperature.put(month, new Pair(temperature, 1));
        }
    }

    public Double getAverageTemp(Month month) {
        if (averageTemperature.getOrDefault(month, null) == null) {
            return null;
        }
        var information = averageTemperature.getOrDefault(month, null);
        return information.getFirst();
    }
}
