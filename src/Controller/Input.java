package Controller;
import java.awt.*;
import java.util.List;
import java.util.Scanner;
import Models.Color;
import Models.GameSetting;

public class Input {

    /** Сканер для ввода данных */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Выбор настроек
     * @return игровые настройки
     */
    public GameSetting chooseGameSetting() {
        System.out.println("""
                Выберите режим игры, для этого введите команду:
                easy - режим против компьютера в легком режиме 🌈
                hard - режим игры против компьютера в сложном режиме 🔥
                pvp - режим против игрока 💀
                """);
        String commandLine;
        GameSetting gameSetting = null;
        do {
            commandLine = scanner.nextLine().toLowerCase().trim();
            switch (commandLine) {
                case ("easy") :
                    gameSetting = GameSetting.Easy;
                    break;
                case ("hard") :
                    gameSetting = GameSetting.Hard;
                    break;
                case ("pvp") :
                    gameSetting = GameSetting.Pvp;
                    break;
                default :
                    System.out.print("Некорректный режим, введи правильно\n");
                    break;
            }
        } while (!commandLine.equals("pvp")
                && !commandLine.equals("easy")
                && !commandLine.equals("hard"));
        return gameSetting;
    }

    /**
     * Ввод имени игрока
     * @param colorPlayer цвет игрока
     * @return имя игрока
     */
    public String inputUserName(Color colorPlayer) {
        if (colorPlayer == Color.Black) {
            System.out.print("Введите имя первого игрока:\n");
        }
        if (colorPlayer == Color.White) {
            System.out.print("Введите имя второго игрока:\n");
        }
        return scanner.nextLine();
    }

    /**
     * Считывает команду, введенную пользлвателем
     * @param colorPlayer цвет игрока
     * @param pointList список координат, куда можно сделать ход
     * @return -1, если игрок решил отменить свой ход, иначе 1..pointList.size
     */
    public int chooseCommand(Color colorPlayer, List<Point> pointList) {
        if (colorPlayer == Color.Black) {
            System.out.println("Сейчас ход черного");
        }
        if (colorPlayer == Color.White) {
            System.out.println("Сейчас ход белого");
        }
        System.out.println("Введите номер хода из диапазона 1 - " + pointList.size());
        System.out.println("Или наберите команду /z для отмены последнего хода");
        String line;
        int inputOption;
        do {
            try {
                line = scanner.nextLine();
                if (line.toLowerCase().trim().equals("/z")) {
                    return -1;
                }
                inputOption = Integer.parseInt(line);
                if (inputOption > 0 && inputOption <= pointList.size()) {
                    break;
                } else {
                    System.out.println("Число должно быть из диапазоне 1 - " + pointList.size());
                }
            } catch (Exception e) {
                System.out.println("Введите корректную команду");
            }

        } while (true);
        return inputOption;
    }

    /**
     * Хочет ли пользователь завершить игру
     * @return true, если игра завершается, иначе false
     */
    public boolean isGameFinishInput() {
        System.out.println();
        System.out.println("Хотите сыграть еще раз? Наберите: \n/y - для еще одной игры \n/n - для выхода");
        String commandline = scanner.nextLine().toLowerCase().trim();
        while (!commandline.equals("/n") && !commandline.equals("/y")) {
            System.out.println("Введите корректную команду");
            commandline = scanner.nextLine().toLowerCase().trim();
        }
        return commandline.equals("/n");
    }
}
