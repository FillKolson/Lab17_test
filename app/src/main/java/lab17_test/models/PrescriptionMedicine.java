package lab17_test.models;

import java.util.Objects;

import lab17_test.enums.ActiveIngredient;
import lab17_test.enums.DosageForm;
import lab17_test.enums.PrescriptionType;

/**
 * Клас, що представляє лікарський засіб, який відпускається за рецептом.
 * Наслідує клас {@link Medicine}.
 */
public class PrescriptionMedicine extends Medicine {
    private int prescriptionValidityDays; // кількість днів дії рецепта
    private ActiveIngredient activeIngredient;

    /**
     * Конструктор з параметрами.
     *
     * @param name                     Назва лікарського засобу.
     * @param manufacturer             Виробник лікарського засобу.
     * @param price                    Ціна лікарського засобу.
     * @param dosageForm               Лікарська форма (наприклад, таблетки, сироп
     *                                 тощо).
     * @param prescriptionValidityDays Кількість днів дії рецепта (має бути > 0).
     * @param activeIngredient         Діюча речовина у складі засобу.
     * @throws IllegalArgumentException якщо prescriptionValidityDays <= 0 або
     *                                  activeIngredient є null.
     */
    public PrescriptionMedicine(String name, String manufacturer, double price,
            DosageForm dosageForm, int prescriptionValidityDays,
            ActiveIngredient activeIngredient) {
        super(name, manufacturer, price, dosageForm);
        setPrescriptionRequired(PrescriptionType.REQUIRED);
        setPrescriptionValidityDays(prescriptionValidityDays);
        setActiveIngredient(activeIngredient);
    }

    /**
     * Копіювальний конструктор.
     *
     * @param other Інший об'єкт {@code PrescriptionMedicine}, з якого копіюються
     *              дані.
     * @throws IllegalArgumentException якщо передано null.
     */
    public PrescriptionMedicine(PrescriptionMedicine other) {
        super(other);
        this.prescriptionValidityDays = other.prescriptionValidityDays;
        this.activeIngredient = other.activeIngredient;
        setPrescriptionRequired(PrescriptionType.REQUIRED);
    }

    /**
     * Повертає кількість днів дії рецепта.
     *
     * @return Дійсна кількість днів дії рецепта.
     */
    public int getPrescriptionValidityDays() {
        return prescriptionValidityDays;
    }

    /**
     * Встановлює кількість днів дії рецепта.
     *
     * @param prescriptionValidityDays Дійсний термін дії рецепта у днях (> 0).
     * @throws IllegalArgumentException якщо значення менше або дорівнює 0.
     */
    public void setPrescriptionValidityDays(int prescriptionValidityDays) {
        if (prescriptionValidityDays <= 0)
            throw new IllegalArgumentException("\nPrescription validity must be positive.");
        this.prescriptionValidityDays = prescriptionValidityDays;
    }

    /**
     * Повертає активну речовину засобу.
     *
     * @return Об'єкт {@link ActiveIngredient}, що представляє діючу речовину.
     */
    public ActiveIngredient getActiveIngredient() {
        return activeIngredient;
    }

    /**
     * Встановлює активну речовину засобу.
     *
     * @param activeIngredient Діюча речовина (не може бути null).
     * @throws IllegalArgumentException якщо значення null.
     */
    public void setActiveIngredient(ActiveIngredient activeIngredient) {
        if (activeIngredient == null)
            throw new IllegalArgumentException("\nActive ingredient cannot be null.");
        this.activeIngredient = activeIngredient;
    }

    /**
     * Створює і повертає копію поточного об'єкта {@code PrescriptionMedicine}.
     *
     * Використовує копіювальний конструктор для створення нового об'єкта.
     *
     * @return новий об'єкт {@code PrescriptionMedicine}, який є копією поточного
     *         об'єкта
     */
    @Override
    public Medicine clone() {
        return new PrescriptionMedicine(this);
    }

    /**
     * Повертає рядкове представлення об'єкта.
     *
     * @return Рядок, що містить основну інформацію про засіб.
     */
    @Override
    public String toString() {
        return String.format("%s, Ingredient: %s, Validity: %d days",
                super.toString(),
                activeIngredient,
                prescriptionValidityDays);
    }

    /**
     * Перевіряє рівність двох об'єктів за основними полями.
     *
     * @param obj Об'єкт для порівняння.
     * @return {@code true}, якщо об'єкти однакові за вмістом, {@code false} —
     *         інакше.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof PrescriptionMedicine))
            return false;
        PrescriptionMedicine other = (PrescriptionMedicine) obj;
        return this.prescriptionValidityDays == other.prescriptionValidityDays &&
                this.activeIngredient == other.activeIngredient;
    }

    /**
     * Обчислює хеш-код для об'єкта {@code HerbalRemedy}.
     *
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prescriptionValidityDays, activeIngredient);
    }
}
