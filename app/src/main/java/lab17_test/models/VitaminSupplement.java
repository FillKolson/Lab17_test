package lab17_test.models;

import java.util.Objects;

import lab17_test.enums.DosageForm;
import lab17_test.enums.VitaminType;

/**
 * Клас, що представляє вітамінну добавку.
 * Наслідує клас {@link Medicine}
 */
public class VitaminSupplement extends Medicine {

    /** Тип вітаміну або комплексу (наприклад, A, B12, комплекс B тощо). */
    private VitaminType vitaminType;

    /** Добова доза в міліграмах. */
    private int dailyDosageMg;

    /**
     * Конструктор класу {@code VitaminSupplement}.
     *
     * @param name          назва препарату
     * @param manufacturer  виробник
     * @param price         ціна
     * @param dosageForm    лікарська форма
     * @param vitaminType   тип вітаміну або комплексу
     * @param dailyDosageMg добова доза в міліграмах
     * @throws IllegalArgumentException якщо {@code vitaminType == null} або
     *                                  {@code dailyDosageMg <= 0}
     */
    public VitaminSupplement(String name, String manufacturer, double price,
            DosageForm dosageForm, VitaminType vitaminType, int dailyDosageMg) {
        super(name, manufacturer, price, dosageForm);
        setVitaminType(vitaminType);
        setDailyDosageMg(dailyDosageMg);
    }

    /**
     * Конструктор копіювання.
     * Створює новий об'єкт {@code VitaminSupplement} на основі іншого об'єкта того
     * ж типу.
     *
     * @param other об'єкт для копіювання
     * @throws IllegalArgumentException якщо {@code other == null}
     */
    public VitaminSupplement(VitaminSupplement other) {
        super(other);
        this.vitaminType = other.vitaminType;
        this.dailyDosageMg = other.dailyDosageMg;
    }

    /**
     * Повертає тип вітаміну.
     *
     * @return тип вітаміну
     */
    public VitaminType getVitaminType() {
        return vitaminType;
    }

    /**
     * Встановлює тип вітаміну.
     *
     * @param vitaminType тип вітаміну
     * @throws IllegalArgumentException якщо {@code vitaminType == null}
     */
    public void setVitaminType(VitaminType vitaminType) {
        if (vitaminType == null)
            throw new IllegalArgumentException("Vitamin type cannot be null.");
        this.vitaminType = vitaminType;
    }

    /**
     * Повертає добову дозу в міліграмах.
     *
     * @return добова доза
     */
    public int getDailyDosageMg() {
        return dailyDosageMg;
    }

    /**
     * Встановлює добову дозу.
     *
     * @param dailyDosageMg добова доза в міліграмах
     * @throws IllegalArgumentException якщо {@code dailyDosageMg <= 0}
     */
    public void setDailyDosageMg(int dailyDosageMg) {
        if (dailyDosageMg <= 0)
            throw new IllegalArgumentException("Daily dosage must be positive.");
        this.dailyDosageMg = dailyDosageMg;
    }

    /**
     * Створює і повертає копію поточного об'єкта {@code VitaminSupplement}.
     *
     * Використовує копіювальний конструктор для створення нового об'єкта.
     *
     * @return новий об'єкт {@code VitaminSupplement}, який є копією поточного
     *         об'єкта
     */
    @Override
    public Medicine clone() {
        return new VitaminSupplement(this);
    }

    /**
     * Повертає рядкове представлення об'єкта, включаючи інформацію про вітамін і
     * дозу.
     *
     * @return рядковий опис об'єкта
     */
    @Override
    public String toString() {
        return String.format("%s, Vitamin: %s, Dosage: %d mg/day",
                super.toString(), vitaminType, dailyDosageMg);
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
        if (!(obj instanceof VitaminSupplement))
            return false;
        VitaminSupplement other = (VitaminSupplement) obj;
        return this.vitaminType == other.vitaminType &&
                this.dailyDosageMg == other.dailyDosageMg;
    }

    /**
     * Обчислює хеш-код для об'єкта {@code HerbalRemedy}.
     *
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vitaminType, dailyDosageMg);
    }
}
