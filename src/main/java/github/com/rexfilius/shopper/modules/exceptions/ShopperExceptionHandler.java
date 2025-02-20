package github.com.rexfilius.shopper.modules.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ShopperExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception
    ) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(
            ApiException exception
    ) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * @ExceptionHandler(AppException.class)
     *     public final ResponseEntity<Object> handleAppException(AppException ex) {
     *         log.error("AppException => ", ex);
     *         HashMap<String, String> response = new HashMap<>();
     *         response.put("responseCode", ex.getResponseCode());
     *         response.put("responseMessage", ex.getMessage());
     *         return ResponseEntity.status(HttpStatus.OK).body(response);
     *     }
     *
     *     @ExceptionHandler(MaxUploadSizeExceededException.class)
     *     public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
     *         HashMap<String, String> response = new HashMap<>();
     *         response.put("responseCode", ResponseCodes.ERROR);
     *         response.put("responseMessage", "File too large!");
     *         return ResponseEntity.status(HttpStatus.OK).body(response);
     *     }
     */
}
