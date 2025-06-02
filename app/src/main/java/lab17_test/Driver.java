package lab17_test;

// Шевченко Кирил
// ІН-26-3
// ООП на мові Java
// Лабораторна робота 16
// Кінцевий строк подання: 28 травня 2025
// Використано часу: 2 години
// Ця Програма керує аптекою та списком лікарських засобів в ній, зберігаючі всі інформацію
//      у файлі, дозволяє додавати нові препарати, виконувати пошук лікарських засобів
//      згідно критеріям, перевіряти унікальність записів та виводити їхню інформацію.
// Це моя власна робота. Не було використано жодної несанкціонованої допомоги.
// Використання штучного інтелекту: ні

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import lab17_test.enums.ActiveIngredient;
import lab17_test.enums.DosageForm;
import lab17_test.enums.Indication;
import lab17_test.enums.PlantOrigin;
import lab17_test.enums.VitaminType;
import lab17_test.models.HerbalRemedy;
import lab17_test.models.Medicine;
import lab17_test.models.OverTheCounterMedicine;
import lab17_test.models.Pharmacy;
import lab17_test.models.Pharmacy.InventoryItem;
import lab17_test.models.PrescriptionMedicine;
import lab17_test.models.VitaminSupplement;
import lab17_test.utils.MedicineParser;

import static lab17_test.utils.InputUtils.*;

/**
 * Головний клас, що забезпечує консольний інтерфейс користувача для взаємодії з
 * аптекою.
 * Дозволяє здійснювати пошук, додавання та перегляд медикаментів, а також
 * зберігати дані до файлу.
 */
public class Driver {

    /** Назва файлу, з якого зчитуються/у який записуються дані про аптеку. */
    private static final String FILE_NAME = "input.txt";

    /**
     * Точка входу в програму. Керує основним циклом взаємодії з користувачем.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Завантаження аптеки з файлу або створення нової
        Pharmacy apteka = readFromFile();
        if (apteka == null) {
            System.out.println("\nСтворення нової аптеки.");
            String name = getValidString("Введiть назву аптеки: ");
            String address = getValidString("Введiть адресу аптеки: ");
            apteka = new Pharmacy(name, address);
        }

        int value;
        do {
            System.out.println("\n1. Пошук ліків");
            System.out.println("2. Створити новi лiки");
            System.out.println("3. Модифікувати лікарський засіб");
            System.out.println("4. Видалити лікарський засіб");
            System.out.println("5. Вивести iнформацiю про всi лiки");
            System.out.println("6. Вивести вiдсортовану iнформацiю про всi лiки");
            System.out.println("7. Зберегти та вийти");

            value = getValidInt("\nВведiть номер меню: ");
            switch (value) {
                case 1:
                    searchMenu(scanner, apteka);
                    break;
                case 2:
                    addMedicine(scanner, apteka);
                    break;
                case 3:
                    modificateInventory(scanner, apteka);
                    break;
                case 4:
                    DeleteInventory(scanner, apteka);
                    break;
                case 5:
                    printInventory(apteka.getInventory());
                    break;
                case 6:
                    printSortedInventory(apteka);
                    break;
                case 7:
                    System.out.println("\nЗбереження до файлу та завершення...");
                    writeToFile(apteka);
                    break;
                default:
                    System.out.println("\nВведiть число вiд 1 до 7.");
            }
        } while (value != 7);

        scanner.close();
    }

    /**
     * Інтерактивно зчитує дані користувача з консолі та додає новий медикамент до
     * інвентаря.
     *
     * @param scanner об'єкт Scanner для зчитування введення
     */
    public static void addMedicine(Scanner scanner, Pharmacy apteka) {
        int value;
        do {
            System.out.println("\nОберiть тип лiкарського засобу або 1 для повернення:");
            System.out.println("1. Повернутись до головного меню");
            System.out.println("2. Prescription Medicine");
            System.out.println("3. Herbal Remedy");
            System.out.println("4. Over-the-Counter Medicine");
            System.out.println("5. Vitamin Supplement");

            value = getValidInt("\nВведiть номер меню: ");

            if (value < 1 || value > 5) {
                System.out.println("\nВведiть число вiд 1 до 5.");
            }

            if (value == 1)
                return;

        } while (value < 1 || value > 5);

        String name = getValidString("\nНазва: ");
        String manufacturer = getValidString("\nВиробник: ");
        double price = getValidDouble("\nЦiна: ");
        DosageForm dosageForm = getValidDosageForm();
        Medicine newMedicine = null;

        switch (value) {
            case 2 -> {
                ActiveIngredient ingredient = getValidActiveIngredient();
                int validity = getValidInt("\nТермін дії рецепту (днiв): ");
                newMedicine = new PrescriptionMedicine(name, manufacturer, price, dosageForm, validity, ingredient);
            }
            case 3 -> {
                PlantOrigin origin = getValidPlantOrigin();
                boolean certified = getYesNo();
                newMedicine = new HerbalRemedy(name, manufacturer, price, dosageForm, origin, certified);
            }
            case 4 -> {
                int maxDays = getValidInt("\nМаксимальна тривалiсть використання (днiв): ");
                Indication indication = getValidIndication();
                newMedicine = new OverTheCounterMedicine(name, manufacturer, price, dosageForm, maxDays, indication);
            }
            case 5 -> {
                VitaminType vitaminType = getValidVitaminType();
                int dailyDosage = getValidInt("\nДобова доза (мг): ");
                newMedicine = new VitaminSupplement(name, manufacturer, price, dosageForm, vitaminType, dailyDosage);
            }
        }

        int quantity = getValidInt("\nКiлькiсть: ");

        if (apteka.addNewMedicine(newMedicine, quantity)) {
            System.out.println("\nЛiкарський засiб додано.");
        } else {
            System.out.println("\nОновлено наявну кiлькiсть.");
        }

    }

    /**
     * Забезпечує інтерфейс користувача для модифікування властивостей обраного
     * лікарського засобу з інвентарю аптеки.
     * 
     * <p>
     * Метод виконує такі дії:
     * <ul>
     * <li>Отримує копію поточного списку інвентаря аптеки.</li>
     * <li>Виводить список медикаментів для вибору.</li>
     * <li>Дозволяє вибрати препарат для редагування.</li>
     * <li>Створює копію обраного препарату для внесення змін.</li>
     * <li>Виводить меню атрибутів, які можна модифікувати, з урахуванням типу
     * препарату.</li>
     * <li>Зчитує нове значення обраного атрибуту з консолі і оновлює копію
     * препарату.</li>
     * <li>Оновлює запис в інвентарі аптеки, замінюючи старий елемент новим.</li>
     * <li>Повідомляє користувача про успіх або помилку оновлення.</li>
     * </ul>
     * 
     * @param scanner об'єкт {@link Scanner} для зчитування вводу користувача
     * @param apteka  об'єкт {@link Pharmacy}, інвентар якого редагується
     */
    public static void modificateInventory(Scanner scanner, Pharmacy apteka) {
        ArrayList<InventoryItem> inventory = new ArrayList<>(apteka.getInventory()); // копія списку

        if (inventory.isEmpty()) {
            System.out.println("\nIнвентар порожнiй.");
            return;
        }

        System.out.println("\nОберiть лікарський засіб для модифікування:\n");
        printInventory(inventory);

        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > inventory.size()) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", inventory.size());
            }
        } while (choice < 1 || choice > inventory.size());

        // Внутрішня змінна, яка зберігає обраний об'єкт та препарат
        InventoryItem existingItem = inventory.get(choice - 1);
        Medicine originalMedicine = existingItem.getMedicine();

        // Створення копії:
        Medicine clonedMedicine = originalMedicine.clone();
        InventoryItem newItem = apteka.new InventoryItem(clonedMedicine, existingItem.getQuantity());

        int attrChoice;

        do {
            System.out.println("\nОбраний препарат:\n");
            System.out.println(existingItem.getMedicine() + " | Кiлькiсть: " + existingItem.getQuantity());

            System.out.println("\nОберiть атрибут для модифікування:");
            System.out.println("1. Кiлькiсть");
            System.out.println("2. Назва");
            System.out.println("3. Виробник");
            System.out.println("4. Цiна");
            System.out.println("5. Форма");

            // Специфічні поля дочірніх класів
            if (originalMedicine instanceof PrescriptionMedicine) {
                System.out.println("6. Термін дії рецепту");
                System.out.println("7. Активна діюча речовина");
            } else if (originalMedicine instanceof HerbalRemedy) {
                System.out.println("6. Вид рослини");
                System.out.println("7. Чи сертифікований засіб як органічний");
            } else if (originalMedicine instanceof OverTheCounterMedicine) {
                System.out.println("6. Показання до застосування");
                System.out.println("7. Максимальний рекомендований термін вживання");
            } else if (originalMedicine instanceof VitaminSupplement) {
                System.out.println("6. Тип вітаміну або комплексу");
                System.out.println("7. Добова доза (мг)");
            }

            attrChoice = getValidInt("\nВаш вибір: ");

            if (attrChoice < 1 || attrChoice > 7) {
                System.out.println("\nВведiть число вiд 1 до 7\n");
            }
        } while (attrChoice < 1 || attrChoice > 7);

        switch (attrChoice) {
            case 1 -> {
                int newQuantity = getValidInt("\nНова кiлькiсть: ");
                newItem.setQuantity(newQuantity);
            }
            case 2 -> {
                String newName = getValidString("\nНова назва: ");
                clonedMedicine.setName(newName);
            }
            case 3 -> {
                String newManufacturer = getValidString("\nНовий виробник: ");
                clonedMedicine.setManufacturer(newManufacturer);
            }
            case 4 -> {
                double newPrice = getValidDouble("\nНова цiна: ");
                clonedMedicine.setPrice(newPrice);
            }
            case 5 -> {
                DosageForm newDosageForm = getValidDosageForm();
                clonedMedicine.setDosageForm(newDosageForm);
            }
            case 6 -> {
                if (clonedMedicine instanceof PrescriptionMedicine) {
                    PrescriptionMedicine md = (PrescriptionMedicine) clonedMedicine;
                    ActiveIngredient ingredient = getValidActiveIngredient();
                    md.setActiveIngredient(ingredient);
                } else if (clonedMedicine instanceof HerbalRemedy) {
                    HerbalRemedy md = (HerbalRemedy) clonedMedicine;
                    PlantOrigin origin = getValidPlantOrigin();
                    md.setPlantOrigin(origin);
                } else if (clonedMedicine instanceof OverTheCounterMedicine) {
                    OverTheCounterMedicine md = (OverTheCounterMedicine) clonedMedicine;
                    Indication indication = getValidIndication();
                    md.setIndication(indication);
                } else if (clonedMedicine instanceof VitaminSupplement) {
                    VitaminSupplement md = (VitaminSupplement) clonedMedicine;
                    VitaminType vitaminType = getValidVitaminType();
                    md.setVitaminType(vitaminType);
                }
            }
            case 7 -> {
                if (clonedMedicine instanceof PrescriptionMedicine) {
                    PrescriptionMedicine md = (PrescriptionMedicine) clonedMedicine;
                    int validity = getValidInt("\nТермін дії рецепту (днiв): ");
                    md.setPrescriptionValidityDays(validity);
                } else if (clonedMedicine instanceof HerbalRemedy) {
                    HerbalRemedy md = (HerbalRemedy) clonedMedicine;
                    boolean certified = getYesNo();
                    md.setCertifiedOrganic(certified);
                } else if (clonedMedicine instanceof OverTheCounterMedicine) {
                    OverTheCounterMedicine md = (OverTheCounterMedicine) clonedMedicine;
                    int maxDays = getValidInt("\nМаксимальна тривалiсть використання (днiв): ");
                    md.setMaxRecommendedDaysOfUse(maxDays);
                } else if (clonedMedicine instanceof VitaminSupplement) {
                    VitaminSupplement md = (VitaminSupplement) clonedMedicine;
                    int dailyDosage = getValidInt("\nДобова доза (мг): ");
                    md.setDailyDosageMg(dailyDosage);
                }
            }
            default -> System.out.println("\nНевiрний вибiр.");
        }

        boolean updated = apteka.update(existingItem, newItem);
        if (updated) {
            System.out.println("\nПрепарат успішно оновлено.");
        } else {
            System.out.println("\nПомилка при оновленні препарату.");
        }
    }

    private static void DeleteInventory(Scanner scanner, Pharmacy apteka) {
        ArrayList<InventoryItem> inventory = new ArrayList<>(apteka.getInventory()); // копія списку

        if (inventory.isEmpty()) {
            System.out.println("\nIнвентар порожнiй.");
            return;
        }

        System.out.println("\nОберiть лікарський засіб для видалення:\n");
        printInventory(inventory);

        int choice;
        do {
            choice = getValidInt("\nНомер: ");
            if (choice < 1 || choice > inventory.size()) {
                System.out.printf("\nВведiть число вiд 1 до %d.\n", inventory.size());
            }
        } while (choice < 1 || choice > inventory.size());

        // Внутрішня змінна, яка зберігає обраний об'єкт та препарат
        InventoryItem existingItem = inventory.get(choice - 1);

        boolean deleted = apteka.delete(existingItem);
        if (deleted) {
            System.out.println("\nПрепарат успішно видалено.");
        } else {
            System.out.println("\nПомилка при видаленні препарату.");
        }
    }

    /**
     * Відображає меню пошуку медикаментів у аптеці з різними критеріями.
     *
     * <p>
     * Метод забезпечує інтерфейс для користувача у консолі, де можна обрати
     * пошук медикаментів за назвою, виробником або за ціною (з умовами більше,
     * менше або дорівнює). Після введення параметрів виконується відповідний
     * пошук у об'єкті {@code Pharmacy} і результати виводяться у консоль.
     *
     * <p>
     * Пошук повторюється до вибору опції повернення до головного меню.
     *
     * @param scanner об'єкт {@link Scanner} для зчитування вводу користувача
     * @param apteka  об'єкт {@link Pharmacy}, у якому виконується пошук
     *                медикаментів
     */
    public static void searchMenu(Scanner scanner, Pharmacy apteka) {
        int value;
        ArrayList<InventoryItem> result;
        do {
            System.out.println("\n=== Пошук ===");
            System.out.println("1. Пошук за назвою");
            System.out.println("2. Пошук за виробником");
            System.out.println("3. Пошук за ціною");
            System.out.println("4. Повернутись до меню");

            value = getValidInt("\nВведiть номер меню: ");

            switch (value) {
                case 1 -> {
                    String name = getValidString("\nВведiть назву: ");
                    result = apteka.searchByName(name);
                    printInventory(result);
                }
                case 2 -> {
                    String manufacturer = getValidString("\nВведiть назву виробника: ");
                    result = apteka.searchByManufacturer(manufacturer);
                    printInventory(result);
                }
                case 3 -> {
                    int value2;
                    do {
                        System.out.println("\n1. Більше за ...");
                        System.out.println("2. Менше за ...");
                        System.out.println("3. Дорівнює ...");
                        value2 = getValidInt("\nВведiть номер меню: ");

                        if (value2 < 1 || value2 > 3) {
                            System.out.println("\nВведiть число вiд 1 до 3.");
                        }
                    } while (value2 < 1 || value2 > 3);

                    int price = getValidInt("\nВведiть ціну для пошуку: ");
                    result = apteka.searchByPrice(value2, price);
                    printInventory(result);
                }
                case 4 -> {
                    // вихід
                }
                default -> System.out.println("\nВведiть число вiд 1 до 4.");
            }
        } while (value != 4);
    }

    /**
     * Виводить відсортований список інвентарю за обраним критерієм.
     *
     * <p>
     * Користувачеві пропонуються наступні варіанти сортування:
     * <ul>
     * <li>{@code 1} – За назвою препарату (лексикографічно, без врахування
     * регістру)</li>
     * <li>{@code 2} – За виробником (лексикографічно, без врахування регістру)</li>
     * <li>{@code 3} – За ціною (у порядку зростання)</li>
     * <li>{@code 4} – Повернення до головного меню</li>
     * </ul>
     *
     * <p>
     * Сортування виконується на копії поточного списку {@code inventory}, щоб
     * уникнути зміни
     * основного списку. Кожен елемент копіюється через конструктор копіювання класу
     * {@link InventoryItem}.
     *
     * <p>
     * Після сортування список виводиться на консоль методом
     * {@code printInventory()}.
     *
     * <p>
     * У разі некоректного введення виводиться повідомлення про помилку та
     * пропонується повторне введення.
     */
    public static void printSortedInventory(Pharmacy apteka) {
        int value;
        do {
            System.out.println("\nВиберіть критерій сортування:");
            System.out.println("1. Назва");
            System.out.println("2. Виробник");
            System.out.println("3. Ціна");
            System.out.println("4. Повернутись до головного меню");
            value = getValidInt("\nВаш вибір: ");

            Comparator<InventoryItem> comparator = null;

            switch (value) {
                case 1:
                    comparator = (o1, o2) -> o1.getMedicine().getName()
                            .compareToIgnoreCase(o2.getMedicine().getName());
                    break;
                case 2:
                    comparator = (o1, o2) -> o1.getMedicine().getManufacturer()
                            .compareToIgnoreCase(o2.getMedicine().getManufacturer());
                    break;
                case 3:
                    comparator = (o1, o2) -> Double.compare(
                            o1.getMedicine().getPrice(),
                            o2.getMedicine().getPrice());
                    break;
                case 4:
                    System.out.println("\nПовернення до головного меню...");
                    continue;
                default:
                    System.out.println("\nВведiть число вiд 1 до 4.");
                    continue;
            }

            // Копія списку інвентарю для сортування
            ArrayList<InventoryItem> result = new ArrayList<InventoryItem>();
            for (InventoryItem item : apteka.getInventory()) {
                result.add(apteka.new InventoryItem(item));
            }

            // Сортування за вибраним компаратором
            Collections.sort(result, comparator);

            // Вивід відсортованого інвентарю
            printInventory(result);

        } while (value != 4);
    }

    /**
     * Виводить список медикаментів.
     *
     * @param inventory список елементів інвентаря
     */
    public static void printInventory(List<InventoryItem> inventory) {
        if (inventory == null) {
            System.out.println("\nОб'єкт не знайдено.");
            return;
        }

        List<InventoryItem> safeCopy = new ArrayList<>(inventory); // копія списку

        if (safeCopy.isEmpty()) {
            System.out.println("\nСписок порожнiй.");
        } else {
            System.out.println("\nСписок лiкарських засобiв з кiлькiстю:");
            int index = 1;
            for (InventoryItem item : safeCopy) {
                System.out.println(index++ + "." + item.getMedicine() + " | Кiлькiсть: " + item.getQuantity());
            }
        }
    }

    /**
     * Зчитує дані з файлу {@link #FILE_NAME} та створює об'єкт {@link Pharmacy}.
     *
     * @return об'єкт {@link Pharmacy}, зчитаний з файлу, або {@code null}, якщо
     *         сталася помилка
     */
    private static Pharmacy readFromFile() {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            if (lines.isEmpty()) {
                System.out.println("Файл порожній.");
                return null;
            }

            Pharmacy pharmacy = MedicineParser.parsePharmacyWithInventory(lines);
            if (pharmacy != null) {
                System.out.println("Данi успiшно завантажено з файлу.");
            }
            return pharmacy;

        } catch (IOException e) {
            System.out.println("Помилка при читанні з файлу: " + e.getMessage());
            return null;
        }
    }

    /**
     * Записує дані про аптеку до файлу {@link #FILE_NAME}.
     *
     * @param pharmacy об'єкт {@link Pharmacy}, що підлягає серіалізації та запису у
     *                 файл
     */
    private static void writeToFile(Pharmacy pharmacy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            String data = MedicineParser.serializePharmacy(pharmacy);
            writer.write(data);
            System.out.println("Данi успiшно збережено до файлу.");
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

}
