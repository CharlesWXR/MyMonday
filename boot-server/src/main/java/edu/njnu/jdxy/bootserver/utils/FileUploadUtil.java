package edu.njnu.jdxy.bootserver.utils;

import java.util.UUID;

public class FileUploadUtil {
    public static String generateFileName(String filename) {
        String fileExt = filename.substring(filename.lastIndexOf("."));
        return UUID.randomUUID().toString().replaceAll("-", "") + fileExt;
    }
}
