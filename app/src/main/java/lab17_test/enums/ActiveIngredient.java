package lab17_test.enums;

/**
 * Перелік основних фармакологічних діючих речовин, що застосовуються в
 * рецептурних препаратах.
 */
public enum ActiveIngredient {
    // Антибіотики
    AMOXICILLIN("Амоксицилiн"),
    AZITHROMYCIN("Азитромiцин"),
    CIPROFLOXACIN("Ципрофлоксацин"),
    DOXYCYCLINE("Доксициклiн"),

    // Гормональні засоби
    INSULIN("Iнсулiн"),
    LEVOTHYROXINE("Левотироксин"),
    PREDNISONE("Преднiзолон"),

    // Серцево-судинні
    AMLODIPINE("Амлодипiн"),
    ATORVASTATIN("Аторвастатин"),
    ENALAPRIL("Еналаприл"),

    // Протидіабетичні
    METFORMIN("Метформiн"),
    GLICLAZIDE("Глiклазид"),

    // Психотропні / протиепілептичні
    DIAZEPAM("Дiазепам"),
    SERTRALINE("Сертралiн"),
    CARBAMAZEPINE("Карбамазепiн"),
    RISPERIDONE("Рисперидон"),

    // Протизапальні та знеболювальні
    IBUPROFEN("Iбупрофен"),
    KETOROLAC("Кеторолак"),
    DICLOFENAC("Диклофенак"),

    // Засоби для ШКТ
    OMEPRAZOLE("Омепразол");

    private final String displayName;

    ActiveIngredient(String displayName) {
        this.displayName = displayName;
    }

    public static ActiveIngredient fromDisplayName(String displayName) {
        for (ActiveIngredient type : values()) {
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
