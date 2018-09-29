import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Timer;
import java.util.TimerTask;

public class JoinLayeredPane extends JLayeredPane {
	
	BGPanel bgPanel;
    ImagePanel playerPanel1;      //プレイヤー１アイコン描画用パネル
    ImagePanel playerPanel2;      //プレイヤー２アイコン描画用パネル
    ImagePanel playerPanel3;      //プレイヤー3アイコン描画用パネル
    ImagePanel playerPanel4;      //プレイヤー4アイコン描画用パネル
    ImagePanel playerCase;			//プレイヤーアイコン囲み
    ImagePanel playerWord;
    JPanel wordPane;            //プレイヤーナンバー描画用パネル
    JLabel label;               //プレイヤーナンバー描画用ラベル
    JPanel btnPane;  
    JPanel countPane;
    JLabel countLabel;
    JButton button;

    public JoinLayeredPane( int width, int height) {
        super();

        // 背景となるPanel
        bgPanel = new BGPanel("img/background/kuro.jpg");
        bgPanel.setBounds( 0, 0, width, height );
        this.add( bgPanel);
        this.setLayer( bgPanel , -1 );
        int pointX;     //画像のX座標
        int pointY;     //画像のY座標
        int rotate;     //画像の角度 

        // 重ね合せるPanel
        
        //@@@@@@@@@@文字の追加@@@@@@@@@@@@
        /*  プレイヤーナンバー表示用  */
        wordPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        //wordPane.setBounds( (width / 2) - 150, height - 120 , 300, 100 );
        wordPane.setBounds( (width / 2) - 150, 10, 300, 100 );
        wordPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(wordPane);
        this.setLayer( wordPane, 0);

        label = new JLabel();
        label.setBounds(width / 2, height / 2, 300, 100);
        label.setFont(new Font(null, Font.PLAIN, 15));
        label.setForeground(Color.ORANGE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setOpaque(false);
        wordPane.add(label);
        
        /*  カウント表示用  */
        countPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        countPane.setBounds( (width / 2) - 150, (height / 2) - 150 , 300, 100 );
        countPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(countPane);
        this.setLayer(countPane, 0);

        countLabel = new JLabel();
        countLabel.setBounds(width / 2, height / 2, 300, 100);
        countLabel.setFont(new Font(null, Font.PLAIN, 60));
        countLabel.setForeground(Color.ORANGE);
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        countLabel.setVerticalAlignment(JLabel.CENTER);

        countLabel.setOpaque(false);
        countPane.add(countLabel);
        
        //@@@@@@@@ボタンの追加@@@@@@@@@@@
        btnPane = new JPanel(){
        	public void paintComponent(Graphics g){
        		super.paintComponent(g);
        	}
        };
        btnPane.setBounds(10, 10, 200, 100);
        btnPane.setOpaque(false);
        this.add(btnPane);
        this.setLayer(btnPane, 0);
        
        button = new JButton("START");
        button.setSize(200, 100);
        
        button.setOpaque(false);
        //btnPane.add(button);
        
      //@@@@@@@@集まったプレイヤー@@@@@@@@
        pointX = 480;
        pointY = 250;
        rotate = 0;
        playerWord = new ImagePanel("img/playericon/playerword.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        playerWord.setBounds( 0, 0, width, height );
        playerWord.setOpaque(false);
        //初期状態は非表示にしておく
        playerWord.setVisible(true);

        this.add( playerWord);
        this.setLayer( playerWord , 0);
        
      //@@@@@@@@囲み@@@@@@@@
        pointX = 480;
        pointY = 380;
        rotate = 0;
        playerCase = new ImagePanel("img/playericon/case.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        playerCase.setBounds( 0, 0, width, height );
        playerCase.setOpaque(false);
        //初期状態は非表示にしておく
        playerCase.setVisible(true);

        this.add( playerCase);
        this.setLayer( playerCase , 0);

        //@@@@@@@@プレイヤー1@@@@@@@@
        pointX = 500;
        pointY = 400;
        rotate = 0;
        playerPanel1 = new ImagePanel("img/playericon/player1.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        playerPanel1.setBounds( 0, 0, width, height );
        playerPanel1.setOpaque(false);
        //初期状態は非表示にしておく
        playerPanel1.setVisible(false);

        this.add( playerPanel1);
        this.setLayer( playerPanel1 , 1);
        
        //@@@@@@@@プレイヤー2@@@@@@@@
        pointX = 560;
        pointY = 400;
        rotate = 0;
        playerPanel2 = new ImagePanel("img/playericon/player2.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        playerPanel2.setBounds( 0, 0, width, height );
        playerPanel2.setOpaque(false);
        //初期状態は非表示にしておく
        playerPanel2.setVisible(false);

        this.add( playerPanel2);
        this.setLayer( playerPanel2 , 1);

        //@@@@@@@@プレイヤー3@@@@@@@@
        pointX = 620;
        pointY = 400;
        rotate = 0;
        playerPanel3 = new ImagePanel("img/playericon/player3.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        playerPanel3.setBounds( 0, 0, width, height );
        playerPanel3.setOpaque(false);
        //初期状態は非表示にしておく
        playerPanel3.setVisible(false);

        this.add( playerPanel3);
        this.setLayer( playerPanel3 , 1);

        //@@@@@@@@プレイヤー4@@@@@@@@
        pointX = 680;
        pointY = 400;
        rotate = 0;
        playerPanel4 = new ImagePanel("img/playericon/player4.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        playerPanel4.setBounds( 0, 0, width, height );
        playerPanel4.setOpaque(false);
        //初期状態は非表示にしておく
        playerPanel4.setVisible(false);
        this.add( playerPanel4);
        this.setLayer( playerPanel4 , 1);
    }
    
    //プレイヤーアイコン表示処理
    public void dispPlayerIcon(String playerNum){
    	System.out.println("dispPlayerIcon");
    	switch(playerNum){
    		case "1":
    			playerPanel1.setVisible(true);
    			break;
    		case "2":
    			playerPanel1.setVisible(true);
    			playerPanel2.setVisible(true);
    			break;
    		case "3":
    			playerPanel1.setVisible(true);
    			playerPanel2.setVisible(true);
    			playerPanel3.setVisible(true);
    			break;
    		case "4":
    			playerPanel1.setVisible(true);
    			playerPanel2.setVisible(true);
    			playerPanel3.setVisible(true);
    			playerPanel4.setVisible(true);
    			break;
    	}
    }
}
