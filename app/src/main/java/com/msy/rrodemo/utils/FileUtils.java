package com.msy.rrodemo.utils;

import android.os.Environment;

import com.misy.photopicker.utils.PermissionsUtils;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by Administrator on 2020/1/8/008.
 */

public class FileUtils {

    private static String ABSOLUTE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String createAndWriteFile(byte[] content) throws IOException {
        String path = ABSOLUTE_PATH + "/MisyImageSrc";
        String fileName = String.valueOf(System.currentTimeMillis())+".png";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }else {
            path = path + "/" + fileName;
        }
        File imgFile = new File(path);
        if(!imgFile.exists()){
            //创建文件
            imgFile.createNewFile();
        }
        BufferedSink source = Okio.buffer(Okio.sink(imgFile));
        source.write(content);
        source.close();
        return path;
    }
}
