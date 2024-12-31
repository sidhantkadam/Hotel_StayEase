package com.sid.project.StayEase.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sid.project.StayEase.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class AwsS3Service {
    private final String bucketName = "stayease-hotel-image";

    @Value("${aws.s3.access.key}")
    private String accessKey;

    @Value("${aws.s3.secret.key}")
    private String secretKey;

    public String saveImageToS3(MultipartFile photo) {
        String awsS3Image = null;

        try {
            String Filename = photo.getOriginalFilename();

            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .withRegion(Regions.AP_SOUTH_1)
                    .build();

            InputStream inputStream = photo.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, Filename, inputStream, objectMetadata);

            s3Client.putObject(putObjectRequest);
            return "https://" + bucketName + ".s3.amazonaws.com/" + Filename;

        } catch (Exception e) {
            throw new CommonException("Unable to upload the image on aws s3 bucket!" + e.getMessage());
        }
    }
}
