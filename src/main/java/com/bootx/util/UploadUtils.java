package com.bootx.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author black
 */
public final class UploadUtils {
    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI4GJqk6kDCvgv2gJDhdAc";
    private static final String accessKeySecret = "0nQLRyTzSnC81zyHnPmjtMEIUoFbne";
    private static final String bucketName = "bootx-pdd";
    private static final String url="https://bootx-pdd.oss-cn-hangzhou.aliyuncs.com/";

    public static void upload(String url,String path){
        if(StringUtils.startsWith(url,"http")||StringUtils.startsWith(url,"https")){

        }else{
            url="http:"+url;
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            System.out.println(url);
            InputStream inputStream = new URL(url).openStream();
            ossClient.putObject(bucketName, path, inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
    }

    public static String getUrl(String path){
        return url+path;
    }


    public static void upload(String path, File file, String contentType) {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(file.length());
            ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static void info(String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, path);
        Date lastModified = metadata.getLastModified();
        System.out.println(lastModified);
    }



    public static void delete(String path) {
        long now = System.currentTimeMillis();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request(bucketName);
        listObjectsV2Request.setPrefix(path);
        ListObjectsV2Result result = ossClient.listObjectsV2(listObjectsV2Request);
        System.out.println("Objects:");
        List<String> keys = new ArrayList<>();
        for (OSSObjectSummary objectSummary : result.getObjectSummaries()) {
            ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, objectSummary.getKey());
            Date lastModified = metadata.getLastModified();
            System.out.println(objectSummary.getKey()+":"+lastModified);
            // 超过12个小时的稿件删除
            if(now-lastModified.getTime()>=1000*60*60*12){
                keys.add(objectSummary.getKey());
            }
        }
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        ossClient.shutdown();
    }

    public static void main(String[] args) {
        delete("tmp/");
    }
}
