import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.imageio.*;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
import java.net.*;


public class ImagePanel extends JPanel {

    // 描画する画像
    private BufferedImage image;
    private int imageX;
    private int imageY;
    private int imageRotate;

    public ImagePanel(String path, int x, int y, int rotate) {
        try {
            this.image = ImageIO.read(getClass().getResource(path));
            this.imageX = x;
            this.imageY = y;
            this.imageRotate = rotate;
        } catch (IOException ex) {
            ex.printStackTrace();
            this.image = null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();   
        AffineTransform af = new AffineTransform();
        af.setToRotation(imageRotate * Math.PI / 180
                        , imageX + (imageWidth / 2)
                        , imageY + (imageHeight / 2) );
        g2D.setTransform(af);
        g2D.drawImage(image, imageX, imageY, this);
    }
}
