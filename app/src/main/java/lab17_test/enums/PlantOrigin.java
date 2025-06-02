package lab17_test.enums;

/**
 * Перелік основних рослин, що використовуються у фітопрепаратах.
 */
public enum PlantOrigin {
    ECHINACEA("Ехiнацея"),
    GINGER("Iмбир"),
    GINSENG("Женьшень"),
    PEPPERMINT("М'ята перцева"),
    LAVENDER("Лаванда"),
    NETTLE("Кропива"),
    CHAMOMILE("Ромашка"),
    VALERIAN("Валерiана");

    private final String displayName;

    PlantOrigin(String displayName) {
        this.displayName = displayName;
    }

    public static PlantOrigin fromDisplayName(String displayName) {
        for (PlantOrigin type : values()) {
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
