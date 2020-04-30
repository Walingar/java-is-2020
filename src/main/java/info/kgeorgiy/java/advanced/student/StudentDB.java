package info.kgeorgiy.java.advanced.student;

import java.util.*;

import static java.util.stream.Collectors.*;

public class StudentDB implements StudentQuery {
    private static final Comparator<? super Student> COMPARATOR = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return students.stream()
                .map(Student::getLastName)
                .collect(toList());
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return students.stream()
                .map(Student::getGroup)
                .collect(toList());
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return students.stream()
                .map(student -> student.getFirstName() + " " + student.getLastName())
                .collect(toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(toSet());
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
        return students.stream()
                .sorted(COMPARATOR)
                .collect(toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getFirstName().equals(name))
                .sorted(COMPARATOR)
                .collect(toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getLastName().equals(name))
                .sorted(COMPARATOR)
                .collect(toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .sorted(COMPARATOR)
                .collect(toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }
}
