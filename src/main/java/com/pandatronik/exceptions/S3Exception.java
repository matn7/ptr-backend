package com.pandatronik.exceptions;

public class S3Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public S3Exception(Throwable e) {
		super(e);
	}

	public S3Exception(String s) {
		super(s);
	}
}