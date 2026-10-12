package ru.ustinov.SecondSpringBootApp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank
    private String uid;
    @NotBlank
    private String operationUid;
    private String systemName;
    @NotBlank
    private String systemTime;
    private String source;
    private int communicationId;
    private int templateId;
    private int productCode;
    private int smsCode;
}