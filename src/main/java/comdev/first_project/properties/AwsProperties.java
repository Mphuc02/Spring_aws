package comdev.first_project.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {
    @NotBlank
    private String region;
    @NotBlank
    private String localStackHost;
    @NotBlank
    private String accessKey;
    @NotBlank
    private String secretAccessKey;
    @NotNull
    private S3 s3;
    @Data
    @Valid
    public static class S3 {
        @NotBlank
        private String imageBucketName;
        @NotBlank
        private String backUpBucketName;
    }
}