package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    private static final String EMPTY = "";

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return students.stream().map(student -> student.getFirstName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return students.stream().map(student -> student.getLastName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return students.stream().map(student -> student.getGroup()).collect(Collectors.toList());
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return students.stream().map(student -> (student.getFirstName() + " " + student.getLastName())).collect(Collectors.toList());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream().map(student -> student.getFirstName()).collect(Collectors.toSet());
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse(EMPTY);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream().sorted(Comparator.comparing(Student::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream().sorted(Comparator.comparing(Student::getFirstName)).collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return students.stream().filter(student -> name.equals(student.getFirstName())).collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return students.stream().filter(student -> name.equals(student.getLastName())).collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return students.stream().filter(student -> group.equals(student.getGroup())).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream().filter(student -> group.equals(student.getGroup())).collect(Collectors.toMap(Student::getLastName, Student::getLastName));
    }
}
