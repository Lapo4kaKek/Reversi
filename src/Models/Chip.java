package Models;

/**
 * Класс игровой фишки
 */
public class Chip {
    /** Цвет фишки */
    private Color color;
    /** Конструктор создания фишки по умолчанию */
    public Chip() {
        color = Color.Gray;
    }
    /** Конструктор создания фишки с параметром */
    public Chip(Color color) {
        this.color = color;
    }
    /** Получить цвет текущей фишки */
    public Color getColor() {
        return color;
    }
    /** Установить цвет фишки */
    public void setColor(Color color) {
        this.color = color;
    }
}
