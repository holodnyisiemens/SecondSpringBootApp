package ru.ustinov.SecondSpringBootApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ustinov.SecondSpringBootApp.exceptions.ValidationFailedException;
import ru.ustinov.SecondSpringBootApp.model.*;
import ru.ustinov.SecondSpringBootApp.service.ModifyRequestService;
import ru.ustinov.SecondSpringBootApp.service.ModifyResponseService;
import ru.ustinov.SecondSpringBootApp.service.ValidationService;
import jakarta.validation.Valid;
import java.util.Date;
import ru.ustinov.SecondSpringBootApp.exceptions.UnsupportedCodeException;
import ru.ustinov.SecondSpringBootApp.util.DateTimeUtil;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        @Qualifier("modifySourceRequestService") ModifyRequestService modifyRequestService) {

        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            validationService.isValid(bindingResult);
            log.info("Проверяем корректность UID запроса");
            if ("123".equals(request.getUid())) {
                log.error("Выброс исключения: UID '123' не поддерживается. Запрос отклонен.");
                throw new UnsupportedCodeException("Unsupported uid: 123");
            }

        } catch (UnsupportedCodeException e) {
            log.error("Ошибка. Запрос не поддерживается: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Сформирован ответ с ошибкой поддерживаемости запроса: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (ValidationFailedException e) {
            // Логируем конкретные ошибки из bindingResult
            log.error("Ошибка валидации запроса: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Сформирован ответ с ошибкой валидации: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Непредвиденная ошибка запроса: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Сформирован ответ с непредвиденной ошибкой: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Запрос успешен: {}", response);
        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
