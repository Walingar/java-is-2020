package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentQueryImpl implements StudentQuery {


    @Override
    public List<String> getFirstNames(List<Student> students) {
        return (List<String>) getMappedCollection(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return (List<String>) getMappedCollection(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return (List<String>) getMappedCollection(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return (List<String>) getMappedCollection(students, s -> s.getFirstName() + " " + s.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new HashSet<>(getMappedCollection(students, Student::getFirstName));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return sortStudentsById(students).stream()
                .map(Student::getFirstName)
                .findFirst()
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return (List<Student>) getSortedCollection(students, Comparator.comparingInt(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return (List<Student>) getSortedCollection(students, Comparator.comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId));
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(getFilteredCollection(students, s -> s.getFirstName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return sortStudentsByName(getFilteredCollection(students, s -> s.getLastName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(getFilteredCollection(students, s -> s.getGroup().equals(group)));
    }


    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(
                        Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String.CASE_INSENSITIVE_ORDER)
                        )
                );
    }


    private <T, R> Collection<R> getMappedCollection(Collection<T> collection, Function<T, R> function) {
        return collection.stream()
                .map(function)
                .collect(Collectors.toList());
    }

    private <T> Collection<T> getFilteredCollection(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private <T> Collection<T> getSortedCollection(Collection<T> collection, Comparator<T> comparator) {
        return collection.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}
