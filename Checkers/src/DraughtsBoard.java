import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DraughtsBoard extends JPanel implements  MouseListener {
    ArrayList<Chip> whiteChips = new ArrayList<>();
    ArrayList<Chip> blackChips = new ArrayList<>();
    ArrayList<Cell> boardCells = new ArrayList<>();

    Chip currentChip = null;
    private int rectX = -50;
    private int rectY = -50;
    private boolean whiteTurn = true;
    private int step = 50;
    private int boardSize = step * 7;

    public DraughtsBoard() {
        setStartPosition();
        addMouseListener(this);
    }

    private void setStartPosition() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((j % 2 == 0 && i % 2 == 0) || (j % 2 != 0 && i % 2 != 0)) {
                    if (i <= 2) {
                        Chip whiteChip = new Chip(x, y);
                        whiteChips.add(whiteChip);
                    }
                    else if(i > 4){
                        Chip blackChip = new Chip(x, y);
                        blackChips.add(blackChip);
                    }
                }
                Cell cell = new Cell(x, y);
                boardCells.add(cell);
                x += step;
            }
            x = 0;
            y += step;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        drawBoard(g);
        drawChips(g);
        drawSelectionRect(g);
        drawHintText(g);

        repaint();
        g.dispose();
    }

    private void drawSelectionRect(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(rectX, rectY, step, step);
    }

    private void drawHintText(Graphics g) {
        g.setColor(Color.black);
        if(!whiteTurn)
            g.drawString("Blues turn", 400, 10);
        else
            g.drawString("Yellows turn", 400, 10);
    }

    private void drawChips(Graphics g) {
        for (Chip cp : whiteChips) {
            if(!cp.isQueen()) {
                g.setColor(Color.orange);
                g.fillOval(cp.getX(), cp.getY(), step, step);
            }
            else{
                g.setColor(Color.red);
                g.fillOval(cp.getX(), cp.getY(), step, step);
            }
        }

        for (Chip cp : blackChips) {
            if(!cp.isQueen()) {
                g.setColor(Color.CYAN);
                g.fillOval(cp.getX(), cp.getY(), step, step);
            }
            else{
                g.setColor(Color.blue);
                g.fillOval(cp.getX(), cp.getY(), step, step);
            }
        }
    }

    private void drawBoard(Graphics g) {
        int x = 0;
        int y = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((j % 2 == 0 && i % 2 == 0) || (j % 2 != 0 && i % 2 != 0)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, step, step);
                }
                x += step;
            }
            x = 0;
            y += step;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Cell clickedCell = getClickedCell(e);

        if (clickedCell != null) {
            setBoundingBoxPosition(clickedCell);
            int x = clickedCell.getX();
            int y = clickedCell.getY();

            if (currentChip != null) {
                setEnemies();
                if (checkIfThereAreEnemies()) {
                    System.out.println("Enemy was found!");
                    defeatEnemy(x, y);
                } else {
                    if(currentChip.isQueen()){
                        System.out.println("Queen!");
                        if(cellIsEmpty(x, y) && checkQueensDirection(x, y)) {
                            System.out.println("Queen moves!");
                            moveChip(x, y);
                            setEnemies();
                            whiteTurn = whiteTurn ? false : true;
                        }
                    }
                    else if (cellIsEmpty(x, y) && checkDistance(x, y) && checkDirection(y)) {
                        moveChip(x, y);
                        checkQueen();
                        setEnemies();
                        whiteTurn = whiteTurn ? false : true;
                    }
                    currentChip = null;
                }
            }
            else
                setCurrentChip(x, y);

            checkIfGameEnded();
            repaint();
        }
    }

    private boolean checkQueensDirection(int x, int y) {
        if(currentChip.getX() != x && currentChip.getY() != y
                && isOnOneDiagonal(currentChip.getX(), currentChip.getY(), x, y))
            return true;
        return false;
    }

    private void checkQueen() {
        if(whiteTurn){
            if(currentChip.getY() == boardSize)
                setQueen();
        }
        else
            if(currentChip.getY() == 0)
                setQueen();
    }

    private void setQueen() {
        for(Chip chip : whiteChips) {
            if (chip.getY() == currentChip.getY() && chip.getX() == currentChip.getX()) {
                chip.setQueen(true);
                break;
            }
        }
        for(Chip chip : blackChips) {
            if (chip.getY() == currentChip.getY() && chip.getX() == currentChip.getX()) {
                chip.setQueen(true);
                break;
            }
        }

    }

    private boolean checkIfThereAreEnemies() {
        if(whiteTurn){
            for(Chip white : whiteChips)
                if(white.getEnemies().size() > 0) {
                    return true;
                }
        }else{
            for(Chip black : blackChips)
                if(black.getEnemies().size() > 0)
                    return true;
        }
        return false;
    }

    private void defeatEnemy(int x, int y) {
        if (isEnemyDefeated(x, y)) {
            moveChip(x, y);
            checkQueen();
            setEnemies();
            if (!checkIfEnemiesLeft()) {
                whiteTurn = whiteTurn ? false : true;
                currentChip = null;
            }
        }
        else
            currentChip = null;
    }

    private void setCurrentChip(int x, int y) {
        if (whiteTurn) {
            for (Chip chip : whiteChips) {
                if (x == chip.getX() && y == chip.getY()) {
                    currentChip = chip;
                    break;
                }
            }
        }
        else{
            for (Chip chip : blackChips) {
                if (x == chip.getX() && y == chip.getY()) {
                    currentChip = chip;
                    break;
                }
            }
        }
    }

    private void setBoundingBoxPosition(Cell clickedCell) {
        rectX = clickedCell.getX();
        rectY = clickedCell.getY();
    }

    private boolean checkIfEnemiesLeft() {
        if(currentChip.getEnemies().size() > 0)
            return true;
        return false;
    }

    private boolean isEnemyDefeated(int x, int y) {
        ArrayList<Chip> enemiesList = currentChip.getEnemies();
        if(enemiesList.size() > 0){
            for(Chip enemy : enemiesList)
            {
                if(checkClickCorrectness(x, y, enemy.getX(), enemy.getY()) && y != currentChip.getY() && x != currentChip.getX()){
                    removeEnemy(enemy);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkClickCorrectness(int x, int y, int enemyX, int enemyY) {
        if(!currentChip.isQueen())
            return (x == enemyX - step && y == enemyY + step || x == enemyX + step && y == enemyY - step
                    || x == enemyX + step && y == enemyY + step || x == enemyX - step && y == enemyY - step);
        else{
            System.out.println("Queen check correctness!");
            if(currentChip.getY() > enemyY){
                if(y < enemyY && isOnOneDiagonal(x, y, enemyX, enemyY))
                    return true;
            }
            else if(currentChip.getY() < enemyY){
                if(y > enemyY && isOnOneDiagonal(x, y, enemyX, enemyY))
                    return true;
            }
        }
        return false;
    }

    private boolean isOnOneDiagonal(int x, int y, int enemyX, int enemyY) {
        return Math.abs(x - enemyX) == Math.abs(y - enemyY);
    }

    private void removeEnemy(Chip defeatedChip) {
        if(whiteTurn) {
            for (Chip chip : blackChips) {
                if(chip.getX() == defeatedChip.getX() && chip.getY() == defeatedChip.getY()){
                    blackChips.remove(chip);
                    break;
                }
            }
        }
        else {
            for (Chip chip : whiteChips) {
                if (chip.getX() == defeatedChip.getX() && chip.getY() == defeatedChip.getY()) {
                    whiteChips.remove(chip);
                    break;
                }
            }
        }
    }

    private Cell getClickedCell(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Cell ch : boardCells) {
            if ((x >= ch.getX() && x <= ch.getX() + step) && (y >= ch.getY() && y <= ch.getY() + step) && isOnBoard(x, y))
                return ch;
        }
        return null;
    }

    private boolean isOnBoard(int x, int y) {
        return x < boardSize + step && y < boardSize + step;
    }

    private void setEnemies() {
        removeEnemies();
        for (Chip whiteChip : whiteChips) {
            for (Chip blackChip : blackChips) {
                if (!whiteTurn) {
                    if (!blackChip.isQueen())
                        checkSides(whiteChip, blackChip);
                    else
                        setQueensEnemies(whiteChip, blackChip);
                }
                else {
                    if (!whiteChip.isQueen())
                        checkSides(blackChip, whiteChip);
                    else
                        setQueensEnemies(blackChip, whiteChip);
                }
            }
        }
    }

    private void setQueensEnemies(Chip enemy, Chip queen) {
        if(isOnOneDiagonal(enemy.getX(), enemy.getY(), queen.getX(), queen.getY())){
            if(queen.getY() > enemy.getY() || queen.getY() < enemy.getY()) {
                if (queen.getX() > enemy.getX() && cellIsEmpty(enemy.getX() + step, enemy.getX() + step)
                        && cellIsEmpty(enemy.getX() - step, enemy.getX() - step)) {
                    queen.addEnemy(enemy);
                }
                else if(queen.getX() < enemy.getX() && cellIsEmpty(enemy.getX() + step, enemy.getX() + step)
                        && cellIsEmpty(enemy.getX() + step, enemy.getX() - step)){
                    queen.addEnemy(enemy);
                }
            }
            System.out.println("Queens enemy was set!");
        }
    }

    private void removeEnemies() {
        for(Chip white : whiteChips)
            white.removeAllEnemies();
        for(Chip black : blackChips)
            black.removeAllEnemies();
    }

    private void checkSides(Chip enemy, Chip chip) {
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();

        if (enemyX == chip.getX() + step) {
            if (enemyY == chip.getY() + step && cellIsEmpty(enemyX + step, enemyY + step))
                chip.addEnemy(enemy);
            if (enemyY == chip.getY() - step && cellIsEmpty(enemyX + step, enemyY - step))
                chip.addEnemy(enemy);
        }
        else if(enemyX == chip.getX() - step){
            if (enemyY == chip.getY() + step && cellIsEmpty(enemyX - step, enemyY + step))
                chip.addEnemy(enemy);
            if (enemyY == chip.getY() - step && cellIsEmpty(enemyX - step, enemyY - step))
                chip.addEnemy(enemy);
        }
    }

    private boolean checkDirection(int y) {
        if(!whiteTurn) {
            if (y > currentChip.getY())
                return false;
        }
        else {
            if (y < currentChip.getY())
                return false;
        }
        return true;
    }

    private boolean checkDistance(int endX, int endY) {
        int startY = currentChip.getY();
        int startX = currentChip.getX();

        if((Math.abs(endY - startY) == step && endX != startX && (Math.abs(endX - startX) == step)))
            return true;
        else
            return false;
    }

    private void moveChip(int x, int y) {
        for(Chip cp : whiteChips)
            if(currentChip.getX() == cp.getX() && currentChip.getY() == cp.getY()){
                cp.setY(y);
                cp.setX(x);
                break;
            }

        for(Chip cp : blackChips)
            if(currentChip.getX() == cp.getX() && currentChip.getY() == cp.getY()){
                cp.setY(y);
                cp.setX(x);
                break;
            }
    }

    private boolean cellIsEmpty(int x, int y) {
        if(x > boardSize || x < 0 || y < 0 || y > boardSize)
            return false;

        for (Chip cp : whiteChips) {
            if (cp.getY() == y && cp.getX() == x)
                return false;
        }

        for (Chip cp : blackChips) {
            if (cp.getY() == y && cp.getX() == x)
                return false;
        }
        return true;
    }

    private void checkIfGameEnded(){
        if(whiteChips.size() == 0 || blackChips.size() == 0) {
            currentChip = null;
            whiteChips.clear();
            blackChips.clear();
            setStartPosition();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
