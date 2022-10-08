package com.techriff.userdetails.util;

public class CommonUtility {
	
	public static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toUpperCase();
	}

	public static long getMaxAllowedFileSize(long fileUploadMaxSizeAllowed) {
		return (fileUploadMaxSizeAllowed == 0) ? 0 : (fileUploadMaxSizeAllowed * 1024 * 1024);
	}

}
