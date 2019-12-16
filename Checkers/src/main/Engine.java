package main;

import behaviour.*;
import components.*;
import command.*;
import draw.SingeltonDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Engine extends JPanel implements MouseListener
{
    public static boolean whiteTurn = true;
    private int whiteScore, blackScore = 0;
    private Draught clickedDraught;
    private SingeltonDraw draw;

    Engine() {
        Board.createBoard();
        Board.setStartPosition();
        addMouseListener(this);
        draw = SingeltonDraw.getInstance();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        try{
            new MouseClickCommand(new Click()).execute(e);

            if(Click.clickedDraught == null)
                performAction(Click.clickedCell);
            else
                clickedDraught = Click.clickedDraught;

            checkIfGameEnded();
            repaint();
        }catch (Exception ex){
            System.out.println("Wrong click!");
        }
    }

    private void performAction(Cell clicked) {
        int x = clicked.getX();
        int y = clicked.getY();

        resetEnemies();
        if (checkIfThereAreEnemies())
            defeatEnemy(x, y);
        else if (Board.isCellEmpty(x, y) && clickedDraught.checkDistance(x, y) && clickedDraught.checkDirection(x, y)) {
            moveChip(x, y);
            checkQueen();
            clickedDraught = null;
            whiteTurn = !whiteTurn;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw.drawBoard(g);
        draw.drawDraught(g);
        draw.drawRectangle(g, new Color(153, 0, 0));
        String hintText = whiteTurn ? "Yellows turn" : "  Blues turn";
        draw.draHintText(g, hintText);
        draw.drawScore(g, whiteScore, blackScore);
        g.dispose();
    }

    private void resetEnemies() {
        removeEnemies();
        setEnemies();
    }

    private static void removeEnemies() {
        for(Draught draught : Board.allDraughts)
            draught.removeEnemies();
    }

    private void checkQueen() {
        if(clickedDraught.isWhite() && clickedDraught.getY() == Board.boardSize)
            clickedDraught.setBehaviour(new QueenBehavior());
        else if(!clickedDraught.isWhite() && clickedDraught.getY() == 0)
            clickedDraught.setBehaviour(new QueenBehavior());
    }

    private static void setEnemies() {
        for (Draught draught : Board.allDraughts)
            draught.setEnemies();
    }

    private void defeatEnemy(int x, int y) {
        if (isEnemyDefeated(x, y)) {
            moveChip(x, y);
            checkQueen();
            removeEnemies();
            setEnemies();
            if (!areEnemiesLeft()) {
                whiteTurn = !whiteTurn;
                clickedDraught = null;
            }
        }
    }

    private static boolean checkIfThereAreEnemies() {
        for (Draught draught : Board.allDraughts) {
            if (whiteTurn && draught.isWhite() && draught.getEnemies().size() > 0)
                return true;
            else if (!whiteTurn && !draught.isWhite() && draught.getEnemies().size() > 0)
                return true;
        }
        return false;
    }

    private boolean areEnemiesLeft() {
        return clickedDraught.getEnemies().size() > 0;
    }

    private boolean isEnemyDefeated(int x, int y) {
        if(clickedDraught.getEnemies().size() > 0)
            for(Draught enemy : clickedDraught.getEnemies())
                if(clickedDraught.checkDestination(x, y, enemy.getX(), enemy.getY())){
                    removeEnemy(enemy);
                    return true;
                }
        return false;
    }

    private void removeEnemy(Draught enemy) {
        for(Draught draught : Board.allDraughts){
            if(draught.getX() == enemy.getX() && draught.getY() == enemy.getY()) {
                Board.allDraughts.remove(draught);
                break;
            }
        }
        increaseScore();
    }

    private void increaseScore() {
        if(whiteTurn) whiteScore ++;
        else blackScore ++;
    }

    private void moveChip(int x, int y) {
        for(Draught draught : Board.allDraughts)
            if(clickedDraught.getX() == draught.getX() && clickedDraught.getY() == draught.getY()){
                draught.setY(y);
                draught.setX(x);
                break;
            }
    }

    private void checkIfGameEnded(){
        if(whiteScore == Board.maxScore || blackScore == Board.maxScore)
            showEndDialog();
    }

    private void showEndDialog() {
        int dialogOptions = JOptionPane.YES_NO_OPTION;
        String messageText = "Yellow won!";
        if(blackScore == Board.maxScore)
            messageText = "Blue won!";
        int dialogResult = JOptionPane.showConfirmDialog (null, messageText + "\nWould you like to play again?","Warning", dialogOptions);
        if(dialogResult == JOptionPane.YES_OPTION)
            resetGame();
        else
            System.exit(0);
    }

    private void resetGame(){
        clickedDraught = null;
        whiteScore = 0;
        blackScore = 0;
        Board.clear();
        Board.setStartPosition();
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
