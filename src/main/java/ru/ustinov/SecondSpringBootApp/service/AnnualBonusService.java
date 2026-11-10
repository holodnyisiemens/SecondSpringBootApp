package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.stereotype.Service;
import ru.ustinov.SecondSpringBootApp.model.Positions;

@Service
public interface AnnualBonusService {

    double calculate(Positions positions, double salary, double bonus, int workDays);

    // Новый метод для квартальной премии
    double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays);

}