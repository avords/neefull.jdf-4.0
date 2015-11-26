package com.mvc.component.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.mvc.component.file.model.BaseFile;

public final class FileCompressUtils {
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private static final int COMPRESS_FAIL = 0;
	private static final int COMPRESS_OK = 1;
	private static final String ZIP_FILE_SUFFIX = "zip";
	private static final String RAR_FILE_SUFFIX = "rar";
	private static final Logger LOGGER = Logger.getLogger(FileCompressUtils.class);

	private FileCompressUtils() {
	}

	public static InputStream handleRar(InputStream inputStream, BaseFile baseFile) throws FileNotFoundException {
		String fileName = baseFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + RAR_FILE_SUFFIX;
		if (COMPRESS_OK == doRarCompress(fileName, baseFile.getName(), inputStream)) {
			baseFile.setName(fileName);
			inputStream = new FileInputStream(new File(fileName));
		}
		return inputStream;
	}

	public static InputStream handleZip(InputStream inputStream, BaseFile baseFile) throws FileNotFoundException {
		String fileName = baseFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + ZIP_FILE_SUFFIX;
		if (COMPRESS_OK == doZipCompress(fileName, baseFile.getName(), inputStream)) {
			baseFile.setName(fileName);
			inputStream = new FileInputStream(new File(fileName));
		}
		return inputStream;
	}

	private static int doZipCompress(String compressFileName, String originalFileName, InputStream inputStream) {
		File newFile = new File(compressFileName);
		if (newFile.exists()) {
			newFile.delete();
		}
		try {
			newFile.createNewFile();
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(newFile));
			byte[] data = new byte[DEFAULT_BUFFER_SIZE];
			ZipEntry entry = new ZipEntry(originalFileName);
			zipOut.putNextEntry(entry);
			int n;
			while (-1 != (n = inputStream.read(data))) {
				zipOut.write(data, 0, n);
			}
			zipOut.close();
		} catch (IOException e) {
			LOGGER.error("doZipCompress",e);
			return COMPRESS_FAIL;
		}
		return COMPRESS_OK;
	}

	private static int doRarCompress(String compressFileName, String originalFileName, InputStream inputStream) {
		return doZipCompress(compressFileName, originalFileName, inputStream);
	}
}
