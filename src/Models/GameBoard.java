package Models;

import java.util.Arrays;

public class GameBoard {

    /** Размер игрового поле */
    private final int size = 8;

    /** Доска */
    private final Chip[][] gameBoard = new Chip[size][size];

    /** Констрокутор */
    public GameBoard() {
        initGameBoard();
    }

    /** Создание первоначального поля с четырьмя фишками */
    private void initGameBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = new Chip(Color.Gray);
            }
        }
        addNewChip(3, 3, Color.White);
        addNewChip(4, 4, Color.White);
        addNewChip(3, 4, Color.Black);
        addNewChip(4, 3, Color.Black);
    }

    /**
     * Возвращает кол-во черных фишек на поле
     * @return кол-во черных фишек
     */
    public int getNumberBlackChip() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += Arrays.stream(gameBoard[i]).filter(chip ->
                    chip.getColor() == Color.Black).count();
        }
        return sum;
    }

    /**
     * Возвращает количество белых фишек
     * @return кол-во белых фишек
     */
    public int getNumberWhiteChip() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += Arrays.stream(gameBoard[i]).filter(chip ->
                    chip.getColor() == Color.White).count();
        }
        return sum;
    }

    /**
     * Возвращает размер доски
     * @return размер доски
     */
    public int getSize() {
        return size;
    }

    /**
     * Метод, возвращающий цвет фишки по координатам,
     * если такой не существует, то возвращает Color.Gray (цвет свободной клетки)
     * @param x Координата X
     * @param y Координата Y
     * @return Цвет фишки с координатами X, Y
     */
    public Color getChipColor(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return Color.Gray;
        }
        return gameBoard[x][y].getColor();
    }

    /**
     * Получить фишку по заданным координатам
     * @param x координата x
     * @param y координата y
     * @return фишка
     */
    public Chip getChip(int x, int y) {
        return gameBoard[x][y];
    }

    /**
     * Метод добавляющий фишку поле
     * @param x координата x
     * @param y координата y
     * @param color цвет фишки
     */
    public void addNewChip(int x, int y, Color color) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return;
        }
        gameBoard[x][y].setColor(color);
    }

    /**
     * Проверяет занятость доски
     * @return true, если мест нет
     */
    public boolean isFull() {
        return getNumberBlackChip() + getNumberWhiteChip() == size * size;
    }

    /**
     * Получить копию игрового поля
     * @return доску
     */
    public GameBoard copyBoard() {
        GameBoard gameBoardCopy = new GameBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoardCopy.addNewChip(i, j, getChipColor(i, j));
            }
        }
        return gameBoardCopy;
    }
}
