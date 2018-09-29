import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.Timer;
import java.util.TimerTask;


public class InputLayeredPane extends JLayeredPane {
	
	BGPanel bgPanel;

    ImagePanel pImagePane;            //ポート番号描画用パネル
    ImagePanel aImagePane;            //アドレス描画用パネル
    
    JPanel pWordPane;
    JPanel aWordPane;
    
    JTextField pText;
    JTextField aText;
    
    JPanel btnPane;
    JButton button;
    
    ImagePanel startBtn;

    public InputLayeredPane( int width, int height) {
        super();
        System.out.println("InputLayerdPane");

        // 背景となるPanel
        bgPanel = new BGPanel("img/background/kuro.jpg");
        bgPanel.setBounds( 0, 0, width, height );

        this.add( bgPanel);
        this.setLayer( bgPanel , -1 );


        int pointX;     //画像のX座標
        int pointY;     //画像のY座標
        int rotate;     //画像の角度 

        // 重ね合せるPanel
        
      //@@@@@@@@ポート番号入力文言@@@@@@@@
        pointX = width / 2 - 250;
        pointY = 10;
        rotate = 0;
        pImagePane = new ImagePanel("img/port.png"
                                        , pointX
                                        , pointY
                                        , rotate);
        pImagePane.setBounds(0, 0, width, height);
        pImagePane.setOpaque(false);

        this.add(pImagePane);
        this.setLayer(pImagePane, 0);   
        
        
      //@@@@@@@@@@ポート入力欄@@@@@@@@@@@@
        pWordPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        pWordPane.setBounds( (width / 2) - 150, 70 , 300, 100 );
        pWordPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(pWordPane);
        this.setLayer( pWordPane, 1);

        pText = new JTextField("", 20);
        pText.setBounds(width / 2, height / 2, 300, 100);
        
        pText.setOpaque(true);
        pWordPane.add(pText);
        
        
      //@@@@@@@@アドレス入力文言@@@@@@@@
        pointX = width / 2 - 250;
        pointY = 200;
        rotate = 0;
        aImagePane = new ImagePanel("img/address.png"
                                        , pointX
                                        , pointY
                                        , rotate);
        aImagePane.setBounds(0, 0, width, height);
        aImagePane.setOpaque(false);

        this.add(aImagePane);
        this.setLayer(aImagePane, 0);     
        
      //@@@@@@@@@@アドレス入力欄@@@@@@@@@@@@
        aWordPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        aWordPane.setBounds( (width / 2) - 150, 260 , 300, 100 );
        aWordPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(aWordPane);
        this.setLayer( aWordPane, 1);

        aText = new JTextField("", 20);
        aText.setBounds(width / 2, height / 2, 300, 100);

        aText.setOpaque(true);
        aWordPane.add(aText);

      //@@@@@@@@スタートボタン@@@@@@@@
        pointX = width / 2;
        pointY = 300;
        rotate = 0;
        startBtn = new ImagePanel("img/start.png"
                                        , pointX
                                        , pointY
                                        , rotate);
        startBtn.setBounds(10, 300, 300, 200);
        startBtn.setOpaque(false);
        startBtn.setVisible(true);

        //this.add(startBtn);
        this.setLayer(startBtn, 1);   
        
        //@@@@@@@@ボタンの追加@@@@@@@@@@@
        btnPane = new JPanel(){
        	public void paintComponent(Graphics g){
        		super.paintComponent(g);
        	}
        };
        btnPane.setBounds(width / 2 - 100, 400, 200, 100);
        btnPane.setOpaque(false);
        this.add(btnPane);
        this.setLayer(btnPane, 1);
        
        button = new JButton("START!");
        button.setSize(200, 100);
        
        button.setOpaque(false);
        btnPane.add(button);
    }
}
