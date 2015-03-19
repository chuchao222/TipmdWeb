package com.tipmd.webapp.dao.pager;

public class InvalidSelectSqlException extends RuntimeException {

    public InvalidSelectSqlException() {
        super();
    }

    public InvalidSelectSqlException(String s) {
        super(s);
    }


    public InvalidSelectSqlException(String message, Throwable cause) {
        super(message, cause);
    }


    public InvalidSelectSqlException(Throwable cause) {
        super(cause);
    }
    
	private static final long serialVersionUID = -586332391395001122L;
}
