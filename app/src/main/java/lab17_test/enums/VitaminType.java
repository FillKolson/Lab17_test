package lab17_test.enums;

/**
 * Перелік типів вітамінів та вітамінних комплексів, що використовуються у
 * вітамінних добавках.
 */
public enum VitaminType {
    // Основні водорозчинні вітаміни
    VITAMIN_B1("Вiтамiн B1 (Тiамiн)"),
    VITAMIN_B2("Вiтамiн B2 (Рибофлавiн)"),
    VITAMIN_B3("Вiтамiн B3 (Нiацин)"),
    VITAMIN_B5("Вiтамiн B5 (Пантотенова кислота)"),
    VITAMIN_B6("Вiтамiн B6 (Пiридоксин)"),
    VITAMIN_B7("Вiтамiн B7 (Бiотин)"),
    VITAMIN_B9("Вiтамiн B9 (Фолiєва кислота)"),
    VITAMIN_B12("Вiтамiн B12 (Кобаламiн)"),
    VITAMIN_C("Вiтамiн C (Аскорбiнова кислота)"),

    // Жиророзчинні вітаміни
    VITAMIN_A("Вiтамiн A"),
    VITAMIN_D("Вiтамiн D"),
    VITAMIN_E("Вiтамiн E"),
    VITAMIN_K("Вiтамiн K"),

    // Вітамінні комплекси
    MULTIVITAMIN("Мультивiтамiнний комплекс"),
    VITAMIN_B_COMPLEX("Комплекс вiтамiнiв групи B"),
    IMMUNE_COMPLEX("Iмуностимулюючий комплекс"),
    SKIN_HAIR_NAILS_COMPLEX("Комплекс для шкiри, волосся та нiгтiв"),
    ENERGY_COMPLEX("Енергетичний комплекс"),
    ANTIOXIDANT_COMPLEX("Антиоксидантний комплекс");

    private final String displayName;

    VitaminType(String displayName) {
        this.displayName = displayName;
    }

    public static VitaminType fromDisplayName(String displayName) {
        for (VitaminType type : values()) {
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
