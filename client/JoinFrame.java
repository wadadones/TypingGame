import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Timer;
import java.util.TimerTask;

public class JoinFrame extends JFrame implements ActionListener, Runnable{

	JoinLayeredPane lPane;
    Container container;
    Socket Sct;
    BufferedReader Br;
    PrintWriter Pw;
    int N = 3;		//参加プレイヤー人数
    String PlayerNum;
    ReadyTask myTask;
    Timer readyTimer;
    int GameReadyCnt = 11;

    public JoinFrame (Socket s) {
        super( "テスト" );
        System.out.println("JoinFrame" );
        Sct = s;
        System.out.println(Sct);
        try {
			Br = new BufferedReader(new InputStreamReader(this.Sct.getInputStream()));
			Pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.Sct.getOutputStream())), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
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
        this.setBackground(Color.BLACK);
        
        // LayeredPaneをコンテナに配置
        lPane = new JoinLayeredPane( this.getWidth(), this.getHeight());
        container = getContentPane();
        lPane.setOpaque(false);
        lPane.button.addActionListener(this);
        container.add( lPane );

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
            String joinPlayerNum;	//参加プレイヤーナンバー
            int cnt = 0;
            //最初に自分のプレイヤーナンバーを取得する
            this.PlayerNum = Br.readLine();
            //自分のプレイヤーナンバーを画面に表示する
            lPane.label.setText("あなたはプレイヤー" + this.PlayerNum + "です");
           
        	//参加プレイヤー待ち処理
            while (true){
            	//参加プレイヤーナンバーを取得する
            	joinPlayerNum = Br.readLine();
            	cnt++;
            	System.out.println("joinPlayerNum : " + joinPlayerNum);
            	lPane.dispPlayerIcon(joinPlayerNum);
            	repaint();
            	//参加プレイヤーが規定数になったら抜ける
            	if (Integer.parseInt(joinPlayerNum) >= this.N){
            		break;
            	}
            }
            //リーダーとライターをクローズする
            //this.Br.close();
            //this.Pw.close();
            myTask = new ReadyTask();
            readyTimer = new Timer();
            readyTimer.schedule(myTask, 0, 1000);
            
        }catch(Exception ex){
            System.out.println("error!!");
        }
    }
    
    public void actionPerformed(ActionEvent e){}
    
    public void readyCounter(){
    	//カウントをひとつ減らす
    	this.GameReadyCnt--;
    	System.out.println(this.GameReadyCnt);
    	//画面に表示
    	lPane.countLabel.setText(String.valueOf(this.GameReadyCnt));
    	
    	//カウントが0になったらゲーム画面に遷移
    	if (this.GameReadyCnt <= 0){
    		this.readyTimer.cancel();
    		//ゲーム画面へ遷移
            System.out.println("Join OK");
            //mf.PanelChange(mf.PanelNames[1], mf.PanelNames[2]);
            TypingGameFrame frame = new TypingGameFrame(this.Br, this.Pw);
            this.setVisible(false);
            frame.setVisible(true);
    	}	
    }
    //タイマーで呼び出されるクラス
    class ReadyTask extends TimerTask{
        public void run(){
            readyCounter();
        }
    }
}
