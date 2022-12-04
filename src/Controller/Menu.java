package Controller;

import Core.BoardSaver;
import Core.BoardUpdater;
import Models.GameBoard;
import Models.GameSetting;
import Models.Color;
import java.awt.*;
import java.lang.constant.Constable;
import java.util.List;

public class Menu {

    /**
     * Работающая игра
     */
    public static void runGame() {
        System.out.println("ЙОУ Привет! Это игра Реверси, давай поиграем 😈😈😈");
        int maxCountChips = 0;
        Input consoleInput = new Input();
        do {
            GameBoard gameBoard = new GameBoard();
            Output consoleOutput = new Output(gameBoard);
            BoardUpdater gameBoardUpdater = new BoardUpdater(gameBoard);
            BoardSaver gameBoardSaver = new BoardSaver(gameBoard);
            GameSetting gameSetting = consoleInput.chooseGameSetting();
            String blackPlayerName = consoleInput.inputUserName(Color.Black);
            String whitePlayerName;
            Color colorPlayer = Color.Black;
            if (gameSetting == GameSetting.Pvp) {
                whitePlayerName = consoleInput.inputUserName(Color.White);
            } else {
                whitePlayerName = "Компьютер";
            }
            while (gameBoard.getNumberWhiteChip() != 0 && !gameBoard.isFull() && gameBoard.getNumberBlackChip() != 0) {
                if (isPat(gameBoardUpdater)) break;
                List<Point> pointList = gameBoardUpdater.getUpdateMoves(colorPlayer);
                if (pointList.size() == 0) {
                    System.out.println("Нет ходов, переход хода к следющему игроку");
                    colorPlayer = BoardUpdater.repaintColor(colorPlayer);
                    continue;
                }
                if (gameSetting == GameSetting.Easy && colorPlayer == Color.White) {
                    consoleOutput.printComputerTurn();
                    gameBoardUpdater.easyComputerPlaceChip();
                } else if (gameSetting == GameSetting.Hard && colorPlayer == Color.White) {
                    consoleOutput.printComputerTurn();
                    gameBoardUpdater.hardComputerPlaceChip(gameBoardSaver);
                } else {
                    consoleOutput.printGameBoard(pointList);
                    int updateNumber = consoleInput.chooseCommand(colorPlayer, pointList);
                    if (updateNumber == -1) {
                        if (gameSetting == GameSetting.Pvp) {
                            if (gameBoardSaver.isOnlyInitItem()) {
                                colorPlayer = BoardUpdater.repaintColor(colorPlayer);
                            }
                            gameBoardSaver.removeLastSave();
                            gameBoardUpdater.copyGameBoard(gameBoardSaver.loadSaveAndRemove());
                        }
                        if (gameSetting == GameSetting.Hard || gameSetting == GameSetting.Easy) {
                            gameBoardSaver.removeLastSave();
                            gameBoardSaver.removeLastSave();
                            gameBoardUpdater.copyGameBoard(gameBoardSaver.loadSaveAndRemove());
                            colorPlayer = BoardUpdater.repaintColor(colorPlayer);
                        }
                    } else gameBoardUpdater.placeChip(pointList.get(updateNumber - 1), colorPlayer);
                }
                colorPlayer = BoardUpdater.repaintColor(colorPlayer);
                gameBoardSaver.makeSave(gameBoard);
            }
            consoleOutput.printGameBoard();
            maxCountChips =
                    consoleOutput.findWinner(gameSetting, maxCountChips, blackPlayerName, whitePlayerName);
        } while (!consoleInput.isGameFinishInput());
    }

    public static boolean isPat(BoardUpdater gameBoardUpdater) {
        return gameBoardUpdater.getUpdateMoves(Color.Black).isEmpty()
                && gameBoardUpdater.getUpdateMoves(Color.White).isEmpty();
    }
}
