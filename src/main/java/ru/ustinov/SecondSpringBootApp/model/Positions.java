package ru.ustinov.SecondSpringBootApp.model;

import lombok.Getter;

@Getter
public enum Positions {
    DEV(2.2, false),
    HR(1.2, false),
    TL(2.6, false),

    PO(2.5, true),  // Product Owner
    TPM(2.7, true), // Technical Product Manager
    CTO(3.0, true); // Chief Technology Officer

    private final double positionCoefficient;
    private final boolean isManager;

    // конструктор
    Positions(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}
