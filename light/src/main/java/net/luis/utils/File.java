package net.luis.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件相关
 * 
 * @author shove
 * 
 */
public class File {

	/**
	 * 读取文本文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String read(String fileName) throws IOException {
		java.io.File file = new java.io.File(fileName);
		StringBuilder sb = new StringBuilder();
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(file));

		while ((line = br.readLine()) != null) {
			sb.append(line + "\r\n");
		}

		br.close();

		return sb.toString();
	}

	/**
	 * 写文本文件
	 * 
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public static void write(String fileName, String content) throws IOException {
		java.io.File file = new java.io.File(fileName);
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		br.write(content);
		br.close();
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtensionName(String fileName) {
		if ((fileName == null) || (fileName.length() == 0)) {
			return "";
		}

		int path1 = fileName.lastIndexOf('/');
		int path2 = fileName.lastIndexOf('\\');
		int path = (path1 > path2) ? path1 : path2;

		fileName = fileName.substring(path + 1);

		if ((fileName == null) || (fileName.length() == 0)) {
			return "";
		}

		int dot = fileName.lastIndexOf('.');

		if ((dot > -1) && (dot < (fileName.length() - 1))) {

			return fileName.substring(dot);
		}

		return "";
	}

	/**
	 * 获取文件名不包含扩展名部分
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNameWithoutExt(String fileName) {
		if ((fileName == null) || (fileName.length() == 0)) {
			return "";
		}

		int path1 = fileName.lastIndexOf('/');
		int path2 = fileName.lastIndexOf('\\');
		int path = (path1 > path2) ? path1 : path2;

		fileName = fileName.substring(path + 1);

		if ((fileName == null) || (fileName.length() == 0)) {
			return "";
		}

		int dot = fileName.lastIndexOf('.');

		if ((dot > -1) && (dot < (fileName.length()))) {
			return fileName.substring(0, dot);
		}

		return "";
	}
}
