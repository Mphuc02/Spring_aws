    package comdev.first_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages implements ErrorMessage {
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad request"),
    INVALID_VALUE(400_001, "Invalid value"),
    SAVE_DATABASE_ERROR(400_002, "Save database error"),
    NOT_FOUND(404, "Resource not found"),
    ID_PROVINCE_NOT_FOUND(404, "province with id is not exists"),
    ID_DISTRICT_NOT_FOUND(404, "district with id is not exists"),
    FILE_IS_NOT_IMAGE(400, "Can not upload this file because this file is not png or jpeg or jpg"),
    FILE_IS_EMPTY(400, "Can not upload this file because this file is empty"),
    UPLOAD_ERROR(400, "Cannot upload the image"),
    IMAGE_NOT_FOUND(404, "Image with given id not found"),
    OBJECT_NOT_FOUNT(404, "Object with given key not found"),
    LOG_FILE_NOT_FOUND(404, "The log file not found"),
    USER_NOT_FOUND(404, "User not found"),
    USER_NOT_AUTHENTICATED(400, "User hasn't authenticated")
    ;

    private final Integer errorCode;
    private final String errorMessage;
}