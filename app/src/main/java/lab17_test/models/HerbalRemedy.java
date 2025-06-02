package lab17_test.models;

import java.util.Objects;

import lab17_test.enums.DosageForm;
import lab17_test.enums.PlantOrigin;
import lab17_test.enums.PrescriptionType;

/**
 * Клас, що представляє рослинний або натуральний засіб.
 * Наслідує клас {@link Medicine}.
 */
public class HerbalRemedy extends Medicine {
    private PlantOrigin plantOrigin; // Вид рослини
    private boolean certifiedOrganic; // Чи сертифікований засіб як органічний

    /**
     * Конструктор для створення рослинного засобу.
     *
     * @param name             Назва лікарського засобу.
     * @param manufacturer     Виробник.
     * @param price            Ціна.
     * @param dosageForm       Лікарська форма.
     * @param plantOrigin      Походження рослини.
     * @param certifiedOrganic Чи сертифікований засіб.
     */
    public HerbalRemedy(String name, String manufacturer, double price,
            DosageForm dosageForm, PlantOrigin plantOrigin,
            boolean certifiedOrganic) {
        super(name, manufacturer, price, dosageForm);
        setPlantOrigin(plantOrigin);
        setCertifiedOrganic(certifiedOrganic);
        setPrescriptionRequired(PrescriptionType.NOT_REQUIRED);
    }

    /**
     * Копіювальний конструктор.
     *
     * @param other Об'єкт {@code HerbalRemedy}, з якого копіюються дані.
     * @throws IllegalArgumentException якщо переданий об'єкт дорівнює {@code null}.
     */
    public HerbalRemedy(HerbalRemedy other) {
        super(other);
        this.plantOrigin = other.plantOrigin;
        this.certifiedOrganic = other.certifiedOrganic;
        setPrescriptionRequired(PrescriptionType.NOT_REQUIRED);
    }

    /**
     * Повертає походження рослини.
     *
     * @return Enum {@link PlantOrigin}.
     */
    public PlantOrigin getPlantOrigin() {
        return plantOrigin;
    }

    /**
     * Встановлює походження рослини.
     *
     * @param plantOrigin Значення типу {@link PlantOrigin}.
     * @throws IllegalArgumentException якщо значення {@code null}.
     */
    public void setPlantOrigin(PlantOrigin plantOrigin) {
        if (plantOrigin == null)
            throw new IllegalArgumentException("\nPlant origin cannot be null.");
        this.plantOrigin = plantOrigin;
    }

    /**
     * Перевіряє, чи засіб сертифіковано як органічний.
     *
     * @return {@code true}, якщо сертифікований.
     */
    public boolean isCertifiedOrganic() {
        return certifiedOrganic;
    }

    /**
     * Встановлює статус сертифікації.
     *
     * @param certifiedOrganic {@code true}, якщо сертифікований.
     */
    public void setCertifiedOrganic(boolean certifiedOrganic) {
        this.certifiedOrganic = certifiedOrganic;
    }

    /**
     * Створює і повертає копію поточного об'єкта {@code HerbalRemedy}.
     *
     * Використовує копіювальний конструктор для створення нового об'єкта.
     *
     * @return новий об'єкт {@code HerbalRemedy}, який є копією поточного об'єкта
     */
    @Override
    public Medicine clone() {
        return new HerbalRemedy(this);
    }

    /**
     * Повертає рядкове представлення об'єкта.
     *
     * @return Строка з інформацією про засіб.
     */
    @Override
    public String toString() {
        return String.format("%s, Plant Origin: %s, Certified Organic: %s",
                super.toString(),
                plantOrigin,
                certifiedOrganic ? "Yes" : "No");
    }

    /**
     * Перевіряє рівність між об'єктами.
     *
     * @param obj Об'єкт для порівняння.
     * @return {@code true}, якщо об'єкти рівні.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof HerbalRemedy))
            return false;
        HerbalRemedy other = (HerbalRemedy) obj;
        return this.certifiedOrganic == other.certifiedOrganic &&
                this.plantOrigin == other.plantOrigin;
    }

    /**
     * Обчислює хеш-код для об'єкта {@code HerbalRemedy}.
     *
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), certifiedOrganic, plantOrigin);
    }
}
