package com.seczone.ssp.tools;

import java.io.*;

/**
 * @author hubing
 * @date 2022/6/27 17:23
 */
public class LogUtilTest {

    private static final int OFFSET = 3;

    public static boolean changeFile(File inFile, File outFile, int offset) {
        if (!inFile.exists()) {
            throw new NullPointerException("File does not exist");
        }

        if (!outFile.exists()) {
            if (!outFile.getParentFile().exists()) {
                boolean mkdirs = outFile.getParentFile().mkdirs();
                if (!mkdirs) {
                    throw new RuntimeException("Failed to create a file while change the file, fileName: " + outFile.getPath());
                }
            }
        }

        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inFile));
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outFile))) {
            int value;
            while ((value = input.read()) != -1) {
                output.write(value + offset);
            }
        } catch (IOException e) {
            // LogUtil.error(FileUtil.class, "changeFile error:", e);
            // 需要将创建好的文件删除
            outFile.deleteOnExit();
            return false;
        }
        return true;
    }
    /**
     * 文件解密
     *
     * @param inFile
     * @param outFile
     * @return
     */
    public static boolean decipherFile(File inFile, File outFile) {
        return changeFile(inFile, outFile, -OFFSET);
    }

    public static void main(String[] args) throws Exception {

        File file = new File("D:\\WorkSpace\\project\\demo\\log");
        File[] listFiles = file.listFiles();
        for (File listFile : listFiles) {
            decipherFile(listFile, new File("D:\\WorkSpace\\project\\demo\\log\\de_" + listFile.getName()));
        }
    }

}
