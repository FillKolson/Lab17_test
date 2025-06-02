package lab17_test.utils;

import java.util.Scanner;

import lab17_test.enums.ActiveIngredient;
import lab17_test.enums.DosageForm;
import lab17_test.enums.Indication;
import lab17_test.enums.PlantOrigin;
import lab17_test.enums.VitaminType;

/**
 * Утилітний клас для отримання і валідації введення користувача з консолі.
 * Містить методи для зчитування рядків, чисел, а також вибору значень з enum.
 */
public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Зчитує непорожній текстовий рядок від користувача.
     *
     * @param prompt повідомлення-підказка для користувача
     * @return валідний (непорожній) рядок
     */
    public static String getValidString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nПоле не може бути порожнiм.");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Зчитує число з плаваючою точкою, більше нуля.
     *
     * @param prompt повідомлення-підказка
     * @return дійсне число типу {@code double} > 0
     */
    public static double getValidDouble(String prompt) {
        double value = -1;
        boolean isValid = false;
        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nПоле не може бути порожнiм.");
            } else {
                try {
                    value = Double.parseDouble(input);
                    if (value <= 0) {
                        System.out.println("\nЗначення має бути > 0.");
                    } else {
                        isValid = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nПомилка! Введiть число.");
                }
            }
        } while (!isValid);
        return value;
    }

    /**
     * Зчитує ціле число більше нуля.
     *
     * @param prompt повідомлення-підказка
     * @return дійсне ціле число > 0
     */
    public static int getValidInt(String prompt) {
        int value = -1;
        boolean isValid = false;
        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nПоле не може бути порожнiм.");
            } else {
                try {
                    value = Integer.parseInt(input);
                    if (value <= 0) {
                        System.out.println("\nЗначення має бути > 0.");
                    } else {
                        isValid = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nПомилка! Введiть цiле число.");
                }
            }
        } while (!isValid);
        return value;
    }

    /**
     * Зчитує відповідь користувача типу "так/нi" (англійською або українською).
     *
     * @return {@code true}, якщо відповідь "так", {@code false} — якщо "нi"
     */
    public static boolean getYesNo() {
        String input;
        do {
            System.out.print("\nСертифiкований (так/нi): ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.matches("(так|нi|yes|no|y|n)"));
        return input.startsWith("т") || input.startsWith("y");
    }

    /**
     * Дає змогу користувачу обрати форму дозування зі списку {@link DosageForm}.
     *
     * @return вибрана форма дозування
     */
    public static DosageForm getValidDosageForm() {
        System.out.println("\nОберiть форму дозування:");
        DosageForm[] forms = DosageForm.values();
        for (int i = 0; i < forms.length; i++) {
            System.out.printf("%d. %s\n", i + 1, forms[i]);
        }
        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > forms.length) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", forms.length);
            }
        } while (choice < 1 || choice > forms.length);
        return forms[choice - 1];
    }

    /**
     * Дає змогу обрати активний інгредієнт зі списку {@link ActiveIngredient}.
     *
     * @return вибраний активний інгредієнт
     */
    public static ActiveIngredient getValidActiveIngredient() {
        System.out.println("\nОберiть активний iнгредiєнт:");
        ActiveIngredient[] ingredients = ActiveIngredient.values();
        for (int i = 0; i < ingredients.length; i++) {
            System.out.printf("%d. %s\n", i + 1, ingredients[i]);
        }
        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > ingredients.length) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", ingredients.length);
            }
        } while (choice < 1 || choice > ingredients.length);
        return ingredients[choice - 1];
    }

    /**
     * Дає змогу обрати походження рослини зі списку {@link PlantOrigin}.
     *
     * @return вибране походження рослини
     */
    public static PlantOrigin getValidPlantOrigin() {
        System.out.println("\nОберiть походження рослини:");
        PlantOrigin[] origins = PlantOrigin.values();
        for (int i = 0; i < origins.length; i++) {
            System.out.printf("%d. %s\n", i + 1, origins[i]);
        }
        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > origins.length) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", origins.length);
            }
        } while (choice < 1 || choice > origins.length);
        return origins[choice - 1];
    }

    /**
     * Дає змогу обрати показання до застосування зі списку {@link Indication}.
     *
     * @return вибрані показання
     */
    public static Indication getValidIndication() {
        System.out.println("\nОберiть показання до застосування:");
        Indication[] indications = Indication.values();
        for (int i = 0; i < indications.length; i++) {
            System.out.printf("%d. %s\n", i + 1, indications[i]);
        }
        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > indications.length) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", indications.length);
            }
        } while (choice < 1 || choice > indications.length);
        return indications[choice - 1];
    }

    /**
     * Дає змогу обрати тип вітаміну зі списку {@link VitaminType}.
     *
     * @return вибраний тип вітаміну
     */
    public static VitaminType getValidVitaminType() {
        System.out.println("\nОберiть тип вiтамiну:");
        VitaminType[] types = VitaminType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d. %s\n", i + 1, types[i]);
        }
        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > types.length) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", types.length);
            }
        } while (choice < 1 || choice > types.length);
        return types[choice - 1];
    }
}
