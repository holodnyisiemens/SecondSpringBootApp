package ru.ustinov.SecondSpringBootApp.service;

import org.junit.jupiter.api.Test;
import ru.ustinov.SecondSpringBootApp.model.Positions;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnnualBonusServiceImplTest {

    private final AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();

    @Test
    void calculateQuarterlyBonus_Success_ForManager() {
        // given
        Positions position = Positions.PO; // Управленец (isManager = true)
        double salary = 100000.00;
        double bonus = 2.0;
        int workDays = 243;

        // when
        double result = service.calculateQuarterlyBonus(position, salary, bonus, workDays);

        // then
        // Высчитываем ожидаемое значение динамически, чтобы тест не упал в високосный год
        int currentYear = Year.now().getValue();
        int daysInYear = Year.isLeap(currentYear) ? 366 : 365;

        // Квартальная премия = (Годовая премия) / 4
        double expectedAnnual = salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;
        double expectedQuarterly = expectedAnnual / 4;

        assertThat(result).isEqualTo(expectedQuarterly);
    }

    @Test
    void calculateQuarterlyBonus_ThrowsException_ForNonManager() {
        // given
        Positions position = Positions.DEV; // Не управленец (isManager = false)
        double salary = 100000.00;
        double bonus = 2.0;
        int workDays = 243;

        // when & then
        assertThatThrownBy(() -> service.calculateQuarterlyBonus(position, salary, bonus, workDays))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Квартальная премия доступна только для управленцев");
    }
}