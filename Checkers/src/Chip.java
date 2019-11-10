import java.util.ArrayList;

public class Chip {
    private int x, y;
    private ArrayList<Chip> enemies = new ArrayList();
    private boolean queen = false;

    public boolean isQueen() {
        return queen;
    }

    public void setQueen(boolean queen) {
        this.queen = queen;
    }

    public Chip(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Chip> getEnemies() {
        return enemies;
    }

    public void addEnemy(Chip chip){
        this.enemies.add(chip);
    }

    public void removeAllEnemies(){
        this.enemies.clear();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
