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

public class BGPanel extends JPanel {
    // 描画する画像
    private BufferedImage image;
    public BGPanel(String path) {
        try {
            this.image = ImageIO.read(getClass().getResource(path));
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
        double panelWidth = this.getWidth();
        double panelHeight = this.getHeight();
        // 画像がコンポーネントの何倍の大きさか計算
        double sx = (panelWidth / imageWidth);
        double sy = (panelHeight / imageHeight);
        // スケーリング
        AffineTransform af = AffineTransform.getScaleInstance(sx, sy);
        g2D.drawImage(image, af, this);
    }
}
