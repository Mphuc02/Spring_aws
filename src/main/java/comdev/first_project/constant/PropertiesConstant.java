package comdev.first_project.constant;

public class PropertiesConstant {
    public static final class AWS{
        public static final String IMAGE_BUCKET_NAME = "${aws.s3.imageBucketName}";
        public static final String BACKUP_BUCKET_NAME = "${aws.s3.backUpBucketName}";
    }

    public static final class JWT{
        public static final String SECRET_KEY = "${application.security.jwt.secret-key}";
        public static final String EXPIRATION = "${application.security.jwt.expiration}";
        public static final String REFRESH_TOKEN_EXPIRATION = "${application.security.jwt.refresh-token.expiration}";
    }
}