package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentQueryImpl implements StudentQuery {
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getSpecified(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getSpecified(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getSpecified(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getSpecified(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream().map(Student::getFirstName).collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return sortStream(students, Comparator.comparingInt(Student::getId))
                .map(Student::getFirstName)
                .limit(1).collect(Collectors.joining());
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStream(students, Comparator.comparingInt(Student::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStream(students, Comparator.comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(findByParameter(students, name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return sortStudentsByName(findByParameter(students, name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(findByParameter(students, group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(Comparable::compareTo)));
    }

    private <T> List<T> getSpecified(List<Student> students, Function<Student, T> parameter) {
        return students.stream().map(parameter).collect(Collectors.toList());
    }

    private List<Student> findByParameter(Collection<Student> students, String parameter) {
        return students.stream().filter(student -> student.getGroup().equals(parameter)).collect(Collectors.toList());
    }

    private Stream<Student> sortStream(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator);
    }
}
