package com.yuejia.stocks.model;

/**
 * Ref: https://spring.io/guides/gs/uploading-files/
 */
public class StorageFileNotFoundException extends StorageException {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
