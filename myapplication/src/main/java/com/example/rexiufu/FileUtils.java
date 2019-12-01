package com.example.rexiufu;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * @ProjectName: MyApplication3
 * @Package: com.anzhi.rexiufu
 * @ClassName: FileUtils
 * @Description: java类作用描述
 * @Author: yangff
 */
public class FileUtils {

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void copyFile(File sourceFile, File targetFile){
        try(Source source= Okio.buffer(Okio.source(sourceFile));
            Sink sink=Okio.buffer(Okio.sink(targetFile))) {//在try（）里面会自动关闭的
            ((BufferedSink) sink).writeAll(source);
            ((BufferedSink) sink).flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
