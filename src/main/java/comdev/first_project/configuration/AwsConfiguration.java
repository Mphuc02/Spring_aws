package comdev.first_project.configuration;

import comdev.first_project.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.net.URI;

@RequiredArgsConstructor
@Configuration
public class AwsConfiguration {
    private final Logger logger = LoggerFactory.getLogger(AwsConfiguration.class);

    private final AwsProperties awsProperties;

    @Bean
    public S3Client s3Client(){
        S3Client s3Client = S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .endpointOverride(URI.create(awsProperties.getLocalStackHost()))
                .forcePathStyle(true)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsProperties.getAccessKey(), awsProperties.getSecretAccessKey())))
                .build();

        this.createBucketIfNotExist(s3Client, awsProperties.getS3().getImageBucketName());
        this.createBucketIfNotExist(s3Client, awsProperties.getS3().getBackUpBucketName());
        return s3Client;
    }

    private void createBucketIfNotExist(S3Client s3Client, String bucketName){
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                                                    .bucket(bucketName)
                                                    .build();

        try {
            s3Client.headBucket(headBucketRequest);
            System.out.println("Bucket " + bucketName + " is existed");
        } catch (S3Exception e) {
            logger.error("aws exception", e);
            //If bucket is not exist, then create the bucket
            if(e instanceof NoSuchBucketException) {
                S3Waiter s3Waiter = s3Client.waiter();
                CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                                                        .bucket(bucketName)
                                                        .build();

                s3Client.createBucket(bucketRequest);

                // Wait until the bucket is created and print out the response.
                WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(headBucketRequest);
                waiterResponse.matched().response().ifPresent(System.out::println);
                System.out.println("Bucket " + bucketName +" is created");
            }

            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}