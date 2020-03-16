package weather.factory;

import java.time.Month;
import weather.api.DayTemperatureInfo;
import weather.api.impl.DayTemperatureInfoImpl;

public class DayTemperatureInfoFactory {

  public static DayTemperatureInfo getInstance(int day, Month month, int temperature) {
    return new DayTemperatureInfoImpl(day, month, temperature);
  }
}
