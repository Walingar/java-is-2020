package info.kgeorgiy.java.advanced.student;

import java.util.function.Function;

public class StudentQueryFactory {
    public static StudentQuery getInstance() { 
        return new StudentQueryImpl();
    }
}
