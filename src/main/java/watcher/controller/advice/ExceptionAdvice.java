package watcher.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import watcher.exception.CustomException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> coinNotFoundHandler(CustomException e) {
        return new ResponseEntity<>(new ResponseError("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
