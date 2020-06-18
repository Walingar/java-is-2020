package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return students.stream().map(Student::getFirstName).collect(Collectors.toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return students.stream().map(Student::getLastName).collect(Collectors.toList());
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return students.stream().map(Student::getGroup).collect(Collectors.toList());
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
        return students
                .stream()
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students
                .stream()
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return students
                .stream()
                .filter(s -> s.getFirstName().equals(name))
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return students
                .stream()
                .filter(s -> s.getLastName().equals(name))
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return students
                .stream()
                .filter(s -> s.getGroup().equals(group))
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students
                .stream()
                .filter(s -> s.getGroup().equals(group))
                .sorted(Comparator.comparing(s -> s.getLastName() + " " + s.getFirstName() + " " + s.getId()))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(Comparable::compareTo)));
    }
}
