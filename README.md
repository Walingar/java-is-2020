# Домашние задания по курсу "Язык программирования Java"

# Домашнее задание 3. Статистика погоды

Ваша задача разработать реализации интерфейсов `YearTemperatureStats` и `YearTemperatureStatsParser` и
создать их в методах `impl.weather.YearTemperatureStatsFactory.getInstance` и `impl.weather.YearTemperatureStatsParserFactory.getInstance` соответственно.

`YearTemperatureStats` должен уметь возвращать:
  * температуру за данный день месяца (или `null` если о дне ничего не известно); (константное время работы)
  * среднюю температуру за данный месяц (или `null` если о месяце ничего не известно); (константное время работы)
  * максимальную температуру для каждого месяца, о котором есть данные; (время работы `O(число известных месяцев)`)
  * список дней данного месяца, о которых есть данные.
    Список должен быть отсортирован по невозрастанию температуры. 
    Если в два дня температура была одинаковой, первым должен идти тот, данные о котором пришли первые.

`YearTemperatureStats` должен уметь обновлять свое состояние в зависимости от данного `DayTemperatureInfo` (за константное время).

`YearTemperatureStatsParser` должен уметь возвращать `YearTemperatureStats` из сырых данных - `Collection<String>`.
Каждая строка имеет вид `day.month temperature`.