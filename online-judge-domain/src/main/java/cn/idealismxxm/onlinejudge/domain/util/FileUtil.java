package cn.idealismxxm.onlinejudge.domain.util;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件工具
 *
 * @author idealism
 * @date 2018/4/15
 */
public class FileUtil {

    /**
     * 读取文件的所有内容
     *
     * @param filePath 文件路径
     * @return 文件内的内容
     */
    public static String readString(String filePath) {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] content = new byte[fileLength.intValue()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(content);
            return new String(content);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.FILE_READ_ERROR, e);
        }
    }

    /**
     * 向指定的文件写入字符串
     *
     * @param filePath 文件路径
     * @param content 待写入的内容
     */
    public static void writeString(String filePath, String content) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))) {
            fileOutputStream.write(content.getBytes());
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.FILE_WRITE_ERROR, e);
        }
    }

    /**
     * 创建指定的文件夹
     *
     * @param folderPath 文件夹路径
     */
    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);

        // 如果文件夹不存在且未创建成功，则抛出异常
        if (!folder.exists() && !folder.mkdirs()) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.FILE_CREATE_ERROR);
        }
    }
}
