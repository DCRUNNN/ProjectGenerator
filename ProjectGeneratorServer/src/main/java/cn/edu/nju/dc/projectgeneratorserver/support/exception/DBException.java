package cn.edu.nju.dc.projectgeneratorserver.support.exception;

/**
 * @author dc
 * @date 2019/12/17 23:16
 */
public class DBException extends RuntimeException {

    public DBException(final String message) {
        super(message);
    }

    public DBException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
