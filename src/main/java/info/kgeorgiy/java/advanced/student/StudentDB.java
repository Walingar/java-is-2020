package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return students.stream()
                .map(student -> student.getFirstName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return students.stream()
                .map(student -> student.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return students.stream()
                .map(student -> student.getGroup())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return students.stream()
                .map(student -> student.getFirstName() + " " + student.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new HashSet<>(getFirstNames(students));
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

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup() == group)
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }
}