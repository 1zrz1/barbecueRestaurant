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
 * @Author �����
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class PicTest {
    String getPicAdress(String objectName){
        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // �������˺�AccessKeyӵ������API�ķ���Ȩ�ޣ����պܸߡ�ǿ�ҽ�����������ʹ��RAM�û�����API���ʻ��ճ���ά�����¼RAM����̨����RAM�û���
        String accessKeyId = "LTAI5tGgeWmLjZrFt5wTTfc5";
        String accessKeySecret = "B2ZKbGR1XuPpnWFIc2SQiEZCBpFTdI";
        // ��дBucket���ƣ�����examplebucket
        String bucketName = "orderfoodpic";
        // ��дObject����·����Object����·���в��ܰ���Bucket���ơ�
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // ָ��ǩ��URL����ʱ��Ϊ10���ӡ�
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
        return signedUrl.toString();
    };
    public static void main(String[] args) {
        JFrame jFrame=new JFrame("����");
        JPanel jPanel=new JPanel();
        JLabel iconType = new JLabel();

        ImageIcon image = null;


    }
}
