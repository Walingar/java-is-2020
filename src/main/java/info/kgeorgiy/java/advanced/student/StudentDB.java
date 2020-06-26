package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {
    private final static String DEFAULT_LAST_NAME = "";
    private static final Comparator<Student> STUDENT_NAME_COMPARATOR =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getId);

    private static final Comparator<Student> STUDENT_ID_COMPARATOR = Comparator.comparingInt(Student::getId);

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return mapStudentsToStrings(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return mapStudentsToStrings(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return mapStudentsToStrings(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return mapStudentsToStrings(students,
                student -> String.format("%s %s", student.getFirstName(), student.getLastName()));
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return mapStudentsToDistinctStrings(students, Student::getFirstName);
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream()
                .min(STUDENT_ID_COMPARATOR)
                .map(Student::getFirstName)
                .orElse(DEFAULT_LAST_NAME);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudentsByKey(students, STUDENT_ID_COMPARATOR);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudentsByKey(students, STUDENT_NAME_COMPARATOR);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return filterStudentsByField(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return filterStudentsByField(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return filterStudentsByField(students, student -> student.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(Comparable::compareTo)
                ));
    }

    private List<String> mapStudentsToStrings(Collection<Student> students, Function<Student, String> mapper) {
        return getStringStreamFromStudents(students, mapper).collect(Collectors.toList());
    }

    private Set<String> mapStudentsToDistinctStrings(Collection<Student> students, Function<Student, String> mapper) {
        return getStringStreamFromStudents(students, mapper).collect(Collectors.toSet());
    }

    private Stream<String> getStringStreamFromStudents(Collection<Student> students, Function<Student, String> mapper) {
        return students.stream().map(mapper);
    }

    private List<Student> filterStudentsByField(Collection<Student> students, Predicate<Student> condition) {
        return students.stream()
                .filter(condition)
                .sorted(STUDENT_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }


    private List<Student> sortStudentsByKey(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }
}
