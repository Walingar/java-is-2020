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

    private Stream<String> of(List<Student> students, Function<Student, String> getter) {
        return of(students).map(getter);
    }

    private Optional<Student> min(Stream<Student> stream) {
        return stream.min(Comparator.comparingInt(Student::getId));
    }

    private Stream<Student> sorted(Stream<Student> stream) {
        return stream.sorted();
    }

    private Stream<Student> sorted(Stream<Student> stream, Comparator<Student> comparator) {
        return stream.sorted(comparator);
    }

    private Stream<Student> filtered(Stream<Student> stream, Function<Student, String> getter, String value) {
        return stream.filter(s -> getter.apply(s).equals(value));
    }

    private Map<String, String> mapOf(Stream<Student> stream,
                                      Function<Student, String> keyGetter,
                                      Function<Student, String> valueGetter,
                                      BinaryOperator<String> merger) {
        return stream.collect(Collectors.toMap(keyGetter, valueGetter, merger));
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return list(of(students, Student::getFirstName));
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return list(of(students, Student::getLastName));
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return list(of(students, Student::getGroup));
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return list(of(students, this::getFullName));
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return set(of(students, Student::getFirstName));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return min(of(students))
                .map(Student::getFirstName)
                .orElse(DEFAULT_EMPTY_STRING);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return list(sorted(of(students)));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return list(
                sorted(
                        of(students),
                        BY_NAME));
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return list(
                sorted(
                        filtered(of(students),
                                Student::getFirstName, name),
                        BY_NAME));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return list(
                sorted(
                        filtered(of(students),
                                Student::getLastName, name),
                        BY_NAME));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return list(
                sorted(
                        filtered(of(students),
                                Student::getGroup, group),
                        BY_NAME));
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


    private static <T> List<T> list(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    private static <T> Set<T> set(Stream<T> stream) {
        return stream.collect(Collectors.toSet());
    }
}
