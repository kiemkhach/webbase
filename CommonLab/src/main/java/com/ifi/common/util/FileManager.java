package com.ifi.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManager {
	public static StringBuilder getFileContent(final File file) {
		String readLine = null;
		StringBuilder fileContent = new StringBuilder();
		try {
			InputStream in = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			while ((readLine = reader.readLine()) != null) {
				fileContent.append(readLine);
				fileContent.append("\r\n");
			}

			// logger.info("File content: " + fileContent.toString());
			reader.close();
			in.close();
		} catch (IOException e) {
			return null;
		}
		return fileContent;
	}
}
