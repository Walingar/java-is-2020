package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {

    private final static Comparator<? super Student> studentComparator = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);

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
                .map(student -> student.getFirstName()+ " " + student.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return getFirstNames(students).stream()
                .collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        var minIdStudent = students.stream()
                .sorted(Comparator.comparingInt(Student::getId))
                .findFirst()
                .orElse(null);
        return minIdStudent != null ? minIdStudent.getFirstName() : "";
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream()
                .sorted(studentComparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getFirstName() == name)
                .sorted(studentComparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getLastName() == name)
                .sorted(studentComparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup() == group)
                .sorted(studentComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup() == group)
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }
}