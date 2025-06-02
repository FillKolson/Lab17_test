package lab17_test.models;

import java.util.Objects;

import lab17_test.enums.DosageForm;
import lab17_test.enums.Indication;
import lab17_test.enums.PrescriptionType;

/**
 * Клас, що представляє безрецептурний лікарський засіб (Over-The-Counter
 * Medicine).
 * Наслідує клас {@link Medicine}
 */
public class OverTheCounterMedicine extends Medicine {

    private int maxRecommendedDaysOfUse;

    /** Показання до застосування. */
    private Indication indication;

    /**
     * Конструктор класу {@code OverTheCounterMedicine}.
     *
     * @param name                    назва препарату
     * @param manufacturer            виробник
     * @param price                   ціна
     * @param dosageForm              форма дозування
     * @param maxRecommendedDaysOfUse максимально рекомендована тривалість
     *                                використання (в днях)
     * @param indication              показання до застосування
     * @throws IllegalArgumentException якщо maxRecommendedDaysOfUse ≤ 0 або
     *                                  indication == null
     */
    public OverTheCounterMedicine(String name, String manufacturer, double price,
            DosageForm dosageForm, int maxRecommendedDaysOfUse,
            Indication indication) {
        super(name, manufacturer, price, dosageForm);
        setMaxRecommendedDaysOfUse(maxRecommendedDaysOfUse);
        setIndication(indication);
        setPrescriptionRequired(PrescriptionType.NOT_REQUIRED);
    }

    /**
     * Конструктор копіювання.
     * Створює новий об'єкт {@code OverTheCounterMedicine} на основі іншого об'єкта
     * того ж типу.
     *
     * @param other об'єкт для копіювання
     * @throws IllegalArgumentException якщо {@code other == null}
     */
    public OverTheCounterMedicine(OverTheCounterMedicine other) {
        super(other);
        this.maxRecommendedDaysOfUse = other.maxRecommendedDaysOfUse;
        this.indication = other.indication;
        setPrescriptionRequired(PrescriptionType.NOT_REQUIRED);
    }

    /**
     * Повертає максимальну тривалість використання препарату.
     *
     * @return максимально рекомендована тривалість використання (в днях)
     */
    public int getMaxRecommendedDaysOfUse() {
        return maxRecommendedDaysOfUse;
    }

    /**
     * Встановлює максимальну тривалість використання препарату.
     *
     * @param maxRecommendedDaysOfUse максимально рекомендована тривалість
     *                                використання (в днях)
     * @throws IllegalArgumentException якщо значення ≤ 0
     */
    public void setMaxRecommendedDaysOfUse(int maxRecommendedDaysOfUse) {
        if (maxRecommendedDaysOfUse <= 0)
            throw new IllegalArgumentException("Recommended days of use must be positive.");
        this.maxRecommendedDaysOfUse = maxRecommendedDaysOfUse;
    }

    /**
     * Повертає показання до застосування.
     *
     * @return показання до застосування
     */
    public Indication getIndication() {
        return indication;
    }

    /**
     * Встановлює показання до застосування.
     *
     * @param indication показання до застосування
     * @throws IllegalArgumentException якщо значення є {@code null}
     */
    public void setIndication(Indication indication) {
        if (indication == null)
            throw new IllegalArgumentException("Indication cannot be null.");
        this.indication = indication;
    }

    /**
     * Створює і повертає копію поточного об'єкта {@code OverTheCounterMedicine}.
     *
     * Використовує копіювальний конструктор для створення нового об'єкта.
     *
     * @return новий об'єкт {@code OverTheCounterMedicine}, який є копією поточного
     *         об'єкта
     */
    @Override
    public Medicine clone() {
        return new OverTheCounterMedicine(this);
    }

    /**
     * Повертає рядкове представлення об'єкта, включаючи батьківські та специфічні
     * поля.
     *
     * @return текстовий опис об'єкта
     */
    @Override
    public String toString() {
        return String.format("%s, Indication: %s, Max Use: %d days",
                super.toString(), indication, maxRecommendedDaysOfUse);
    }

    /**
     * Перевіряє рівність поточного об'єкта з іншим.
     *
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо об'єкти рівні; інакше {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof OverTheCounterMedicine))
            return false;
        OverTheCounterMedicine other = (OverTheCounterMedicine) obj;
        return this.maxRecommendedDaysOfUse == other.maxRecommendedDaysOfUse &&
                this.indication == other.indication;
    }

    /**
     * Обчислює хеш-код для об'єкта {@code HerbalRemedy}.
     *
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxRecommendedDaysOfUse, indication);
    }
}
