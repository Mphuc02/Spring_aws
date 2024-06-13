package comdev.first_project.constant;

public class SystemConstant {
    public static final class IMAGE{
        public static final String PNG_EXTENSION = "png";
        public static final String JPEG_EXTENSION = "jpeg";
        public static final String JPG_EXTENSION = "jpg";
        public static String IMAGE_NOT_FOUNT(String key){
            return String.format("Image has key = %s not found", key);
        }
    }
    public static final class BACKUP{
        public static final String LOG_FILE_NAME = "server.log";
    }
    public static final class BEAN{
        public static final String USER_AUTHENTICATE = "user_authenticate";
        public static final String ADMIN_AUTHENTICATE = "admin_authenticate";
    }
    public static final class AUTH{
        public static final String AUTH_URL = "/api/v1/auth";
        public static final String REGISTER_URL = "/register";
        public static final String AUTHENTICATE_URL = "/authenticate";
        public static final String AUTH_HEADER = "Authorization";
        public static final String BEARER = "Bearer";
        public static final int INDEX_OF_JWT = 7;
        public static final String NAME_ATTRIBUTE = "name";
        public static final String EMAIl_ATTRIBUTE = "email";
    }
    public static final class LOG_MESSAGE{
        public static String REGISTER_USER(String username){
            return String.format("Register success with username = %s", username);
        }
    }
    public static final class WEB_SOCKET{
        public static final String END_POINT = "/data-socket";
        public static final String APPLICATION_DESTINATION_PREFIXES = "/app";
        public static final String SIMPLE_BROKER = "/topic";
        public static final String NEW_USER_ONLINE = "/topic/new-user-online";
        public static final String USER_OFFLINE = "/topic/user-offline";
    }
    public static final class OAUTH2_USER{
        public static final String NAME = "name";
        public static final String EMAIL = "email";
    }
    public static final class TOKEN{
        public static final String JWT_NAME = "jwt";
        public static final int JWT_MAX_AGE = 3600;
        public static final String JWT_PATH = "/";
        public static final String DOMAIN = "localhost";
        public static final String FULL_NAME_CLAIM = "fullName";
        public static final String PROVIDE_CLAIM = "provider";
    }
    public static final class USER_API{
        public static final String URL = "/api/v1/user";
        public static final String GET_AUTHENTICATED_USER = "/get-authenticated";
    }
}