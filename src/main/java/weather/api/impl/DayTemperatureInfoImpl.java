package weather.api.impl;

import java.time.Month;
import weather.api.DayTemperatureInfo;

public class DayTemperatureInfoImpl implements DayTemperatureInfo {

  private int day;
  private final Month month;
  private int temperature;

  public DayTemperatureInfoImpl(int day, Month month, int temperature) {
    this.day = day;
    this.month = month;
    this.temperature = temperature;
  }

  @Override
  public int getDay() {
    return day;
  }

  @Override
  public Month getMonth() {
    return month;
  }

  @Override
  public int getTemperature() {
    return temperature;
  }
}
