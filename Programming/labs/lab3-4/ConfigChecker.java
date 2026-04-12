import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import exceptions.InvalidConfigException;

public class ConfigChecker {

    public static class Config {
        public String cityName;
        public String[] shortyNames;
        public Config(String cityName, String[] shortyNames) {
            this.cityName = cityName;
            this.shortyNames = shortyNames;
        }
    }

    public static Config loadConfig() throws IOException {
        Properties props = new Properties();
        File file = new File("config.properties");

        if (!file.exists()) {
            System.out.println("Файл config.properties не найден.");
            System.out.print("Хотите создать новый файл? (y/n): ");

            Scanner scanner = new Scanner(System.in, "UTF-8");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (!answer.equals("y")) {
                System.out.println("Выход.");
                return null;
            }

            class InputValidator {
                private final Scanner sc;
                InputValidator(Scanner sc) { 
                    this.sc = sc; 
                }

                String readNonEmpty(String prompt) {
                    String s = "";
                    while (s.isBlank()) {
                        System.out.print(prompt);
                        s = sc.nextLine();
                        if (s.isBlank()) {
                            System.out.println("Значение не может быть пустым");
                        }
                        
                    }
                    return s;
                }

                String readSeparatedWords(String prompt) {
                    String s = "";
                    while (s.isBlank() || s.split("\\s*,\\s*").length < 2) {
                        System.out.print(prompt);
                        s = sc.nextLine();
                        if (s.isBlank()) {
                            System.out.println("Значение не может быть пустым");
                        } else if (s.split("\\s*,\\s*").length < 2) {
                            System.out.println("Введите минимум 2 имен через запятую");
                        }
                    }
                    return s;
                }
            }

            InputValidator validator = new InputValidator(scanner);
            String cityName = validator.readNonEmpty("Введите city_name: ");
            String shortyNames = validator.readSeparatedWords("Введите shorty_names (через запятую, минимум 2 имена): ");

            props.setProperty("city_name", cityName);
            props.setProperty("shorty_names", shortyNames);

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                props.store(writer, "-");
            }

            System.out.println("Файл успешно создан");
            System.out.println();
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            props.load(reader);
        }

        String cityName = props.getProperty("city_name");
        String shortyNamesRaw = props.getProperty("shorty_names");
        if (cityName == null || cityName.isBlank()) {
            throw new InvalidConfigException("city_name");
        }
        if (shortyNamesRaw == null || shortyNamesRaw.isBlank() || shortyNamesRaw.split("\\s*,\\s*").length < 2) {
            throw new InvalidConfigException("shorty_names");
        }

        String[] shortyNames = shortyNamesRaw.split("\\s*,\\s*");

        return new Config(cityName, shortyNames);
    }
    public static String getCityName() throws IOException {
        Config cfg = loadConfig();
        return cfg.cityName;
    }

    public static String[] getShortyNames() throws IOException {
        Config cfg = loadConfig();
        return cfg.shortyNames;
    }

    public static void main(String[] args) throws IOException {
        Config cfg = loadConfig();
    }
}
