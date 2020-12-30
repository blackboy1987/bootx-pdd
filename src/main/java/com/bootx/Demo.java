package com.bootx;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException {
        String path = "E:\\迅雷下载\\BBDown\\速来！相声动画 郭德纲于谦 大合集 助眠好帮手《全集齐》";

        File[] files = new File(path).listFiles();
        for (File file:files) {
            String fileName = file.getName();
            fileName = fileName.replace("mp4","mp3").replace("[P","").replace("]","");
            FileUtils.copyFile(file,new File(path,fileName));
            System.out.println(fileName+"=========================================");
        }


    }


}
