package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery{

    private static final Comparator<? super Student> STUDENT_COMPARATOR = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return toList(Student::getFirstName, students);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return toList(Student::getLastName, students);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return toList(Student::getGroup, students);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return toList(it -> it.getFirstName() + " " + it.getLastName(), students);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return toSet(Student::getFirstName, students);
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
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return toSortedList(students);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return toSortedList(filterByValue(students, Student::getFirstName, name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return toSortedList(filterByValue(students, Student::getLastName, name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return toSortedList(filterByValue(students, Student::getGroup, group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> group.equals(student.getGroup()))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }

    private List<String> toList(Function<Student, String> mapper, Collection<Student> students){
        return students.stream().map(mapper).collect(Collectors.toList());
    }

    private Set<String> toSet(Function<Student, String> mapper, Collection<Student> students){
        return students.stream().map(mapper).collect(Collectors.toSet());
    }

    private List<Student> toSortedList(Collection<Student> students) {
        return students.stream()
                .sorted(STUDENT_COMPARATOR)
                .collect(Collectors.toList());
    }

    private List<Student> filterByValue(Collection<Student> students, Function<Student, String> filter, String value) {
        return students.stream()
                .filter(student -> filter.apply(student).equals(value))
                .collect(Collectors.toList());
    }

}
