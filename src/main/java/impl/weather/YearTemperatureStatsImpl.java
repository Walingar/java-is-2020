package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

import static java.lang.Math.max;

public class YearTemperatureStatsImpl implements YearTemperatureStats {

    private Map<Month, ArrayList<DayTemperatureInfo>> temperature_info;
    private Map<Month, ArrayList<Integer>> update_times;
    private ArrayList<Double> average_temperature_per_month;
    private ArrayList<Integer> maximal_temperature_per_month;
    private int[] days_known_per_month;
    private int last_update;

    public YearTemperatureStatsImpl() {
        temperature_info = new HashMap<>();
        update_times = new HashMap<>();
        last_update = 0;
        int max_month_index = 12 + 1;
        average_temperature_per_month = new ArrayList<>(Collections.nCopies(max_month_index, null));
        maximal_temperature_per_month = new ArrayList<>(Collections.nCopies(max_month_index, null));
        days_known_per_month = new int[max_month_index];
    }

    @Override
    public void updateStats(DayTemperatureInfo info) {
        int month = info.getMonth().getValue();
        int day = info.getDay();
        int temperature = info.getTemperature();

        temperature_info.putIfAbsent(info.getMonth(),
                new ArrayList<>(Collections.nCopies(info.getMonth().maxLength() + 1, null)));
        update_times.putIfAbsent(info.getMonth(),
                new ArrayList<>(Collections.nCopies(info.getMonth().maxLength() + 1, null)));

        days_known_per_month[month] += 1;

        Double cur_avg = average_temperature_per_month.get(month);
        average_temperature_per_month.set(month, cur_avg == null ? temperature : cur_avg + temperature);

        Integer cur_max = maximal_temperature_per_month.get(month);
        maximal_temperature_per_month.set(month, cur_max == null ? temperature : max(cur_max, temperature));

        temperature_info.get(info.getMonth()).set(day, info);
        update_times.get(info.getMonth()).set(day, last_update++);
    }

    @Override
    public Double getAverageTemperature(Month month) {
        Double average = average_temperature_per_month.get(month.getValue());
        if (average == null) {
            return null;
        }
        return average / days_known_per_month[month.getValue()];
    }

    @Override
    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> max_temperatures = new HashMap<>();
        for (Month month : temperature_info.keySet()) {
            max_temperatures.put(month, maximal_temperature_per_month.get(month.getValue()));
        }
        return max_temperatures;
    }

    @Override
    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        List<DayTemperatureInfo> sorted_temperatures = temperature_info.get(month);
        if (sorted_temperatures == null) {
            return new ArrayList<DayTemperatureInfo>();
        }
        sorted_temperatures.removeAll(Collections.singleton(null));
        sorted_temperatures.sort(new Comparator<DayTemperatureInfo>() {
            @Override
            public int compare(DayTemperatureInfo lhs, DayTemperatureInfo rhs) {
                if (lhs.getTemperature() == rhs.getTemperature()) {
                    return Integer.compare(update_times.get(month).get(lhs.getDay()),
                            update_times.get(month).get(rhs.getDay()));
                }
                return Integer.compare(lhs.getTemperature(), rhs.getTemperature());
            }
        });
        return sorted_temperatures;
    }

    @Override
    public DayTemperatureInfo getTemperature(int day, Month month) {
        ArrayList<DayTemperatureInfo> days_info = temperature_info.getOrDefault(month, null);
        if (days_info == null)
            return null;
        return days_info.get(day);
    }
}
