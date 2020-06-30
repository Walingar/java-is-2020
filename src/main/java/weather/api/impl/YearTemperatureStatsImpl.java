package weather.api.impl;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import weather.api.DayTemperatureInfo;
import weather.api.YearTemperatureStats;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

  private final Map<Month, List<DayTemperatureInfo>> temperatureInfo = new HashMap<>();
  private final Map<Month, MonthStats> monthStats = new HashMap<>();

  @Override
  public void updateStats(DayTemperatureInfo info) {
    MonthStats monthStats = this.monthStats.get(info.getMonth());
    if (Objects.isNull(monthStats)) {
      monthStats = new MonthStats();
    }
    monthStats.updateMonthStats(info);
    this.monthStats.put(info.getMonth(), monthStats);

    List<DayTemperatureInfo> dayTemperatureInfos = temperatureInfo.get(info.getMonth());
    if (Objects.isNull(dayTemperatureInfos)) {
      dayTemperatureInfos = new ArrayList<>(Collections.nCopies(32, null));
    }
    dayTemperatureInfos.set(info.getDay() - 1, info);
    temperatureInfo.put(info.getMonth(), dayTemperatureInfos);
  }

  @Override
  public Double getAverageTemperature(Month month) {
    if (Objects.isNull(monthStats.get(month))) {
      return null;
    }

    return monthStats.get(month).getAverageTemperature();
  }

  @Override
  public Map<Month, Integer> getMaxTemperature() {
    Map<Month, Integer> output = new HashMap<>();
    monthStats.forEach((month, monthStats1) -> output.put(month, monthStats1.getMaxTemperature()));
    return output;
  }

  @Override
  public List<DayTemperatureInfo> getSortedTemperature(Month month) {
    if (Objects.isNull(monthStats.get(month))) {
      return Collections.emptyList();
    }

    return temperatureInfo.get(month).stream()
        .filter(obj -> !Objects.isNull(obj))
        .sorted((a1, a2) -> {
          if (a1.getTemperature() == a2.getTemperature()) {
            return this.monthStats.get(month).compareEquals(a1, a2);
          }

          return a1.getTemperature() - a2.getTemperature();
        })
        .collect(Collectors.toList());
  }

  @Override
  public DayTemperatureInfo getTemperature(int day, Month month) {
    if (Objects.isNull(temperatureInfo.get(month))) {
      return null;
    }

    return temperatureInfo.get(month).get(day - 1);
  }

  private static class MonthStats {

    private double summarizedTemperature = 0;
    private int maxTemperature = Integer.MIN_VALUE;
    private int days = 0;
    private LinkedList<DayTemperatureInfo> queue = new LinkedList<>();

    public void updateMonthStats(DayTemperatureInfo averageTemperature) {
      summarizedTemperature += averageTemperature.getTemperature();
      days += 1;
      maxTemperature = Math.max(maxTemperature, averageTemperature.getTemperature());
      queue.add(averageTemperature);
    }

    public int getMaxTemperature() {
      return maxTemperature;
    }

    public double getAverageTemperature() {
      return summarizedTemperature / days;
    }

    public int compareEquals(DayTemperatureInfo d1, DayTemperatureInfo d2) {
      return queue.indexOf(d1) - queue.indexOf(d2);
    }
  }
}
