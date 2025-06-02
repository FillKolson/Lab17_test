package lab17_test.enums;

/**
 * Перелік лікарських форм, у яких можуть випускатися препарати.
 */
public enum DosageForm {
    TABLETS("Таблетки"),
    CAPSULES("Капсули"),
    SYRUP("Сироп"),
    DROPS("Краплi"),
    OINTMENT("Мазь"),
    GEL("Гель"),
    INJECTIONS("Iн'єкцiї"),
    AEROSOL("Аерозоль"),
    SUSPENSION("Суспензiя"),
    PATCH("Пластир");

    private final String displayName;

    DosageForm(String displayName) {
        this.displayName = displayName;
    }

    public static DosageForm fromDisplayName(String displayName) {
        for (DosageForm form : values()) {
            if (form.displayName.equalsIgnoreCase(displayName)) {
                return form;
            }
        }
        throw new IllegalArgumentException("Невiдома лікарська форма: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
