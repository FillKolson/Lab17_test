package lab17_test.utils;

import java.util.ArrayList;

import lab17_test.enums.ActiveIngredient;
import lab17_test.enums.DosageForm;
import lab17_test.enums.Indication;
import lab17_test.enums.PlantOrigin;
import lab17_test.enums.VitaminType;
import lab17_test.models.HerbalRemedy;
import lab17_test.models.Medicine;
import lab17_test.models.OverTheCounterMedicine;
import lab17_test.models.Pharmacy;
import lab17_test.models.PrescriptionMedicine;
import lab17_test.models.VitaminSupplement;

/**
 * Утилітний клас для парсингу та серіалізації об'єктів {@link Pharmacy} та
 * {@link Medicine}.
 * Здійснює перетворення з текстового представлення у відповідні об'єкти і
 * навпаки.
 */
public class MedicineParser {

    /**
     * Парсить список рядків, де перший рядок містить інформацію про аптеку,
     * а всі наступні — медикаменти з відповідною кількістю.
     *
     * @param lines список рядків, зчитаних з файлу
     * @return об'єкт {@link Pharmacy} або {@code null} у разі помилки
     */
    public static Pharmacy parsePharmacyWithInventory(ArrayList<String> lines) {
        if (lines == null || lines.isEmpty()) {
            System.out.println("Файл порожній або недоступний.");
            return null;
        }

        String header = lines.get(0).trim();
        Pharmacy pharmacy = parsePharmacy(header);
        if (pharmacy == null)
            return null;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.isEmpty()) {
                parseMedicine(line, pharmacy);
            }
        }

        return pharmacy;
    }

    /**
     * Парсить рядок з інформацією про аптеку.
     * Очікуваний формат: {@code Pharmacy;Назва;Адреса}.
     *
     * @param line рядок із заголовком аптеки
     * @return об'єкт {@link Pharmacy} або {@code null} у разі помилки формату
     */
    public static Pharmacy parsePharmacy(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length != 3 || !parts[0].equals("Pharmacy")) {
                System.out.println("Неправильний формат заголовка аптеки: " + line);
                return null;
            }
            return new Pharmacy(parts[1].trim(), parts[2].trim());
        } catch (Exception e) {
            System.out.println("Помилка при розборі аптеки: " + line);
            return null;
        }
    }

    /**
     * Парсить рядок, що містить медикамент та його кількість, і додає його до
     * інвентаря аптеки.
     * Очікуваний формат: {@code тип;назва;виробник;ціна;форма;...;кількість}
     *
     * @param line     рядок із медикаментом
     * @param pharmacy аптека, до якої додається препарат
     */
    public static void parseMedicine(String line, Pharmacy pharmacy) {
        try {
            String[] parts = line.split(";");
            if (parts.length < 8) {
                System.out.println("Недостатньо даних у рядку: " + line);
                return;
            }

            String type = parts[0];
            String name = parts[1];
            String manufacturer = parts[2];
            double price = Double.parseDouble(parts[3]);
            DosageForm form = DosageForm.fromDisplayName(parts[4]);

            int quantity = Integer.parseInt(parts[parts.length - 1]);
            Medicine med = null;

            switch (type) {
                case "Prescription" -> {
                    int validity = Integer.parseInt(parts[5]);
                    ActiveIngredient ingredient = ActiveIngredient.fromDisplayName(parts[6]);
                    med = new PrescriptionMedicine(name, manufacturer, price, form, validity, ingredient);
                }
                case "Herbal" -> {
                    PlantOrigin origin = PlantOrigin.fromDisplayName(parts[5]);
                    boolean certified = Boolean.parseBoolean(parts[6]);
                    med = new HerbalRemedy(name, manufacturer, price, form, origin, certified);
                }
                case "OTC" -> {
                    int maxDays = Integer.parseInt(parts[5]);
                    Indication indication = Indication.fromDisplayName(parts[6]);
                    med = new OverTheCounterMedicine(name, manufacturer, price, form, maxDays, indication);
                }
                case "Vitamin" -> {
                    VitaminType vitaminType = VitaminType.fromDisplayName(parts[5]);
                    int dailyDosage = Integer.parseInt(parts[6]);
                    med = new VitaminSupplement(name, manufacturer, price, form, vitaminType, dailyDosage);
                }
                default -> {
                    System.out.println("Невідомий тип медикаменту: " + type);
                    return;
                }
            }

            if (med != null) {
                pharmacy.addNewMedicine(med, quantity);
            }

        } catch (Exception e) {
            System.out.println("Помилка при розборі медикаменту з кількістю: " + line);
        }
    }

    /**
     * Серіалізує аптеку до текстового представлення, яке включає
     * заголовок аптеки та перелік медикаментів з кількістю.
     *
     * @param pharmacy об'єкт {@link Pharmacy} для серіалізації
     * @return рядок, що представляє аптеку у форматі, придатному для збереження у
     *         файл
     */
    public static String serializePharmacy(Pharmacy pharmacy) {
        StringBuilder sb = new StringBuilder();

        // Заголовок
        sb.append("Pharmacy;")
                .append(pharmacy.getName()).append(";")
                .append(pharmacy.getAddress()).append("\n");

        // Інвентар
        for (Pharmacy.InventoryItem item : pharmacy.getInventory()) {
            sb.append(serializeMedicine(item.getMedicine()))
                    .append(";")
                    .append(item.getQuantity())
                    .append("\n");
        }

        return sb.toString();
    }

    /**
     * Серіалізує медикамент у текстовий рядок без інформації про кількість.
     *
     * @param med об'єкт {@link Medicine}, який потрібно серіалізувати
     * @return рядок з інформацією про медикамент
     */
    public static String serializeMedicine(Medicine med) {
        if (med instanceof PrescriptionMedicine pm) {
            return "Prescription;" + pm.getName() + ";" + pm.getManufacturer() + ";" + pm.getPrice() + ";" +
                    pm.getDosageForm() + ";" + pm.getPrescriptionValidityDays() + ";" + pm.getActiveIngredient();
        } else if (med instanceof HerbalRemedy hr) {
            return "Herbal;" + hr.getName() + ";" + hr.getManufacturer() + ";" + hr.getPrice() + ";" +
                    hr.getDosageForm() + ";" + hr.getPlantOrigin() + ";" + hr.isCertifiedOrganic();
        } else if (med instanceof OverTheCounterMedicine otc) {
            return "OTC;" + otc.getName() + ";" + otc.getManufacturer() + ";" + otc.getPrice() + ";" +
                    otc.getDosageForm() + ";" + otc.getMaxRecommendedDaysOfUse() + ";" + otc.getIndication();
        } else if (med instanceof VitaminSupplement vs) {
            return "Vitamin;" + vs.getName() + ";" + vs.getManufacturer() + ";" + vs.getPrice() + ";" +
                    vs.getDosageForm() + ";" + vs.getVitaminType() + ";" + vs.getDailyDosageMg();
        }
        return "";
    }
}
