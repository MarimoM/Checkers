import javax.swing.*;

import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main extends JFrame{

    DraughtsBoard board;

    public static void main(String [] args){
        new Main();
    }

    public Main(){
        this.setSize(495, 440);
        board = new DraughtsBoard();
        this.add(board);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
