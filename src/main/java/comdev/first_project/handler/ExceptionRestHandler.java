package comdev.first_project.handler;

import comdev.first_project.exception.NotFoundException;
import comdev.first_project.exception.UploadObjectException;
import comdev.first_project.response.ResponseCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionRestHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionRestHandler.class);
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException e){
        logger.error("Exception", e);
        return ResponseEntity.ok(new ResponseCustom<>(e.getErrorMessage()));
    }
    @ExceptionHandler(UploadObjectException.class)
    public ResponseEntity<Object> uploadImageException(UploadObjectException e){
        logger.error("Exception", e);
        return ResponseEntity.ok(new ResponseCustom<>(e.getErrorMessage()));
    }
}