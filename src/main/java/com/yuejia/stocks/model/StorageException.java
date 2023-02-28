package com.yuejia.stocks.model;

/**
 * Ref: https://spring.io/guides/gs/uploading-files/
 */
public class StorageException extends RuntimeException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
