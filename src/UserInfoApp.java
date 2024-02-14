import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserInfoApp {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Ввод данных от пользователя
            System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
            String userInput = scanner.nextLine();

            // Разделение введенной строки на компоненты
            String[] userData = userInput.split(" ");

            // Проверка количества введенных данных
            if (userData.length != 6) {
                throw new IllegalArgumentException("Введено неверное количество данных");
            }

            // Извлечение данных
            String lastName = userData[0];
            String firstName = userData[1];
            String middleName = userData[2];
            String birthDateString = userData[3];

            // Проверка формата даты рождения и ее корректности
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateFormat.setLenient(false); // Запрещаем автоматическую коррекцию дат

            Date birthDate = dateFormat.parse(birthDateString);
            if (birthDate.after(new Date())) {
                throw new IllegalArgumentException("Некорректная дата рождения. Дата в будущем недопустима.");
            }

            // Проверка формата номера телефона
            String phoneNumberStr = userData[4];
            if (!phoneNumberStr.matches("\\d+")) {
                throw new IllegalArgumentException("Неверный формат номера телефона. Используйте только цифры.");
            }
            long phoneNumber = Long.parseLong(phoneNumberStr);

            // Проверка формата пола
            char gender = userData[5].charAt(0);
            if (gender != 'm' && gender != 'f') {
                throw new IllegalArgumentException("Неверный формат пола. Используйте 'm' или 'f'.");
            }

            // Валидация формата данных и создание объекта UserInfo
            UserInfo userInfo = new UserInfo(lastName, firstName, middleName, birthDate, phoneNumber, gender);

            // Добавление данных в файл с именем фамилии
            try (FileWriter writer = new FileWriter(lastName + ".txt", true)) {
                // Запись данных в файл
                writer.write(userInfo.toString() + System.lineSeparator());
                System.out.println("Данные успешно добавлены в файл.");
            } catch (IOException e) {
                // Обработка ошибок при записи в файл
                System.err.println("Ошибка при записи в файл:");
                e.printStackTrace();
            }

        } catch (IllegalArgumentException | ParseException e) {
            // Обработка ошибок ввода данных или валидации
            System.err.println("Ошибка:");
            System.err.println(e.getMessage());
        }
    }
}

class UserInfo {
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;
    private long phoneNumber;
    private char gender;

    public UserInfo(String lastName, String firstName, String middleName, Date birthDate, long phoneNumber, char gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return String.format("%s %s %s %s %d %c",
                lastName, firstName, middleName, dateFormat.format(birthDate), phoneNumber, gender);
    }
}
