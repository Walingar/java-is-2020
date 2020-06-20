package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    private static final String EMPTY = "";
    private static final Comparator<Student> COMPARATOR_BY_ID = Comparator.comparing(Student::getId);
    private static final Comparator<Student> COMPARATOR_BY_NAME =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getId);

    private List<String> getResult(final List<Student> students, Function<Student, String> mapFunc) {
        return students.stream()
                .map(mapFunc)
                .collect(Collectors.toList());
    }

    private List<Student> sortBy(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Student> findBy(Collection<Student> students, Function<Student, String> mapFunc, String name) {
        return students.stream()
                .filter(student -> mapFunc.apply(student).equals(name))
                .sorted(COMPARATOR_BY_NAME)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getResult(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getResult(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getResult(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getResult(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream().map(Student::getFirstName).collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse(EMPTY);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortBy(students, COMPARATOR_BY_ID);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortBy(students, COMPARATOR_BY_NAME);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findBy(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findBy(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return findBy(students, Student::getGroup, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream().filter(student -> group.equals(student.getGroup())).collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }
}