package lab17_test.models;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Клас Pharmacy моделює аптеку з назвою, адресою та внутрішнім інвентарем
 * лікарських засобів.
 * Підтримує додавання медикаментів, пошук, вивід та інші базові операції над
 * інвентарем.
 */
public class Pharmacy {
    private String name;
    private String address;
    private ArrayList<InventoryItem> inventory;

    /**
     * Внутрішній клас InventoryItem представляє пару медикамент – кількість.
     */
    public class InventoryItem {
        Medicine medicine;
        int quantity;

        /**
         * Конструктор для створення одиниці інвентаря.
         *
         * @param medicine об'єкт лікарського засобу
         * @param quantity кількість одиниць
         */
        public InventoryItem(Medicine medicine, int quantity) {
            setMedicine(medicine);
            setQuantity(quantity);
        }

        /**
         * Копіювальний конструктор.
         * 
         * @param other інший об'єкт типу InventoryItem.
         */
        public InventoryItem(InventoryItem other) {
            if (other == null) {
                throw new IllegalArgumentException("Cannot copy from a null object.");
            }
            this.medicine = other.getMedicine().clone();
            this.quantity = other.quantity;
        }

        /**
         * Повертає об'єкт {@link Medicine}, що зберігається в інвентарі.
         *
         * @return лікарський засіб
         */
        public Medicine getMedicine() {
            return medicine;
        }

        /**
         * Встановлює лікарський засіб для інвентарного запису.
         *
         * @param medicine об'єкт {@link Medicine}, який додається
         * @throws IllegalArgumentException якщо значення {@code medicine} є
         *                                  {@code null}
         */
        public void setMedicine(Medicine medicine) {
            if (medicine == null) {
                throw new IllegalArgumentException("Лiкарський засiб не може бути null.");
            }
            this.medicine = medicine;
        }

        /**
         * Повертає кількість одиниць даного лікарського засобу в наявності.
         *
         * @return кількість
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Встановлює кількість одиниць лікарського засобу.
         *
         * @param quantity кількість, яка встановлюється
         * @throws IllegalArgumentException якщо {@code quantity} менше нуля
         */
        public void setQuantity(int quantity) {
            if (quantity < 0) {
                throw new IllegalArgumentException("Кiлькiсть не може бути вiд’ємною.");
            }
            this.quantity = quantity;
        }

        /**
         * Перевіряє, чи є поточний об'єкт рівним іншому об'єкту.
         * Два інвентарні записи вважаються рівними, якщо вони містять однаковий
         * {@link Medicine}.
         *
         * @param obj об'єкт для порівняння
         * @return {@code true}, якщо об'єкти рівні; інакше {@code false}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof InventoryItem other))
                return false;
            return medicine.equals(other.medicine);
        }

        /**
         * Обчислює хеш-код для об'єкта {@code InventoryItem}..
         *
         * @return хеш-код об'єкта
         */
        @Override
        public int hashCode() {
            return Objects.hash(medicine);
        }
    }

    /**
     * Створює нову аптеку.
     *
     * @param name    назва аптеки
     * @param address адреса аптеки
     */
    public Pharmacy(String name, String address) {
        setName(name);
        setAddress(address);
        inventory = new ArrayList<>();
    }

    /**
     * Конструктор копіювання для створення нової аптеки на основі іншої.
     *
     * @param other об'єкт Pharmacy для копіювання
     */
    public Pharmacy(Pharmacy other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from a null object.");
        }
        setName(other.getName());
        setAddress(other.getAddress());
        this.inventory = other.getInventory();
    }

    /**
     * Додає медикамент до інвентаря або оновлює кількість, якщо такий вже існує.
     *
     * @param medicine об'єкт медикаменту
     * @param quantity кількість одиниць
     * @return true – якщо новий медикамент додано, false – якщо оновлено кількість
     */
    public boolean addNewMedicine(Medicine medicine, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getMedicine().equals(medicine)) {
                item.setQuantity(item.getQuantity() + quantity);
                return false;
            }
        }
        Medicine medicineCopy = medicine.clone();
        inventory.add(new InventoryItem(medicineCopy, quantity));
        return true;
    }

    /**
     * Оновлює існуючий елемент інвентаря новим екземпляром {@link InventoryItem}.
     *
     * <p>
     * Метод знаходить вказаний елемент {@code existingItem} у внутрішньому списку
     * інвентаря та замінює його новим екземпляром {@code newItem}. Порівняння
     * виконується за методом {@code equals()}.
     *
     * @param existingItem об'єкт інвентаря, який потрібно замінити
     * @param newItem      новий об'єкт інвентаря, яким потрібно замінити наявний
     * @return {@code true}, якщо оновлення пройшло успішно (елемент знайдено і
     *         замінено), або {@code false}, якщо вказаний елемент не знайдено
     */
    public boolean update(InventoryItem existingItem, InventoryItem newItem) {
        int index = inventory.indexOf(existingItem);
        if (index != -1) {
            inventory.set(index, new InventoryItem(newItem));
            return true;
        }
        return false;
    }

    /**
     * Видаляє вказаний елемент інвентаря з внутрішнього списку.
     *
     * <p>
     * Метод шукає об'єкт {@code existingItem} у списку інвентаря за допомогою
     * методу {@code indexOf()}. Якщо елемент знайдено, він видаляється.
     *
     * @param existingItem об'єкт {@link InventoryItem}, який необхідно видалити
     * @return {@code true}, якщо елемент було знайдено і успішно видалено;
     *         {@code false}, якщо елемент не знайдено у списку
     */
    public boolean delete(InventoryItem existingItem) {
        int index = inventory.indexOf(existingItem);
        if (index != -1) {
            inventory.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Пошук медикаментів за назвою.
     *
     * @param name назва для пошуку
     * @return список результатів або null
     */
    public ArrayList<InventoryItem> searchByName(String name) {
        ArrayList<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : getInventory()) {
            if (item.getMedicine().getName().equalsIgnoreCase(name)) {
                result.add(new InventoryItem(item));
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Пошук медикаментів за виробником.
     *
     * @param manufacturer назва виробника
     * @return список результатів або null
     */
    public ArrayList<InventoryItem> searchByManufacturer(String manufacturer) {
        ArrayList<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : getInventory()) {
            if (item.getMedicine().getManufacturer().equalsIgnoreCase(manufacturer)) {
                result.add(new InventoryItem(item));
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Пошук медикаментів за ціною відповідно до вказаної умови.
     *
     * <p>
     * Метод виконує пошук у поточному інвентарі за ціною лікарських засобів.
     * Пошук здійснюється за умовою, що задається параметром {@code value}:
     * <ul>
     * <li>{@code 1} — шукає медикаменти з ціною більшою за {@code price}</li>
     * <li>{@code 2} — шукає медикаменти з ціною меншою за {@code price}</li>
     * <li>{@code 3} — шукає медикаменти з ціною, що дорівнює {@code price}</li>
     * </ul>
     *
     * <p>
     * Повертається глибока копія знайдених елементів інвентаря. Якщо результатів
     * немає, повертається {@code null}.
     *
     * @param value код умови порівняння ціни (1 — більше, 2 — менше, 3 — дорівнює)
     * @param price значення ціни, з якою порівнюється
     * @return список {@code InventoryItem} з медикаментами, що відповідають умові,
     *         або {@code null}, якщо нічого не знайдено
     */
    public ArrayList<InventoryItem> searchByPrice(int value, int price) {
        ArrayList<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : getInventory()) {
            double medPrice = item.getMedicine().getPrice();
            if ((value == 1 && medPrice > price) ||
                    (value == 2 && medPrice < price) ||
                    (value == 3 && medPrice == price)) {
                result.add(new InventoryItem(item));
            }
        }

        return result.isEmpty() ? null : result;
    }

    /**
     * Повертає назву аптеки.
     *
     * @return назва аптеки
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву аптеки.
     *
     * @param name назва аптеки; не може бути {@code null} або порожнім рядком
     * @throws IllegalArgumentException якщо {@code name} є {@code null} або
     *                                  порожнім
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("The name cannot be empty.");
        this.name = name;
    }

    /**
     * Повертає адресу аптеки.
     *
     * @return адреса аптеки
     */
    public String getAddress() {
        return address;
    }

    /**
     * Встановлює адресу аптеки.
     *
     * @param address адреса аптеки; не може бути {@code null} або порожнім рядком
     * @throws IllegalArgumentException якщо {@code address} є {@code null} або
     *                                  порожнім
     */
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty())
            throw new IllegalArgumentException("The address cannot be empty.");
        this.address = address;
    }

    /**
     * Повертає копію списку елементів інвентаря аптеки.
     *
     * <p>
     * Кожен елемент копіюється через конструктор копіювання {@link InventoryItem},
     * щоб запобігти зміні внутрішнього стану аптеки ззовні.
     *
     * @return копія списку {@code InventoryItem}, що містить лікарські засоби
     */
    public ArrayList<InventoryItem> getInventory() {
        ArrayList<InventoryItem> copy = new ArrayList<>();
        for (InventoryItem item : inventory) {
            copy.add(new InventoryItem(item));
        }
        return copy;
    }

    /**
     * Повертає текстове представлення аптеки.
     *
     * @return рядок із назвою та адресою
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Address: %s", getName(), getAddress());
    }

    /**
     * Перевизначений метод рівності для Pharmacy.
     *
     * @param obj об'єкт для порівняння
     * @return true – якщо об'єкти мають однакову назву й адресу
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Pharmacy))
            return false;
        Pharmacy other = (Pharmacy) obj;
        return Objects.equals(name, other.name) &&
                Objects.equals(address, other.address);
    }

    /**
     * Обчислює хеш-код для аптеки на основі її назви та адреси.
     *
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

}
