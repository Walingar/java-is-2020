package info.kgeorgiy.java.advanced.student;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StudentDB implements StudentQuery {
    private static final Comparator<Student> STUDENT_NAME_COMPARATOR =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparingInt(Student::getId);

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return extractField(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return extractField(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return extractField(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return extractField(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return extractFieldStream(students, Student::getFirstName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream()
                .min(Comparator.naturalOrder())
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudentsBy(students, Comparator.naturalOrder());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudentsBy(students, STUDENT_NAME_COMPARATOR);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findStudentsByField(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findStudentsByField(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return findStudentsByField(students, student -> student.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(Comparator.naturalOrder())
                ));
    }

    private static <T> List<T> extractField(List<Student> students, Function<Student, T> extractor) {
        return extractFieldStream(students, extractor).collect(Collectors.toList());
    }

    private static <T> Stream<T> extractFieldStream(List<Student> students, Function<Student, T> extractor) {
        return students.stream().map(extractor);
    }

    private static List<Student> sortStudentsBy(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private static List<Student> findStudentsByField(Collection<Student> students, Predicate<Student> query) {
        return students.stream()
                .filter(query)
                .sorted(STUDENT_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }
}
