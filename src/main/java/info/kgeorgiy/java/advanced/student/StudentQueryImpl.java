package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentQueryImpl implements StudentQuery {


    @Override
    public List<String> getFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return students.stream()
                .map(Student::getLastName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return students.stream()
                .map(Student::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return sortStudentsById(students).stream()
                .findFirst()
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream().
                sorted(Comparator.comparingInt(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream()
                .sorted(STUDENT_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(
                students.stream()
                        .filter(s -> s.getFirstName().equals(name))
                        .collect(Collectors.toList()));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return sortStudentsByName(
                students.stream()
                        .filter(s -> s.getLastName().equals(name))
                        .collect(Collectors.toList()));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(
                students.stream()
                        .filter(s -> s.getGroup().equals(group))
                        .collect(Collectors.toList()));
    }


    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(
                        Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String.CASE_INSENSITIVE_ORDER)
                        )
                );
    }


    private static final Comparator<Student> STUDENT_COMPARATOR = (a, b) -> {
        final int last = a.getLastName().compareTo(b.getLastName());
        if (last != 0) {
            return last;
        }
        final int first = a.getFirstName().compareTo(b.getFirstName());
        if (first != 0) {
            return first;
        }
        return Integer.compare(a.getId(), b.getId());
    };


    private <T, A, R> Collection<R> getMappedCollection(Collection<T> collection, Function<T, R> function, Collector<R, A, R> collectTo) {
        return (Collection<R>) collection.stream()
                .map(function)
                .collect(collectTo);
    }

    private <T> Collection<T> getFilteredCollection(Collection<T> collection, Predicate<T> predicate, Collector collectTo) {
        return (Collection<T>) collection.stream()
                .filter(predicate)
                .collect(collectTo);
    }

    private <T> Collection<T> getSortedCollection(Collection<T> collection, Comparator<T> comparator, Collector collectTo) {
        return (Collection<T>) collection.stream()
                .sorted(comparator)
                .collect(collectTo);
    }


}
