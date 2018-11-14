package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static Logger instance = null;
    private final SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    private Logger() {
        // Private constructor to override calls to default public empty constructor
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void info(String msg) {
        Date now = new Date();
        System.out.println("<I> " + datePattern.format(now) + ": " + msg);
    }

    public void warn(String msg) {
        Date now = new Date();
        System.out.println("<W> " + datePattern.format(now) + ": " + msg);
    }

    public void error(String msg) {
        Date now = new Date();
        System.out.println("<E> " + datePattern.format(now) + ": " + msg);
    }

    public void trace(String msg) {
        Date now = new Date();
        System.out.println("<T> " + datePattern.format(now) + ": " + msg);
    }
}

