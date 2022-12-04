package Core;

import Models.GameBoard;
import Models.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, добавляющий на поле новые фишки
 */
public class BoardUpdater {

    /** Игровое поле */
    private final GameBoard gameBoard;

    /**
     * Конструктор, устагавливающий игровое поле
     * @param gameBoard Игровое поле
     */
    public BoardUpdater(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Находит стоимость замыкаемого диска
     *
     * @param x координата то x
     * @param y координата по y
     * @return ценность замыкаемых фишек
     */
    private int findChip(int x, int y) {
        return (x == 0 || x == 7 || y == 0 || y == 7) ? 2 : 1;
    }

    /** Компьютер легкого уровня сложности размещает фишку */
    public void easyComputerPlaceChip() {
        Color color = Color.White;
        List<Point> pointList = getUpdateMoves(color);
        Point maxValuePoint = pointList.get(0);
        double maxValueChip = 0;
        for (Point point : pointList) {
            double currentDiskValue = findMoveValue(point.x, point.y, color);
            if (currentDiskValue > maxValueChip) {
                maxValueChip = currentDiskValue;
                maxValuePoint = point;
            }
        }
        placeChip(maxValuePoint, Color.White);
    }

    /** Компьютер сложного уровня сложности размещает фишку */
    public void hardComputerPlaceChip(BoardSaver saver) {
        List<Point> moves = getUpdateMoves(Color.White);
        Point maxValueMove = moves.get(0);
        double maxValueChip = -100;
        for (Point item : moves) {
            double currentChipVal = findMoveValue(item.x, item.y, Color.White);
            saver.makeSave(gameBoard);
            placeChip(item, Color.White);
            List<Point> conditionalMoves = getUpdateMoves(Color.Black);
            double currentMaxValueChip = 0;
            for (Point conditionalPoint : conditionalMoves) {
                double currentChipValue
                        = findMoveValue(conditionalPoint.x, conditionalPoint.y, Color.Black);
                if (currentChipValue > currentMaxValueChip) {
                    currentMaxValueChip = currentChipValue;
                }
            }
            currentChipVal -= currentMaxValueChip;
            copyGameBoard(saver.loadSaveAndRemove());
            if (currentChipVal > maxValueChip) {
                maxValueChip = currentChipVal;
                maxValueMove = item;
            }
        }
        placeChip(maxValueMove, Color.White);
    }

    /**
     * Метод, выставляющий фишку на игровое поле
     * @param point Координаты фишки
     * @param color Цвет фишки
     */
    public void placeChip(Point point, Color color) {
        int x = point.x;
        int y = point.y;
        gameBoard.getChip(x, y).setColor(color);
        if (upLineCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x - i, y) == color) {
                    break;
                }
                gameBoard.getChip(x - i, y).setColor(color);
            }
        }
        if (rightUpperDiagonalCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x - i, y + i) == color) {
                    break;
                }
                gameBoard.getChip(x - i, y + i).setColor(color);
            }
        }
        if (rightLineCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x, y + i) == color) {
                    break;
                }
                gameBoard.getChip(x, y + i).setColor(color);
            }
        }
        if (rightLowerDiagonalCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x + i, y + i) == color) {
                    break;
                }
                gameBoard.getChip(x + i, y + i).setColor(color);
            }
        }
        if (lowerLineCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x + i, y) == color) {
                    break;
                }
                gameBoard.getChip(x + i, y).setColor(color);
            }
        }
        if (leftLowerDiagonalCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x + i, y - i) == color) {
                    break;
                }
                gameBoard.getChip(x + i, y - i).setColor(color);
            }
        }
        if (leftLineCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x, y - i) == color) {
                    break;
                }
                gameBoard.getChip(x, y - i).setColor(color);
            }
        }
        if (leftUpperDiagonalCheck(x, y, color)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x - i, y - i) == color) {
                    break;
                }
                gameBoard.getChip(x - i, y - i).setColor(color);
            }
        }
    }

    /**
     * Перекрашивает из цвета n в цвет w, если же Gray - ничего не делаем
     * @param color Цвет
     * @return Перекрашенный цвет
     */
    public static Color repaintColor(Color color) {
        if (color == Color.White) {
            return Color.Black;
        }
        if (color == Color.Black) {
            return Color.White;
        }
        return Color.Gray;
    }

    /**
     * Проверка замыкания сверху
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean upLineCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x - 1, y) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentChipColor = gameBoard.getChipColor(x - i, y);
                if (currentChipColor == colorPlayer) {
                    return true;
                }
                if (currentChipColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка замыкания правой верхней диагонали
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean rightUpperDiagonalCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x - 1, y + 1) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentChipColor = gameBoard.getChipColor(x - i, y + i);
                if (currentChipColor == colorPlayer) {
                    return true;
                }
                if (currentChipColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка возможности замкнуть линию фишек справа
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean rightLineCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x, y + 1) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentDiskColor = gameBoard.getChipColor(x, y + i);
                if (currentDiskColor == colorPlayer) {
                    return true;
                }
                if (currentDiskColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка возможности замкнуть правую нижнюю диагональ фишек
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean rightLowerDiagonalCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x + 1, y + 1) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentDiskColor = gameBoard.getChipColor(x + i, y + i);
                if (currentDiskColor == colorPlayer) {
                    return true;
                }
                if (currentDiskColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка замыкания снизу
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean lowerLineCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x + 1, y) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentDiskColor = gameBoard.getChipColor(x + i, y);
                if (currentDiskColor == colorPlayer) {
                    return true;
                }
                if (currentDiskColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка замыкания левой нижней
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean leftLowerDiagonalCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x + 1, y - 1) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentChipColor = gameBoard.getChipColor(x + i, y - i);
                if (currentChipColor == colorPlayer) {
                    return true;
                }
                if (currentChipColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }


    /**
     * Проверка замыкания слева
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean leftLineCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x, y - 1) == repaintColor(colorPlayer)) {
            for (int i = y - 2; i >= 0; i--) {
                Color currentChipColor = gameBoard.getChipColor(x, i);
                if (currentChipColor == colorPlayer) {
                    return true;
                }
                if (currentChipColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка замыкания левой верхней диагонали
     * @param x Координата по x
     * @param y Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если замкнуть можно, иначе false
     */
    private boolean leftUpperDiagonalCheck(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x - 1, y - 1) == repaintColor(colorPlayer)) {
            for (int i = 2; i < gameBoard.getSize(); i++) {
                Color currentDiskColor = gameBoard.getChipColor(x - i, y - i);
                if (currentDiskColor == colorPlayer) {
                    return true;
                }
                if (currentDiskColor != repaintColor(colorPlayer)) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Проверка возможности поставить фишку по заданным координатам
     * @param x           Координата по x
     * @param y           Координата по y
     * @param colorPlayer Цвет текущего игрока
     * @return true, если можно, ианче false
     */
    private boolean isCanPlaceChip(int x, int y, Color colorPlayer) {
        if (gameBoard.getChipColor(x, y) != Color.Gray) {
            return false;
        }
        return upLineCheck(x, y, colorPlayer)
                || rightUpperDiagonalCheck(x, y, colorPlayer)
                || rightLineCheck(x, y, colorPlayer)
                || rightLowerDiagonalCheck(x, y, colorPlayer)
                || lowerLineCheck(x, y, colorPlayer)
                || leftLowerDiagonalCheck(x, y, colorPlayer)
                || leftLineCheck(x, y, colorPlayer)
                || leftUpperDiagonalCheck(x, y, colorPlayer);
    }

    /**
     * Получить список координат, куда можно поставить фишку
     *
     * @param colorPlayer цвет игрока
     * @return список координат
     */
    public List<Point> getUpdateMoves(Color colorPlayer) {
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if (isCanPlaceChip(i, j, colorPlayer)) {
                    pointList.add(new Point(i, j));
                }
            }
        }
        return pointList;
    }

    /**
     * Находит ценность поле, на которое ставится фишка, для оценки компьютера
     *
     * @param x координата по x
     * @param y координата по y
     * @return ценность поля
     */
    private double findFieldValue(int x, int y) {
        if ((x == 0 || x == 7) && (y == 0 || y == 7)) {
            return 0.8;
        }
        if ((x == 0 || x == 7) || (y == 0 || y == 7)) {
            return 0.4;
        }
        return 0;
    }

    /**
     * Находит ценность хода на клетку
     *
     * @param x           координата x
     * @param y           координата y
     * @param colorPlayer цвет игрока
     * @return ценность хода
     */
    private double findMoveValue(int x, int y, Color colorPlayer) {
        double currentValueChip = findFieldValue(x, y);
        if (upLineCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x - i, y) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x - i, y);
            }
        }
        if (rightUpperDiagonalCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x - i, y + i) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x - i, y + i);
            }
        }
        if (rightLineCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x, y + i) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x, y + i);
            }
        }
        if (rightLowerDiagonalCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x + i, y + i) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x + i, y + i);
            }
        }
        if (lowerLineCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x + i, y) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x + i, y);
            }
        }
        if (leftLowerDiagonalCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x + i, y - i) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x + i, y - i);
            }
        }
        if (leftLineCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x, y - i) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x, y - i);
            }
        }
        if (leftUpperDiagonalCheck(x, y, colorPlayer)) {
            for (int i = 1; i < gameBoard.getSize(); i++) {
                if (gameBoard.getChipColor(x - i, y - i) == colorPlayer) {
                    break;
                }
                currentValueChip += findChip(x - i, y - i);
            }
        }
        return currentValueChip;
    }

    /**
     * Копирует состояние игровой доски
     *
     * @param gameBoard игровая доска, состояние которой надо скопировать
     */
    public void copyGameBoard(GameBoard gameBoard) {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                this.gameBoard.addNewChip(i, j, gameBoard.getChipColor(i, j));
            }
        }
    }
}
