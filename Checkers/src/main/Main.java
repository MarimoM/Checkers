package main;

import javax.swing.*;

public class Main extends JFrame{

    public static void main(String [] args){ new Main(); }

    private Main(){
        this.setSize(495, 440);
        Engine engine = new Engine();
        this.add(engine);
        this.setTitle("Draughts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
