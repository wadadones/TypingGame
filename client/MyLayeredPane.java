import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Timer;
import java.util.TimerTask;

public class MyLayeredPane extends JLayeredPane {

    BGPanel bgPanel;            //背景描画用パネル
    ImagePanel gagePanel;       //制限時間ゲージ描画用パネル
    JPanel gageHidePanel;   //制限時間ゲージ隠すようパネル
    ImagePanel handPanel1;      //プレイヤー１腕描画用パネル
    ImagePanel handPanel2;      //プレイヤー２腕描画用パネル
    ImagePanel handPanel3;      //プレイヤー3腕描画用パネル
    ImagePanel handPanel4;      //プレイヤー4腕描画用パネル
    JPanel wordPane;            //タイピングラベル描画用パネル
    JLabel label;               //タイピング文字列描画用ラベル
    JPanel jwordPane;			//日本語訳表示用パネル
    JLabel jlabel;				//日本語訳表示用ラベル
    String[] point;
    
    public MyLayeredPane( int width, int height) {
        super();
        // 背景となるPanel
        bgPanel = new BGPanel("img/background/back1.png");
        bgPanel.setBounds( 0, 0, width, height );
        this.add( bgPanel);
        this.setLayer( bgPanel , -1 );

        int pointX;     //画像のX座標
        int pointY;     //画像のY座標
        int rotate;     //画像の角度 
        // 重ね合せるPanel
        //@@@@@@@@制限時間ゲージ@@@@@@@@
        pointX = 10;
        pointY = 10;
        rotate = 0;
        gagePanel = new ImagePanel("img/gage.png"
                                        , pointX
                                        , pointY
                                        , rotate);
        gagePanel.setBounds(0, 0, width, height);
        gagePanel.setOpaque(false);
        this.add(gagePanel);
        this.setLayer(gagePanel, 0);

        //@@@@@@@制限時間隠す用ゲージ@@@@@@@
        pointX = 0;
        pointY = 0;
        rotate = 0;
        gageHidePanel = new ImagePanel("img/gagehide.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        
        gageHidePanel.setOpaque(true);
        gageHidePanel.setVisible(true);

        this.add( gageHidePanel);
        this.setLayer( gageHidePanel , 1);

        //@@@@@@@@プレイヤー1@@@@@@@@
        pointX = 230;
        pointY = 250;
        rotate = 180;
        handPanel1 = new ImagePanel("img/hands/hand1.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        handPanel1.setBounds( 0, 0, width, height );
        handPanel1.setOpaque(false);
        //初期状態は非表示にしておく
        handPanel1.setVisible(false);

        this.add( handPanel1);
        this.setLayer( handPanel1 , 2);
        
        //@@@@@@@@プレイヤー2@@@@@@@@
        pointX = 100;
        pointY = 0;
        rotate = 225;
        handPanel2 = new ImagePanel("img/hands/hand2.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        handPanel2.setBounds( 0, 0, width, height );
        handPanel2.setOpaque(false);
        //初期状態は非表示にしておく
        handPanel2.setVisible(false);
        this.add( handPanel2);
        this.setLayer( handPanel2 , 2);
        
      //@@@@@@@@プレイヤー3@@@@@@@@
        pointX = 300;
        pointY = 0;
        rotate = 0;
        handPanel3 = new ImagePanel("img/hands/hand3.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        handPanel3.setBounds( 0, 0, width, height );
        handPanel3.setOpaque(false);
        //初期状態は非表示にしておく
        handPanel3.setVisible(false);

        this.add( handPanel3);
        this.setLayer( handPanel3 , 2);
        
      //@@@@@@@@プレイヤー4@@@@@@@@
        pointX = 300;
        pointY = 0;
        rotate = 45;
        handPanel4 = new ImagePanel("img/hands/hand4.png"
                                                , pointX
                                                , pointY
                                                , rotate);
        handPanel4.setBounds( 0, 0, width, height );
        handPanel4.setOpaque(false);
        //初期状態は非表示にしておく
        handPanel4.setVisible(false);

        this.add( handPanel4);
        this.setLayer( handPanel4 , 2);


        //@@@@@@@@@@タイピング文字の追加@@@@@@@@@@@@
        /* タイピングようアルファベット */
        wordPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        wordPane.setBounds(0, height - 120 , width, 100 );
        wordPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(wordPane);
        this.setLayer( wordPane, 3);

        label = new JLabel();
        label.setBounds(0, height / 2, width, 100);
        label.setFont(new Font(null, Font.PLAIN, 60));
        label.setForeground(Color.ORANGE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setOpaque(false);
        wordPane.add(label);

        /* タイピングよう日本語 */
        jwordPane = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        };
        jwordPane.setBounds(0, height - 150 , width, 100 );
        jwordPane.setOpaque(false);
        //wordPane.setBackground(new Color(0, 0, 0, 0));
        this.add(jwordPane);
        this.setLayer(jwordPane, 3);

        jlabel = new JLabel();
        jlabel.setBounds(0, height / 2, width, 100);
        jlabel.setFont(new Font(null, Font.PLAIN, 30));
        jlabel.setForeground(Color.ORANGE);
        jlabel.setHorizontalAlignment(JLabel.CENTER);
        jlabel.setVerticalAlignment(JLabel.CENTER);
        jlabel.setOpaque(false);
        jwordPane.add(jlabel); 
    }

    public void dispHand(String player) {
        //指定されたプレイヤーの画像を表示する
        switch (player){
            case "1":
                handPanel1.setVisible(true);
                break;
            case "2":
                handPanel2.setVisible(true);
                break;
            case "3":
                handPanel3.setVisible(true);
                break;
            case "4":
                handPanel4.setVisible(true);
                break;
        }
        HandTask myTask = new HandTask(player);
        Timer handTimer = new Timer();
        handTimer.schedule(myTask, 500);
    }

    public void hideHand(String player) {
        //指定されたプレイヤーの画像を非表示にする
        switch (player){
            case "1":
                handPanel1.setVisible(false);
                break;
            case "2":
                handPanel2.setVisible(false);
                break;
            case "3":
                handPanel3.setVisible(false);
                break;
            case "4":
                handPanel4.setVisible(false);
                break;
        }
    }
    //腕表示タイマーで呼び出されるクラス
    class HandTask extends TimerTask{
    	String player;
    	public HandTask(String p){
    		player = p;
    	}
        //実際に処理する関数
        public void run(){
            hideHand(this.player);
        }
    }
}
