package comdev.first_project.service.impl;

import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.exception.NotFoundException;
import comdev.first_project.exception.UploadObjectException;
import comdev.first_project.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;
    private final Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Override
    public List<String> getUrlsOfObjectsInBucket(String bucketName) {
        ListObjectsRequest listObjectsRequest = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();

        ListObjectsResponse listObjectsResponse = this.s3Client.listObjects(listObjectsRequest);
        List<S3Object> objectsInBucket = listObjectsResponse.contents();
        List<String> result = new ArrayList<>();

        objectsInBucket.forEach(object -> result.add(bucketName + "/" + object.key()));

        return result;
    }

    @Override
    public void uploadObject(String bucketName, byte[] data, String key) {
        this.s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(data)));

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        String imageUrl = s3Client.utilities().getUrl(getUrlRequest).toString();
        logger.info("Upload success Object with key: " + imageUrl + " to bucket: " + bucketName);
    }

    @Override
    public void updateObject(String bucketName, byte[] data, String key) {
        HeadObjectRequest headObjectRequest = this.createHeadObjectRequest(bucketName, key);
        try {
            this.s3Client.headObject(headObjectRequest);
            this.uploadObject(bucketName, data, key);
        } catch (SdkClientException | AwsServiceException e) {
            throw new UploadObjectException(ErrorMessages.OBJECT_NOT_FOUNT);
        }
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        HeadObjectRequest headObjectRequest = this.createHeadObjectRequest(bucketName, key);
        try {
            this.s3Client.headObject(headObjectRequest);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            this.s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new NotFoundException(ErrorMessages.OBJECT_NOT_FOUNT);
        }
    }

    private HeadObjectRequest createHeadObjectRequest(String bucketName, String key) {
        return HeadObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }
}