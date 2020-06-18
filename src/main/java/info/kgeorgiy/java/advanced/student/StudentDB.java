package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getInfo(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getInfo(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getInfo(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return students
                .stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students
                .stream()
                .distinct()
                .map(Student::getFirstName)
                .collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students
                .stream()
                .sorted(Comparator.comparing(Student::getId))
                .map(Student::getFirstName)
                .limit(1)
                .collect(Collectors.joining());
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return getSortedList(students, Comparator.comparing(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return getSortedList(students, Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()));
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return getFilteredSortedList(students, s -> s.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return getFilteredSortedList(students, s -> s.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return getFilteredSortedList(students, s -> s.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students
                .stream()
                .filter(s -> s.getGroup().equals(group))
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(Comparable::compareTo)));
    }

    private <T> List<T> getInfo(List<Student> students, Function<Student, T> info) {
        return students.stream().map(info).collect(Collectors.toList());
    }

    private List<Student> getSortedList(Collection<Student> students, Comparator<Student> comparator) {
        return students
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Student> getFilteredSortedList(Collection<Student> students, Predicate<Student> predicate) {
        return students
                .stream()
                .filter(predicate)
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toList());

    }
}
