package common.utils;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class FileUtils {

	public static Map<String, String> listFilesInFolder(String folderPath) {
		Map<String, String> fileMap = new HashMap<>();
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		if (files == null) return fileMap; // empty or not a directory

		for (File file : files) {
			if (file.isFile()) {
				String md5 = MD5Hash.getMD5(file.getAbsolutePath());
				fileMap.put(file.getName(), md5);
			}
		}
		return fileMap;
	}

	public static String getSortedFileList(Map<String, String> files) {
		if (files == null || files.isEmpty()) {
			return "Repository is empty.";
		}
		// TreeMap to sort by key (filename)
		Map<String, String> sorted = new TreeMap<>(files);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : sorted.entrySet()) {
			sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
		}
		return sb.toString().trim();
	}
}