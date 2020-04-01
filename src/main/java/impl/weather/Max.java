package impl.weather;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Max {
    private final Map<Month, Integer> maximumTemperature = new HashMap<>();

    public void updateMaxValue(Month month, int temperature) {
        var max = maximumTemperature.getOrDefault(month, null);
        if (max != null) {
            if (max < temperature) {
                maximumTemperature.replace(month, max, temperature);
            }
        } else {
            maximumTemperature.put(month, temperature);
        }
    }

    public Map<Month, Integer> getMaxTemperature() {
        return this.maximumTemperature;
    }
}
