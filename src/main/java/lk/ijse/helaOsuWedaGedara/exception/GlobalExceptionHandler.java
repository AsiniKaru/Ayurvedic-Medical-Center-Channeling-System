package lk.ijse.helaOsuWedaGedara.exception;

import lk.ijse.helaOsuWedaGedara.constant.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static lk.ijse.helaOsuWedaGedara.constant.ResponseStatusCode.OPERATION_FAIL;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse handleRuntimeException(RuntimeException ex) {
        return new CommonResponse(OPERATION_FAIL, null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleGeneralException(Exception ex) {
        return new CommonResponse(OPERATION_FAIL, null, "An unexpected error occurred: " + ex.getMessage());
    }
}
