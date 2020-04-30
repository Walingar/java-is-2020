package info.kgeorgiy.java.advanced.student;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {

  @Override
  public List<String> getFirstNames(List<Student> students) {
    return students.stream()
        .map(Student::getFirstName)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getLastNames(List<Student> students) {
    return students.stream()
        .map(Student::getLastName)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getGroups(List<Student> students) {
    return students.stream()
        .map(Student::getGroup)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getFullNames(List<Student> students) {
    return students.stream()
        .map(s -> String.format("%s %s", s.getFirstName(), s.getLastName()))
        .collect(Collectors.toList());
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
    return students.stream()
        .sorted(Comparator.comparing(Student::getId))
        .collect(Collectors.toList());
  }

  @Override
  public List<Student> sortStudentsByName(Collection<Student> students) {
    return students.stream()
        .sorted(
            Comparator
                .comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)
        )
        .collect(Collectors.toList());
  }

  @Override
  public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
    return students.stream()
        .filter(student -> Objects.equals(student.getFirstName(), name))
        .sorted(
            Comparator
                .comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)
        )
        .collect(Collectors.toList());
  }

  @Override
  public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
    return students.stream()
        .filter(student -> Objects.equals(student.getLastName(), name))
        .sorted(
            Comparator
                .comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)
        )
        .collect(Collectors.toList());
  }

  @Override
  public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
    return students.stream()
        .filter(student -> Objects.equals(student.getGroup(), group))
        .sorted(
            Comparator
                .comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)
        )
        .collect(Collectors.toList());
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
}