package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import javax.swing.*;
import java.time.Month;
import java.util.*;

public class YearTemperatureStatsFactory {
    public final static YearTemperatureStats getInstance() {
        return new YearTemperatureStatsImpl();
    }
}