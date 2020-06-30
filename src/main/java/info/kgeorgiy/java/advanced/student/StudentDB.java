package info.kgeorgiy.java.advanced.student;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {

  @Override
  public List<String> getFirstNames(List<Student> students) {
    return getListByMapper(students, Student::getFirstName);
  }

  @Override
  public List<String> getLastNames(List<Student> students) {
    return getListByMapper(students, Student::getLastName);
  }

  @Override
  public List<String> getGroups(List<Student> students) {
    return getListByMapper(students, Student::getGroup);
  }

  @Override
  public List<String> getFullNames(List<Student> students) {
    return getListByMapper(students,
        s -> String.format("%s %s", s.getFirstName(), s.getLastName()));
  }

  @Override
  public Set<String> getDistinctFirstNames(List<Student> students) {
    return students.stream()
        .map(Student::getFirstName)
        .collect(Collectors.toSet());
  }

  @Override
  public String getMinStudentFirstName(List<Student> students) {
    return students.stream()
        .min(Comparator.comparing(Student::getId))
        .map(Student::getFirstName)
        .orElse("");
  }

  @Override
  public List<Student> sortStudentsById(Collection<Student> students) {
    return getValuesBySort(
        students.stream(),
        Comparator.comparing(Student::getId)
    );
  }

  @Override
  public List<Student> sortStudentsByName(Collection<Student> students) {
    return getValuesBySort(
        students.stream(),
        Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId)
    );
  }

  @Override
  public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
    return getValuesByFilterAndSort(
        students.stream(),
        student -> Objects.equals(student.getFirstName(), name),
        Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId)
    );
  }

  @Override
  public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
    return getValuesByFilterAndSort(
        students.stream(),
        student -> Objects.equals(student.getLastName(), name),
        Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId)
    );
  }

  @Override
  public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
    return getValuesByFilterAndSort(
        students.stream(),
        student -> Objects.equals(student.getGroup(), group),
        Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId)
    );
  }

  @Override
  public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
    return students.stream()
        .filter(student -> Objects.equals(student.getGroup(), group))
        .collect(Collectors.toMap(
            Student::getLastName,
            Student::getFirstName,
            (a, b) -> a.compareTo(b) > 0 ? b : a)
        );
  }

  private List<String> getListByMapper(List<Student> students,
      Function<? super Student, ? extends String> mapper) {
    return students.stream()
        .map(mapper)
        .collect(Collectors.toList());
  }

  private List<Student> getValuesByFilterAndSort(
      Stream<Student> students,
      Predicate<? super Student> filter,
      Comparator<? super Student> comparator) {
    return getValuesBySort(students.filter(filter), comparator);
  }

  private List<Student> getValuesBySort(Stream<Student> students,
      Comparator<? super Student> comparator) {
    return students
        .sorted(comparator)
        .collect(Collectors.toList());
  }
}