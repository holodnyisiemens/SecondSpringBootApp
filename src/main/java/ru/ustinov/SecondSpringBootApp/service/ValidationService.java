package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.validation.BindingResult;
import ru.ustinov.SecondSpringBootApp.exceptions.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}
