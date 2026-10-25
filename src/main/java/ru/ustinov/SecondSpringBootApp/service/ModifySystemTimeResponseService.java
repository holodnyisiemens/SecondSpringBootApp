package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ustinov.SecondSpringBootApp.model.Response;
import ru.ustinov.SecondSpringBootApp.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        response.setSystemTime((DateTimeUtil.getCustomFormat())
                .format(new Date()));
        return response;
    }
}
