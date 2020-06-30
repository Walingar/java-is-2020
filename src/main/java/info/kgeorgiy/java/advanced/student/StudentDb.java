package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

public class StudentDb implements StudentQuery {

    private static final Comparator<? super Student> STUDENT_NAME_COMPARATOR = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return studentsToStrings(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return studentsToStrings(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return studentsToStrings(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return studentsToStrings(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    private List<String> studentsToStrings(List<Student> students, Function<? super Student, String> mapping) {
        return students.stream().map(mapping).collect(toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(toCollection(TreeSet::new));
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
        return students.stream()
                .sorted()
                .collect(toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return filterStudentsAndSortByName(students, student -> true);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return filterStudentsAndSortByName(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return filterStudentsAndSortByName(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return filterStudentsAndSortByName(students, student -> student.getGroup().equals(group));
    }

    private List<Student> filterStudentsAndSortByName(Collection<Student> students, Predicate<? super Student> filter) {
        return students.stream().filter(filter).sorted(STUDENT_NAME_COMPARATOR).collect(toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }
}