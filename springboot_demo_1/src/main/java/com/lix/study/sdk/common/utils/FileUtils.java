package com.lix.study.sdk.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lix.study.common.conts.DemoConts;
import com.lix.study.sdk.common.excepion.DefException;


public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * 按UTF8格式读文件全部内容
     * 
     * @param path 文件路径   可以是jar包里的路径
     * @return String null表示读取失败/有异常
     */
    public static String read(String path) {
        return read(path, DemoConts.CHARSET_UTF8);
    }

    /**
     * 读文件全部内容
     * @param fileName
     * @param charset
     * @return
     */
    public static String read(String fileName, Charset charset) {
        try {
            File file = new File(fileName);
            if (file.isFile()) {
                return new String(Files.readAllBytes(file.toPath()), charset);
            } else {
                String prefix = "/";
                String fullPath = FileUtils.class.getResource(prefix + fileName).toString();
                if (fullPath.startsWith("jar:file:")) {
                    URL url = new URL(fullPath);
                    try (InputStream is = url.openStream();
                            Reader reader = new InputStreamReader(is, charset);
                            BufferedReader br = new BufferedReader(reader)) {
                        return br.lines().collect(Collectors.joining("\n"));
                    }
                } else {
                    return new String(Files.readAllBytes(Paths.get(URI.create(fullPath))), charset);
                }
            }
        } catch (Exception e) {
            logger.error("Read [{}] error: {}", fileName, e);
            throw new DefException(e);
        }
    }

    /**
     * 根据类型不同获取不同流
     * @param fileName
     * @return
     * @throws IOException
     */
    private static InputStream getFileStream(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.isFile()) {
            return new FileInputStream(file);
        } else {
            String prefix = "/";
            String fullPath = FileUtils.class.getResource(prefix + fileName).toString();
            if (fullPath.startsWith("jar:file:")) {
                URL url = new URL(fullPath);
                return url.openStream();
            } else {
                return new FileInputStream(Paths.get(URI.create(fullPath)).toFile());
            }
        }
    }

    /**
     * 得到文件的MD5字符串
     * 
     * @param fileName
     * @param hashType
     * @return
     */
    public static String md5(String fileName) {
        try (InputStream fis = getFileStream(fileName)) {
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            return EncryptUtils.bytesToHex(md5.digest());
        } catch (Exception e) {
            logger.error("Get md5 failed", e);
            return null;
        }
    }
}
