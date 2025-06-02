package lab17_test.enums;

/**
 * Перелік основних показань до застосування безрецептурних лікарських засобів.
 */
public enum Indication {
    COLD("Застуда"),
    FLU("Грип"),
    HEADACHE("Головний бiль"),
    ALLERGY("Алергiя"),
    INDIGESTION("Розлад травлення"),
    COUGH("Кашель"),
    PAIN("Бiль"),
    SLEEP_DISORDER("Порушення сну"),
    SKIN_IRRITATION("Подразнення шкiри");

    private final String displayName;

    Indication(String displayName) {
        this.displayName = displayName;
    }

    public static Indication fromDisplayName(String displayName) {
        for (Indication type : values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Невiдомий тип вiтамiну: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
