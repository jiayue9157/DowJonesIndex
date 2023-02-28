package com.yuejia.stocks.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Ref: https://spring.io/guides/gs/uploading-files/
 */
@Getter
@Setter
@ConfigurationProperties("storage")
public class StorageProperties {

	//Folder location for storing files
	private String location = "upload-dir";

}
