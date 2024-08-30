package ru.tms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.tms.dto.BaseResponse;
import ru.tms.dto.Faults;
import ru.tms.exception.BusinessException;
import ru.tms.exception.ExceptionType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleException(Exception ex) {

        if(ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argumentException = (MethodArgumentNotValidException) ex;

            List<String> errors = new ArrayList<>();
            argumentException.getBindingResult().getAllErrors().forEach((error) -> {
                String errorMessage = error.getDefaultMessage();
                errors.add(errorMessage);
            });

            List<Faults> faults = new ArrayList<>();
            for (String error : errors) {
                Faults fault = new Faults(ExceptionType.BUSINESS.name().toLowerCase(), error);
                faults.add(fault);
            }
            return new ResponseEntity<>(new BaseResponse<>(faults), HttpStatus.OK);
        }

        if(ex instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException methodException = (HttpRequestMethodNotSupportedException) ex;

            String supportedMethods = String.join(", ", methodException.getSupportedMethods());
            Faults fault = new Faults(ExceptionType.SYSTEM.name().toLowerCase(),
                    MessageFormat.format("Метод должен быть {0}", supportedMethods));
            return new ResponseEntity<>(new BaseResponse<>(List.of(fault)), HttpStatus.METHOD_NOT_ALLOWED);
        }

        if(ex instanceof NoResourceFoundException) {
            NoResourceFoundException resourceException = (NoResourceFoundException) ex;

            Faults fault = new Faults(ExceptionType.BUSINESS.name().toLowerCase(),
                    MessageFormat.format("Указанного ресурса не существует: {0}",
                            resourceException.getResourcePath()));

            return new ResponseEntity<>(new BaseResponse<>(List.of(fault)), HttpStatus.NOT_FOUND);
        }

        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            Faults fault = new Faults(ExceptionType.BUSINESS.name().toLowerCase(), businessException.getMessage());

            return new ResponseEntity<>(new BaseResponse<>(List.of(fault)), HttpStatus.OK);
        }

        Faults fault = new Faults(ExceptionType.BUSINESS.name().toLowerCase(),
                "Что-то пошло не так. Проверьте параметры запроса");

        return new ResponseEntity<>(new BaseResponse<>(List.of(fault)), HttpStatus.BAD_REQUEST);
    }
}
