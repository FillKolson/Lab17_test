package lab17_test.models;

import java.util.Objects;

import lab17_test.enums.DosageForm;
import lab17_test.enums.PrescriptionType;

/**
 * Клас, що представляє лікарський засіб.
 */
public abstract class Medicine {
    private String name;
    private String manufacturer;
    private double price;
    private PrescriptionType prescriptionRequired;
    private DosageForm dosageForm;

    /**
     * Конструктор для створення об'єкта Medicine.
     * 
     * @param name         Назва лікарського засобу.
     * @param manufacturer Виробник.
     * @param price        Ціна в гривнях.
     * @param dosageForm   Лікарська форма (таблетки, капсули, мазь тощо).
     */
    public Medicine(String name, String manufacturer, double price, DosageForm dosageForm) {
        setName(name);
        setManufacturer(manufacturer);
        setPrice(price);
        setDosageForm(dosageForm);
        setPrescriptionRequired(PrescriptionType.NOT_REQUIRED);
    }

    /**
     * Копіювальний конструктор.
     * 
     * @param other інший об'єкт типу Medicine.
     */
    public Medicine(Medicine other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from a null object.");
        }
        setName(other.name);
        setManufacturer(other.manufacturer);
        setPrice(other.price);
        setDosageForm(other.dosageForm);
        setPrescriptionRequired(PrescriptionType.NOT_REQUIRED);
    }

    /**
     * @return Назва лікарського засобу.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву лікарського засобу.
     * 
     * @param name Назва (не порожня).
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("The name cannot be empty.");
        this.name = name;
    }

    /**
     * @return Виробник лікарського засобу.
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Встановлює виробника.
     * 
     * @param manufacturer Виробник (не порожній).
     */
    public void setManufacturer(String manufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty())
            throw new IllegalArgumentException("The manufacturer cannot be empty.");
        this.manufacturer = manufacturer;
    }

    /**
     * @return Ціна лікарського засобу.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну.
     * 
     * @param price Ціна (позитивне число).
     */
    public void setPrice(double price) {
        if (price <= 0)
            throw new IllegalArgumentException("The price must be positive.");
        this.price = price;
    }

    /**
     * @return Чи потрібен рецепт.
     */
    public PrescriptionType getPrescriptionRequired() {
        return prescriptionRequired;
    }

    /**
     * Встановлює тип необхідності рецепта для лікарського засобу.
     *
     * @param prescriptionRequired Тип рецептурної вимоги (REQUIRED або
     *                             NOT_REQUIRED).
     * @throws IllegalArgumentException якщо передано значення {@code null}.
     */
    protected void setPrescriptionRequired(PrescriptionType prescriptionRequired) {
        if (prescriptionRequired == null)
            throw new IllegalArgumentException("Prescription type cannot be null.");
        this.prescriptionRequired = prescriptionRequired;
    }

    /**
     * Повертає лікарську форму (наприклад, таблетки, сироп тощо).
     *
     * @return Об'єкт {@link DosageForm}, що представляє лікарську форму засобу.
     */
    public DosageForm getDosageForm() {
        return dosageForm;
    }

    /**
     * Встановлює лікарську форму засобу.
     *
     * @param dosageForm Значення типу {@link DosageForm}. Не може бути
     *                   {@code null}.
     * @throws IllegalArgumentException якщо передано {@code null} як лікарську
     *                                  форму.
     */
    public void setDosageForm(DosageForm dosageForm) {
        if (dosageForm == null)
            throw new IllegalArgumentException("The dosage form value cannot be null.");
        this.dosageForm = dosageForm;
    }

    /**
     * Створює і повертає копію поточного об'єкта {@code Medicine}.
     *
     * @return новий об'єкт, який є копією цього {@code Medicine}
     */
    public abstract Medicine clone();

    /**
     * @return Рядкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Manufacturer: %s, Price: %.2f UAH, Dosage Form: %s, Prescription: %s",
                getName(), getManufacturer(), getPrice(), getDosageForm(), getPrescriptionRequired());
    }

    /**
     * Перевизначений метод {@code equals} для порівняння об'єктів {@code Medicine}.
     *
     * @param obj Об'єкт для порівняння.
     * @return {@code true}, якщо об'єкти мають однакову назву, виробника та
     *         лікарську форму;
     *         {@code false} — в іншому випадку.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Medicine))
            return false;
        Medicine other = (Medicine) obj;
        return Objects.equals(name, other.name) &&
                Objects.equals(manufacturer, other.manufacturer) &&
                dosageForm == other.dosageForm;
    }

    /**
     * Обчислює хеш-код для об'єкта {@code Medicine} на основі його назви,
     * виробника та лікарської форми.
     *
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, dosageForm);
    }

}
