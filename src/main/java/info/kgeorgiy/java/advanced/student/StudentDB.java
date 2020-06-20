package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {

    private static final String DEFAULT_EMPTY_STRING = "";

    private final Comparator<Student> BY_NAME = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparingInt(Student::getId);

    private String getFullName(Student s) {
        return String.format("%s %s", s.getFirstName(), s.getLastName());
    }

    private Stream<Student> of(Collection<Student> students) {
        return students.stream();
    }

    private Stream<String> extract(List<Student> students, Function<Student, String> getter) {
        return of(students).map(getter);
    }

    private List<String> listOf(List<Student> students, Function<Student, String> getter) {
        return extract(students, getter).collect(Collectors.toList());
    }

    private Set<String> setOf(List<Student> students, Function<Student, String> getter) {
        return extract(students, getter).collect(Collectors.toSet());
    }

    private Optional<Student> min(Stream<Student> stream) {
        return stream.min(Comparator.comparingInt(Student::getId));
    }

    private List<Student> sorted(Stream<Student> stream) {
        return stream.sorted().collect(Collectors.toList());
    }

    private List<Student> sorted(Stream<Student> stream, Comparator<Student> comparator) {
        return stream.sorted(comparator).collect(Collectors.toList());
    }

    private Stream<Student> filtered(Stream<Student> stream, Function<Student, String> getter, String value) {
        return stream.filter(s -> getter.apply(s).equals(value));
    }

    private List<Student> sortFilteredByName(Collection<Student> students, Function<Student, String> getter, String model) {
        return sorted(
                filtered(of(students),
                        getter, model),
                BY_NAME);
    }

    private Map<String, String> mapOf(Stream<Student> stream,
                                      Function<Student, String> keyGetter,
                                      Function<Student, String> valueGetter,
                                      BinaryOperator<String> merger) {
        return stream.collect(Collectors.toMap(keyGetter, valueGetter, merger));
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return listOf(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return listOf(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return listOf(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return listOf(students, this::getFullName);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return setOf(students, Student::getFirstName);
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return min(of(students))
                .map(Student::getFirstName)
                .orElse(DEFAULT_EMPTY_STRING);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sorted(of(students));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sorted(of(students), BY_NAME);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortFilteredByName(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return sortFilteredByName(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortFilteredByName(students, Student::getGroup, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return mapOf(
                filtered(
                        of(students),
                        Student::getGroup, group),
                Student::getLastName,
                Student::getFirstName,
                BinaryOperator.minBy(Comparable::compareTo));
    }
}
