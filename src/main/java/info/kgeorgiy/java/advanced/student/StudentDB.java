package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collector;
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
        return students.stream()
                .map(student -> student.getFirstName().concat(" ").concat(student.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream().map(Student::getFirstName).collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.size() == 0 ? "" : students.stream()
                .min(Comparator.comparingInt(Student::getId)).get().getFirstName();
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream().sorted(
                Comparator.comparing(Student::getLastName)
                        .thenComparing(Comparator.comparing(Student::getFirstName)
                                .thenComparing(Student::getId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return students.stream().filter(student -> student.getFirstName().equals(name))
                .sorted(Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return students.stream().filter(student -> student.getLastName().equals(name))
                .sorted(Comparator.comparing(Student::getFirstName)
                        .thenComparing(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return students.stream().filter(student -> student.getGroup().equals(group))
                .sorted(Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName)
                        .thenComparing(Student::getId))
                .collect(Collectors.toList());
    }

    //redo
    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        final Map<String, String> result = new HashMap<>();

        findStudentsByGroup(students, group).stream().forEach((student) ->
                result.merge(student.getLastName(), student.getFirstName(), BinaryOperator.minBy(Comparable::compareTo)));

        return result;
    }

}

