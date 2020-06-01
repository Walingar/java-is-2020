package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    private static final Comparator<Student> sortComparator =
            Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName).
                    thenComparing(Student::getId);

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return select(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return select(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return select(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return select(students, StudentDB::getStudentFullName);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return selectDistinct(students, Student::getFirstName);
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return min(students, Student::getFirstName);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return orderBy(students, Comparator.comparing(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return orderBy(students, sortComparator);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(filter(students, student -> student.getFirstName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return orderBy(filter(students, student -> student.getLastName().equals(name)),
                Comparator.comparing(Student::getLastName));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(filter(students, student -> student.getGroup().equals(group)));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return toMap(findStudentsByGroup(students, group), Student::getLastName, Student::getFirstName,
                (lastName, firstName) -> lastName.compareTo(firstName) < 0 ? lastName : firstName);
    }

    private static List<String> select(Collection<Student> collection, Function<Student, String> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    private static Set<String> selectDistinct(Collection<Student> collection, Function<Student, String> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    private static String min(Collection<Student> collection, Function<Student, String> selector) {
        return collection.stream().min(Student::compareTo).map(selector).orElse("");
    }

    private static List<Student> orderBy(Collection<Student> collection, Comparator<Student> comparator) {
        return collection.stream().sorted(comparator).collect(Collectors.toList());
    }

    private static List<Student> filter(Collection<Student> collection, Predicate<Student> predicate) {
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    private static Map<String, String> toMap(Collection<Student> collection,
                                             Function<Student, String> keySelector,
                                             Function<Student, String> valueSelector,
                                             BinaryOperator<String> mergeFunction) {
        return collection.stream().collect(Collectors.toMap(keySelector, valueSelector, mergeFunction));
    }

    private static String getStudentFullName(Student student) {
        return student.getFirstName() + " " + student.getLastName();
    }
}
