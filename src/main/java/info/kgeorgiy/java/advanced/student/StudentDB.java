package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getListOfValues(students, Collections.singletonList(Student::getFirstName));
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getListOfValues(students, Collections.singletonList(Student::getLastName));
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getListOfValues(students, Collections.singletonList(Student::getGroup));
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getListOfValues(students, Arrays.asList(Student::getFirstName, Student::getLastName));
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new HashSet<>(getFirstNames(students));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream()
                .min(Comparator.comparingInt(Student::getId))
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return getListOfSortedStudentsByNames(students);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return getListOfSortedStudentsByNames(getListOfStudentsByValue(students, Student::getFirstName, name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return getListOfSortedStudentsByNames(getListOfStudentsByValue(students, Student::getLastName, name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return getListOfSortedStudentsByNames(getListOfStudentsByValue(students, Student::getGroup, group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(Comparator.naturalOrder())));
    }

    private List<String> getListOfValues(List<Student> students, List<Function<Student, String>> functions) {
        return students.stream()
                .map(student ->
                        functions.stream()
                                .map(function ->
                                        function.apply(student))
                                .collect(Collectors.joining(" "))
                ).collect(Collectors.toList());
    }

    private List<Student> getListOfSortedStudentsByNames(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName)
                        .thenComparing(Student::getId))
                .collect(Collectors.toList());
    }

    private List<Student> getListOfStudentsByValue(Collection<Student> students, Function<Student, String> function, String value) {
        return students.stream()
                .filter(student ->
                        function.apply(student).equals(value))
                .collect(Collectors.toList());
    }
}
