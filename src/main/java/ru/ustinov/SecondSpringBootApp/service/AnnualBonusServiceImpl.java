package ru.ustinov.SecondSpringBootApp.service;

import org.springframework.stereotype.Service;
import ru.ustinov.SecondSpringBootApp.model.Positions;
import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int currentYear = Year.now().getValue();
        int daysInYear = Year.isLeap(currentYear) ? 366 : 365;

        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays) {
        // 1. Проверка: является ли сотрудник управленцем
        if (positions == null || !positions.isManager()) {
            throw new IllegalArgumentException("Квартальная премия доступна только для управленцев (isManager = true)");
        }

        // 2. Считаем базовую премию (как годовую)
        double annualBonus = calculate(positions, salary, bonus, workDays);

        // 3. Делим на 4 (квартала в году), чтобы получить квартальную
        return annualBonus / 4;
    }
}