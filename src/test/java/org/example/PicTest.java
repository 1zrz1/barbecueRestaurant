package org.example;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class PicTest {
    String getPicAdress(String objectName){
        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tGgeWmLjZrFt5wTTfc5";
        String accessKeySecret = "B2ZKbGR1XuPpnWFIc2SQiEZCBpFTdI";
        // 填写Bucket名称，例如examplebucket
        String bucketName = "orderfoodpic";
        // 填写Object完整路径。Object完整路径中不能包含Bucket名称。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 指定签名URL过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
        return signedUrl.toString();
    };
    public static void main(String[] args) {
        JFrame jFrame=new JFrame("窗口");
        JPanel jPanel=new JPanel();
        JLabel iconType = new JLabel();

        ImageIcon image = null;


    }
}
