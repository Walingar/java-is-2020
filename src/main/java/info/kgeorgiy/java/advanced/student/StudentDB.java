package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StudentDB implements StudentQuery {
    private static final Comparator<Student> COMPARATOR = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);


    private static <T> Stream<T> selectStream(List<Student> students, Function<Student, T> selector) {
        return students.stream().map(selector);
    }

    private static <T> List<T> select(List<Student> students, Function<Student, T> selector) {
        return selectStream(students, selector).collect(Collectors.toList());
    }

    private static List<Student> sort(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    private static List<Student> filter(Collection<Student> students, Predicate<Student> filter) {
        return applyFilter(students, filter).collect(Collectors.toList());
    }

    private static Stream<Student> applyFilter(Collection<Student> students, Predicate<Student> filter){
        return students.stream().filter(filter);
    }


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
        return students.stream()
                .map(student -> student.getFirstName() + " " + student.getLastName())
                .collect(toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return selectStream(students, Student::getFirstName).collect(toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream()
                .min(Student::compareTo)
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sort(students, Comparator.comparing(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sort(students, COMPARATOR);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(filter(students, student -> student.getFirstName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return sortStudentsByName(filter(students, student -> student.getLastName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(filter(students, student -> student.getGroup().equals(group)));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return applyFilter(students, student -> student.getGroup().equals(group))
                .collect(toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }
}
