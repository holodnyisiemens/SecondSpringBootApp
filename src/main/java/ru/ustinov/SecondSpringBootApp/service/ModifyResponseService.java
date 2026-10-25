package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.stereotype.Service;
import ru.ustinov.SecondSpringBootApp.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
