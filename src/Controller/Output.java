package Controller;

import Models.GameBoard;
import Models.Color;
import Models.GameSetting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Output {
    /** Игровая доска */
    private final GameBoard gameBoard;
    /** Конструктор */
    public Output(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /** Метод печатаюший игровую доску на экран */
    public void printGameBoard() {
        displayOptionsToPlay(new ArrayList<>());
    }

    /**
     * Метод, печатающий игровую доску и возможные ходы игрока на ней на экран
     * @param pointList возможные варианты хода
     */
    public void printGameBoard(List<Point> pointList) {
        displayOptionsToPlay(pointList);
    }

    /**
     * Проверка на возможность походить на заданную клетку
     * @param pointList лист клеток
     * @param x координата по x
     * @param y координата по y
     * @return true, если сюда похожить можно, иначе false
     */
    private boolean isInPointList(List<Point> pointList, int x, int y) {
        for (int i = 0; i < pointList.size(); i++) {
            if (pointList.get(i).getX() == x && pointList.get(i).getY() == y) {
                if (i + 1 >= 10) {
                    System.out.print(" ");
                    System.out.print(i + 1 + " |");
                } else {
                    System.out.print(" ");
                    System.out.print(i + 1 + " |");
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Метод печатающий игрвую доску на экран со всеми возможеными вариантами хода
     * @param moves список возможных ходов
     */
    private void displayOptionsToPlay(List<Point> moves) {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            System.out.println("‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒");
            System.out.print("|");
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if (isInPointList(moves, i, j)) {
                    continue;
                }
                if (gameBoard.getChipColor(i, j) == Color.Black) {
                    System.out.print(" ○ |");
                }
                if (gameBoard.getChipColor(i, j) == Color.White) {
                    System.out.print(" ● |");
                }
                if (gameBoard.getChipColor(i, j) == Color.Gray) {
                    System.out.print(" ‒ |");
                }
            }
            System.out.println();
        }
        System.out.println("‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒‒");
    }

    /** Печатает в консоль игровое поле и сообщение о том что компьютер сделал ход */
    public void printComputerTurn() {
        printGameBoard();
        System.out.println("==========================");
        System.out.println("Ход компьютера");
        System.out.println("==========================");
    }

    /**
     * Метод, подводящий итоги при завершение
     * @param gameSetting настройки игры
     * @param maxCountChip текущий максимум фишек
     * @param blackPlayerName имя игрока за черных
     * @param whitePlayerName имя игрока за белых
     * @return если по очкам - рекорд, возвращаем его, иначе старый максимум
     */
    public int findWinner(GameSetting gameSetting,
                          int maxCountChip, String blackPlayerName, String whitePlayerName) {
        System.out.println("Игра окончена!");
        if (gameBoard.getNumberBlackChip() >= gameBoard.getNumberWhiteChip()) {
            if (gameBoard.getNumberBlackChip() == gameBoard.getNumberWhiteChip()) {
                System.out.println("Ничья");
            } else {
                System.out.println("Победил " + blackPlayerName + "");
            }
            System.out.println(blackPlayerName + ": " + gameBoard.getNumberBlackChip());
            System.out.println(whitePlayerName + ": " + gameBoard.getNumberWhiteChip());
            if (gameBoard.getNumberBlackChip() > maxCountChip) {
                System.out.println("Установлен рекорд среди игроков: " + gameBoard.getNumberBlackChip()
                        + "\uD83E\uDD70");
                maxCountChip = gameBoard.getNumberBlackChip();
            } else {
                System.out.println("Текущий рекорд: " + maxCountChip);
            }
        }
        if (gameBoard.getNumberBlackChip() < gameBoard.getNumberWhiteChip()) {
            System.out.println("Победил " + whitePlayerName + "\uD83E\uDD81!");
            System.out.println(blackPlayerName + ": " + gameBoard.getNumberBlackChip());
            System.out.println(whitePlayerName + ": " + gameBoard.getNumberWhiteChip());
            if (gameSetting == GameSetting.Pvp && gameBoard.getNumberWhiteChip() > maxCountChip) {
                System.out.println("Рекорд среди игроков: " + gameBoard.getNumberWhiteChip());
                maxCountChip = gameBoard.getNumberWhiteChip();
            } else if (gameSetting != GameSetting.Pvp && gameBoard.getNumberBlackChip() > maxCountChip) {
                System.out.println("Рекорд среди игроков: " + gameBoard.getNumberBlackChip());
                maxCountChip = gameBoard.getNumberBlackChip();
            } else {
                System.out.println("Текущий рекорд: " + maxCountChip);
            }
        }
        return maxCountChip;
    }
}
