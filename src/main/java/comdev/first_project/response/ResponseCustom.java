package comdev.first_project.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import comdev.first_project.exception.ErrorMessage;
import comdev.first_project.exception.ErrorMessages;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseCustom<T> {
    private Integer code;
    private String message;
    private T data;
    private List<T> listDatas;
    public ResponseCustom(){
        this.code = 200;
    }
    public ResponseCustom(T data){
        this.code = 200;
        this.data = data;
    }
    public ResponseCustom(ErrorMessage errorMessage){
        this.code = errorMessage.getErrorCode();
        this.message = errorMessage.getErrorMessage();
    }
    public ResponseCustom(T data, ErrorMessage errorMessage){
        this.data = data;
        this.code = errorMessage.getErrorCode();
        this.message = errorMessage.getErrorMessage();
    }
    public ResponseCustom(T data, List<T> listDatas, ErrorMessages errorMessages){
        this.data = data;
        this.listDatas = listDatas;
        this.code = errorMessages.getErrorCode();
        this.message = errorMessages.getErrorMessage();
    }
    public ResponseCustom(List<T> data, ErrorMessages errorMessages){
        this.listDatas = data;
        this.code = errorMessages.getErrorCode();
        this.message = errorMessages.getErrorMessage();
    }
}