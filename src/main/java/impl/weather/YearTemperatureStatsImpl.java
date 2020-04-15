package impl.weather;

import api.weather.DayTemperatureInfo;
import api.weather.YearTemperatureStats;

import java.time.Month;
import java.util.*;

public class YearTemperatureStatsImpl implements YearTemperatureStats {
    static private class MonthTemperatureItem {
        public MonthTemperatureItem(Double avg, Integer max, Integer count) {
            this.avg = avg;
            this.max = max;
            this.count = count;
        }

        public Double avg;
        public Integer max;
        public Integer count;
    }

    static private class OrderedDayTemperatureInfo {
        public OrderedDayTemperatureInfo(DayTemperatureInfo info, int order) {
            this.info = info;
            this.order = order;
        }

        public DayTemperatureInfo info;
        public int order;
    }

    static public class OrderedDayTemperatureInfoComparator implements Comparator<OrderedDayTemperatureInfo> {
        public int compare(OrderedDayTemperatureInfo a, OrderedDayTemperatureInfo b) {
            if (a.info.getTemperature() < b.info.getTemperature()) {
                return -1;
            }

            if (a.info.getTemperature() > b.info.getTemperature()) {
                return 1;
            }

            return Integer.compare(a.order, b.order);
        }
    }

    private Map<Month, MonthTemperatureItem> temp_info_per_mon;
    private Map<Month, Map<Integer, OrderedDayTemperatureInfo>> temp_per_day;
    private int add_order_count;

    public YearTemperatureStatsImpl() {
        temp_info_per_mon = new HashMap<>();
        temp_per_day = new HashMap<>();
        add_order_count = 0;
    }

    public void updateStats(DayTemperatureInfo info) {
        MonthTemperatureItem temp_info = temp_info_per_mon.get(info.getMonth());

        if (temp_info == null) {
            temp_info = new MonthTemperatureItem(0.0, null, 0);
        }

        temp_info.avg = (temp_info.avg * temp_info.count + info.getTemperature()) / (temp_info.count + 1);
        temp_info.max = (temp_info.max == null || temp_info.max < info.getTemperature()) ? info.getTemperature()
                                                                                         : temp_info.max;
        temp_info.count++;
        temp_info_per_mon.put(info.getMonth(), temp_info);

        Map<Integer, OrderedDayTemperatureInfo> month_info = temp_per_day.get(info.getMonth());

        if (month_info == null) {
            month_info = new HashMap<>();
        }

        month_info.put(info.getDay(), new OrderedDayTemperatureInfo(info, add_order_count));
        add_order_count++;
        temp_per_day.put(info.getMonth(), month_info);

    }

    public Double getAverageTemperature(Month month) {
        MonthTemperatureItem temp_info = temp_info_per_mon.get(month);

        if (temp_info == null) {
            return null;
        }

        return temp_info.avg;
    }

    public Map<Month, Integer> getMaxTemperature() {
        Map<Month, Integer> map = new HashMap<>();

        for (Map.Entry<Month, MonthTemperatureItem> entry : temp_info_per_mon.entrySet()) {
            map.put(entry.getKey(), entry.getValue().max);
        }

        return map;
    }

    public List<DayTemperatureInfo> getSortedTemperature(Month month) {
        List<DayTemperatureInfo> list = new LinkedList<>();
        Map<Integer, OrderedDayTemperatureInfo> month_info = temp_per_day.get(month);

        if (month_info == null) {
            return list;
        }

        Set<OrderedDayTemperatureInfo> sorted_info =
                new TreeSet<>(new OrderedDayTemperatureInfoComparator());

        sorted_info.addAll(month_info.values());

        for (OrderedDayTemperatureInfo info : sorted_info) {
            list.add(info.info);
        }

        return list;
    }

    public DayTemperatureInfo getTemperature(int day, Month month) {
        Map<Integer, OrderedDayTemperatureInfo> month_info = temp_per_day.get(month);

        if (month_info == null) {
            return null;
        }

        OrderedDayTemperatureInfo day_info = month_info.get(day);

        if (day_info == null) {
            return null;
        }

        return day_info.info;
    }
}