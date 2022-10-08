package com.techriff.userdetails.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
public static String writeFileIntoDir(MultipartFile file, String uploadFileRootPath) throws IOException {
		
		File dir = new File(uploadFileRootPath);		
		String extension = CommonUtility.getFileExtension(file.getOriginalFilename());
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Random rn = new Random();
		int uniqueDigit = rn.nextInt(1000);
		SimpleDateFormat simpleDateFormatForPattern = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
		String timeAuto = simpleDateFormatForPattern.format(new Date());
		String fileName = timeAuto + String.valueOf(uniqueDigit) + "." + extension;;
		String filePath = dir.getPath() + File.separator + fileName;
		try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) {
			outputStream.write(file.getBytes());
			outputStream.flush();
		}
		return fileName;
	
	}

}
