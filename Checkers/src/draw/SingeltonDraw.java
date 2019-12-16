package draw;

import components.Board;
import components.Cell;
import components.Draught;

import java.awt.*;

public class SingeltonDraw {
    private static final SingeltonDraw instance = new SingeltonDraw();
    public static int rectX,  rectY;
    private  int size;

    private SingeltonDraw(){ this.size = Board.step; }

    public void drawBoard(Graphics g){
        int x, y;

        for(Cell cell : Board.boardCells){
            x = cell.getX();
            y = cell.getY();
            if((x % 100 == 0 && y % 100 == 0) || (x % 100 != 0 && y % 100 != 0)) {
                g.setColor(Color.BLACK);
                g.fillRect(cell.getX(), cell.getY(), size, size);
            }
        }
    }

    public void drawRectangle(Graphics g, Color color){
        Stroke stroke = new BasicStroke(3);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(stroke);
        g2d.drawRect(rectX, rectY, size, size);
    }

    public void draHintText(Graphics g, String text){
        g.setFont(new Font("serif", Font.PLAIN, 14));
        g.setColor(Color.black);
        g.drawString(text, 402, 205);
    }

    public void drawScore(Graphics g, int whiteScore, int blackScore){
        g.setFont(new Font("Calibri", Font.PLAIN, 25));
        g.setColor(Color.black);
        g.drawString(Integer.toString(blackScore), 434, 320);
        g.drawString(Integer.toString(whiteScore), 434, 120);
    }

    public void drawDraught(Graphics g) {
        for (Draught cp : Board.allDraughts)
            cp.drawDrought(g, cp.isWhite());
    }

    public static SingeltonDraw getInstance(){
        return instance;
    }
}
