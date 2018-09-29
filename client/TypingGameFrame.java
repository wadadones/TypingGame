import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class TypingGameFrame extends JFrame implements KeyListener, Runnable{

    MyLayeredPane lPane;
    Container container;
    JButton mybtn;
    JLabel label;
    JPanel wordPane;
    Socket Sct;
    BufferedReader Br;
    PrintWriter Pw;
    //MainFrame mf;
    int wdCnt = 0;
    String[] words;
    int timeLimit = 60;
    int timeNow = 0;
    int gageHeight = 480;
    int hideSpeed = gageHeight / timeLimit;
    TimelimitTask timeLimitTask;
    Timer timeLimitTimer;
    boolean isTimeUp = false;
    int point = 0;

    public TypingGameFrame (BufferedReader b, PrintWriter p) {
        super( "テスト" );
        System.out.println("TypingGameFrame" );

        this.Br = b;
        this.Pw = p;
        try {
            // 外観を設定します
            //UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
            // 外観を変更します
            SwingUtilities.updateComponentTreeUI( this );
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit( -1 );
        }

        setBounds( 0, 0, 960, 540 );
        //setBackground(new Color(0, 0, 0, 0f));

        // LayeredPaneをコンテナに配置
        lPane = new MyLayeredPane( this.getWidth(), this.getHeight());
        lPane.gageHidePanel.setBounds( 10, -this.gageHeight, 90, 480 );
        container = getContentPane();

        lPane.setOpaque(false);
        container.add( lPane );

        addKeyListener(this);
        
        timeLimitTask = new TimelimitTask();
        timeLimitTimer = new Timer();
        timeLimitTimer.schedule(timeLimitTask, 0, 1000);

        //サーバとの通信スレッド起動
        Thread th = new Thread(this);
        th.start();
        setVisible( true );

        addWindowListener( new WindowAdapter() {
                               public void windowClosing( WindowEvent ev ) {
                                   dispose();
                                   System.exit( 0 );
                               }
                           }
                         );
    }

    //単語リスト取得処理
    public void run(){
        System.out.println("run");
        try{
            String data;
            String wordNow = "";
            while (true){
            	data = Br.readLine();
            	//タイムアップだった場合は結果表示画面に遷移する
            	if (this.isTimeUp){
            		this.timeLimitTimer.cancel();
            		ResultFrame frame = new ResultFrame(data, this.point);
                    this.setVisible(false);
                    frame.setVisible(true);
            		break;
            	}
            	
            	//単語か他プレイヤーのデータか判断する
            	//単語だった場合(文字列にカンマが含まれていた場合)
            	if (data.indexOf(",") >= 0){
            		//現在の単語と違うものだった場合は画面に表示
                	if (data != null && data != wordNow){
                		wordNow = data;		//現在画面に表示している単語を更新
                		
                		System.out.println(data);
                    	this.words = data.split(",", 0);

                        System.out.println("日本語:" + words[0] + "　アルファベット:" + words[1]);

                        //サーバから受け取った単語を画面に表示
                        System.out.println(words[1]);
                    	lPane.label.setText(words[1]);
                    	System.out.println(words[0]);
                    	lPane.jlabel.setText(words[0]);
                	}
            	}else{	//他プレイヤーの情報だった場合
            		lPane.dispHand(data);
            	}	
            }
        }catch(Exception ex){
            System.out.println("error!!");
        } 
    }

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
    	
    	String text = lPane.label.getText();
        if (text.charAt(0) == e.getKeyChar()) {
        	
        	//得点をインクリメントする
        	this.point++;
        	
            if (text.length() > 1) {
                lPane.label.setText(text.substring(1));
            }
            else {
                wdCnt++;
                
                //lPane.label.setText(words[1]);
                System.out.println(words[1]);
                Pw.println(words[1]);
                //lPane.dispHand(1);
                
                //lPane.hideHand(1);
            }
            System.out.println(wdCnt);
        }
        repaint();
    }
    
  //時間制限隠す用パネルを動かす
    public void gageMove(){    
        this.timeNow++;
        if (this.timeNow >= this.timeLimit) {
        	this.isTimeUp = true;
        	System.out.println("TimeUp");
            //mf.PanelChange(mf.PanelNames[1], mf.PanelNames[2]);
        	
        	//終了コマンドを投げる
        	Pw.println("END");
        	
        	//自分の得点を投げる
        	Pw.println(this.point);       
        }
        lPane.gageHidePanel.setBounds( 10, -this.gageHeight + (this.timeNow * this.hideSpeed), 90, this.gageHeight );
        System.out.println(this.timeNow);
    }
    
  //制限時間タイマーで呼び出されるクラス
    class TimelimitTask extends TimerTask{
        public void run(){
            gageMove();
        }
    }
}
