package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.stereotype.Service;
import ru.ustinov.SecondSpringBootApp.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
