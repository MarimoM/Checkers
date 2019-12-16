package components;

import behaviour.SimpleBehavior;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Board {
    public static int step = 50;
    public static int maxScore = 12;
    public static int boardSize = step * 7;
    public static ArrayList<Cell> boardCells = new ArrayList<>();
    public static ArrayList<Draught> allDraughts = new ArrayList<>();

    public static boolean isCellEmpty(int x, int y) {
        if(!doesCellExist(x, y))
            return false;

        for(Draught draught : allDraughts)
            if(draught.getY() == y && draught.getX() == x)
                return false;
        return true;
    }

    private static boolean doesCellExist(int x, int y) {
        for(Cell cell : boardCells)
            if(cell.getX() == x && cell.getY() == y)
                return true;
        return false;
    }

    private static boolean isClickOnBoard(int x, int y) {
        return x <= boardSize + step && y <= boardSize + step && x >= 0 && y >= 0;
    }

    public static Cell getClickedCell(MouseEvent e) {
        for (Cell ch : boardCells)
            if ((e.getX() >= ch.getX()
                    && e.getX() <= ch.getX() + step)
                    && (e.getY() >= ch.getY()
                    && e.getY() <= ch.getY() + step)
                    && isClickOnBoard(e.getX(), e.getY()))
                return ch;
        return null;
    }

    public static void createBoard() {
        int x = 0;
        int y = 0;
        int rows = 8;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                Cell cell = new Cell(x, y);
                boardCells.add(cell);
                x += step;
            }
            x = 0;
            y += step;
        }
    }

    public static void setStartPosition(){
        int x, y;
        for(Cell cell : boardCells){
            x = cell.getX();
            y = cell.getY();
            if ((x % 100 == 0 && y % 100 == 0) || (x % 100 != 0 && y % 100 != 0)) {
                if (y < step * 3)
                    allDraughts.add(new Draught(cell.getX(), cell.getY(), true, new SimpleBehavior()));
                else if (y > step * 4)
                    allDraughts.add(new Draught(cell.getX(), cell.getY(), false, new SimpleBehavior()));
            }
        }
    }

    public static void clear(){
        allDraughts.clear();
    }
}
