package command;

import components.Board;
import components.Cell;
import components.Draught;
import draw.SingeltonDraw;

import java.awt.event.MouseEvent;

public class Click{
    public static Draught clickedDraught;
    public static Cell clickedCell;

    void mouseClicked(MouseEvent e) {
        clickedCell = Board.getClickedCell(e);
        setSelectBox(clickedCell);
        clickedDraught = getClickedDraught(clickedCell.getX(), clickedCell.getY());
    }

    private Draught getClickedDraught(int x, int y) {
        for(Draught draught : Board.allDraughts)
            if(draught.getY() == y && draught.getX() == x)
                return draught;
        return null;
    }

    private void setSelectBox(Cell clickedCell) {
        SingeltonDraw.rectX = clickedCell.getX();
        SingeltonDraw.rectY = clickedCell.getY();
    }
}
