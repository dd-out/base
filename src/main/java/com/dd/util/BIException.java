package com.dd.util;

public class BIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4154645050517134601L;

	public BIException() {
		super();
	}

	public BIException(String msg) {
		super(msg);
	}

	public BIException(Throwable e) {
		super(e);
	}

	public BIException(String msg, Throwable e) {
		super(msg, e);
	}
}
