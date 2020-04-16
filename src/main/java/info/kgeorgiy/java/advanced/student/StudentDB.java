package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    public List<String> getFirstNames(final List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(Collectors.toList());
    }

    public List<String> getLastNames(final List<Student> students) {
        return students.stream()
                .map(Student::getLastName)
                .collect(Collectors.toList());
    }

    public List<String> getGroups(final List<Student> students) {
        return students.stream()
                .map(Student::getGroup)
                .collect(Collectors.toList());
    }

    public List<String> getFullNames(final List<Student> students) {
        return students.stream()
                .map(student -> student.getFirstName() + " " + student.getLastName())
                .collect(Collectors.toList());
    }

    public Set<String> getDistinctFirstNames(final List<Student> students) {
        return new TreeSet<>(getFirstNames(students));
    }

    public String getMinStudentFirstName(final List<Student> students) {
        var min_student = students.stream()
                .min(Comparator.comparing(Student::getId))
                .orElse(null);

        return (min_student != null) ? min_student.getFirstName() : "";
    }

    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparing(Student::getLastName).
                        thenComparing(Student::getFirstName).
                        thenComparing(Student::getId))
                .collect(Collectors.toList());
    }

    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getFirstName().equals(name))
                .sorted(Comparator.comparing(Student::getLastName))
                .collect(Collectors.toList());
    }

    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getLastName().equals(name))
                .sorted(Comparator.comparing(Student::getFirstName))
                .collect(Collectors.toList());
    }

    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toList()));
    }

    public Map<String, String> findStudentNamesByGroup(final Collection<Student> students, final String group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(
                        Student::getLastName, Student::getFirstName,
                        BinaryOperator.minBy(String.CASE_INSENSITIVE_ORDER)));
    }
}