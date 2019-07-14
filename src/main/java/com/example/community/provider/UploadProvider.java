package com.example.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/12 20:22
 */
@Service
public class UploadProvider {

    @Value("${qcloud.secretId}")
    private String secretId;

    @Value("${qcloud.secretKey}")
    private String secretKey;

    @Value("${qcloud.region}")
    private String region;

    @Value("${qcloud.bucketName}")
    private String bucketName;

    public String uploadImages(InputStream fileStream, String mineType, String filename) {
        Region region_obj = new Region(region);
        ClientConfig clientConfig = new ClientConfig(region_obj);
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            // 指定要上传到 COS 上对象键
//            String key = filename;
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
//            PutObjectResult putObjectResult = cosClient.putObject();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(mineType);
            cosClient.putObject(bucketName, filename, fileStream, objectMetadata);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
        String url = "https://" + bucketName + ".cos." + region +".myqcloud.com/" + filename;
        return url;
    }
}
