import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PaintImage extends JPanel {
    public static BufferedImage image;
    public ArrayList<BufferedImage> images = new ArrayList<>();
    public int x, y;

    public PaintImage (String path, int x, int y)
    {
        super();
        try
        {
            this.image = ImageIO.read(new File(path));
            this.x = x;
            this.y = y;
            images.add(ImageIO.read(new File(path)));
        }
        catch (IOException e)
        {
            //Not handled.
        }
    }

    public static BufferedImage getImage() {
        return image;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.image, this.getX(), this.getY(), null);
        repaint();
    }
}
