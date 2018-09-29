import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.Timer;
import java.util.TimerTask;


public class ResultLayeredPane extends JLayeredPane {
	
	BGPanel bgPanel;

    JPanel wordPane;            //プレイヤーナンバー描画用パネル
    JLabel label;               //プレイヤーナンバー描画用ラベル
    
    JPanel rankPane;
    JLabel rankLabel;

    public ResultLayeredPane( int width, int height) {
        super();

        // 背景となるPanel
        bgPanel = new BGPanel("img/gagehide.png");
        bgPanel.setBounds( 0, 0, width, height );

        this.add( bgPanel);
        this.setLayer( bgPanel , -1 );


        int pointX;     //画像のX座標
        int pointY;     //画像のY座標
        int rotate;     //画像の角度 

        // 重ね合せるPanel
        
        //@@@@@@@@@@文字の追加@@@@@@@@@@@@
        /*  得点  */
        wordPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        wordPane.setBounds( (width / 2) - 300, (height/2) - 200 , 600, 200 );
        wordPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(wordPane);
        this.setLayer( wordPane, 0);

        label = new JLabel("終わり");
        label.setBounds(0, 0, 600, 200);
        label.setFont(new Font(null, Font.PLAIN, 50));
        label.setForeground(Color.ORANGE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        label.setOpaque(false);
        wordPane.add(label);
        
        
        /*  ランキング  */
        rankPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        rankPane.setBounds( (width / 2) - 300, (height/2) - 100 , 600, 200 );
        rankPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(rankPane);
        this.setLayer( rankPane, 0);

        rankLabel = new JLabel("終わり");
        rankLabel.setBounds(0, 0, 600, 200);
        rankLabel.setFont(new Font(null, Font.PLAIN, 50));
        rankLabel.setForeground(Color.ORANGE);
        rankLabel.setHorizontalAlignment(JLabel.CENTER);
        rankLabel.setVerticalAlignment(JLabel.CENTER);

        rankLabel.setOpaque(false);
        rankPane.add(rankLabel);
            
    }
}
