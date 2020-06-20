package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return selectField(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return selectField(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return selectField(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return selectField(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return selectFieldStream(students, Student::getFirstName).collect(Collectors.toSet());
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
        return selectSortedField(students, Comparator.comparing(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return selectSortedField(students, STUDENT_COMPARATOR);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(selectFilteredField(students, student -> student.getFirstName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return selectFilteredField(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(selectFilteredField(students, student -> student.getGroup().equals(group)));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return applyFilterToStream(students, student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }

    private static final Comparator<Student> STUDENT_COMPARATOR = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);

    private static <T> List<T> selectField(List<Student> students, Function<Student, T> selector) {
        return selectFieldStream(students, selector).collect(Collectors.toList());
    }

    private static <T> Stream<T> selectFieldStream(List<Student> students, Function<Student, T> selector) {
        return students.stream().map(selector);
    }

    private static List<Student> selectSortedField(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    private static List<Student> selectFilteredField(Collection<Student> students, Predicate<Student> filter) {
        return applyFilterToStream(students, filter).collect(Collectors.toList());
    }

    private static Stream<Student> applyFilterToStream(Collection<Student> students, Predicate<Student> filter){
        return students.stream().filter(filter);
    }

}
