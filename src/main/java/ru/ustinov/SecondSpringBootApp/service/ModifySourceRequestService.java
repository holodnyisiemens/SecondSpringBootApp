package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.ustinov.SecondSpringBootApp.model.Request;
import ru.ustinov.SecondSpringBootApp.util.DateTimeUtil;
import java.util.Date;

@Service
public class ModifySourceRequestService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        // 1. Фиксируем время отправки
        String startTime = DateTimeUtil.getCustomFormat().format(new Date());
        request.setSystemTime(startTime);

        // 2. Меняем source
        request.setSource("Service 1");

        System.out.println(">>> [Сервис 1] Отправка. Время: " + startTime);

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        try {
            new RestTemplate().exchange("http://localhost:8084/feedback",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    });
        } catch (Exception e) {
            System.out.println("Ошибка отправки запроса на 2 сервис: " + e.getMessage());
        }
    }
}