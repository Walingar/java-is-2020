package info.kgeorgiy.java.advanced.student;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {

    private final static Comparator<Student> studentComparator = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return get(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return get(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return get(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return get(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new HashSet<>(getFirstNames(students));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sort(students, Comparator.comparingInt(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sort(students, studentComparator);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return sortStudentsByName(filter(students, student -> student.getFirstName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return order(filter(students, student -> student.getLastName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(filter(students, student -> student.getGroup().equals(group)));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, (a, b) -> a.compareTo(b) < 0 ? a : b));
    }

    private List<String> get(List<Student> students, Function<Student, String> mapper) {
        return students.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private List<Student> sort(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Student> order(Collection<Student> students) {
        return students.stream()
                .sorted(studentComparator)
                .collect(Collectors.toList());
    }

    private List<Student> filter(Collection<Student> students, Predicate<Student> predicate) {
        return order(students)
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}