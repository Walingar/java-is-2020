package info.kgeorgiy.java.advanced.student;

public class StudentQueryFactory {
    public static StudentQuery getInstance() {
        return new StudentQueryImpl();
    }
}
