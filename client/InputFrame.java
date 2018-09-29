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


public class InputFrame extends JFrame implements ActionListener, MouseListener{

	InputLayeredPane lPane;
    Container container;
        
  //@@@@@@@@サーバとの接続用@@@@@@@@@
    int Port;
    String Address;//接続先IPアドレスの入力
    InetAddress Addr;
    Socket Sct;//設定要求を送る

    //入力されたポート番号、IPアドレスでサーバに接続する
    public boolean ConnectServer(){
        try{
            //ここで接続処理
            System.out.println("ポート番号:" + Port + " IPアドレス:" + Address);
            Addr = InetAddress.getByName(Address);
            System.out.println("addr = " + Addr);
            Sct = new Socket(Addr, Port);//設定要求を送る
            
        }catch(Exception ex){
            //NGだった場合はもう一度入力させる
            return false;
            //init();
        }
        return true;
    }

    public InputFrame () {
        super( "テスト" );
        System.out.println("InputFrame" );
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
        lPane = new InputLayeredPane( this.getWidth(), this.getHeight());
        
        container = getContentPane();

        lPane.setOpaque(false);
        lPane.button.addActionListener(this);
        //lPane.startBtn.addMouseListener(this);
        container.add( lPane );
        setVisible( true );
        addWindowListener( new WindowAdapter() {
                               public void windowClosing( WindowEvent ev ) {
                                   dispose();
                                   System.exit( 0 );
                               }
                           }
                         );
    }
  //入力確認
    public boolean checkInput(){
      return true;
    }
    
  //接続ボタン押下処理
    public void actionPerformed(ActionEvent e){
      //入力確認がOKなら次の画面に遷移
      if(checkInput()){
        System.out.println("Input OK");
        //サーバとの接続
        this.Port = Integer.parseInt(lPane.pText.getText());
        this.Address = lPane.aText.getText();
        //サーバとのソケット通信がOKなら次の画面へ遷移
        if(ConnectServer()){
          System.out.println("ソケットOK");  
          JoinFrame frame = new JoinFrame(this.Sct);
          this.setVisible(false);
          frame.setVisible(true);
        }else{
          System.out.println("ソケットNG");
        }
      }
    }
    
    public void mouseClicked(MouseEvent e){
        /* 処理したい内容をここに記述する */
    	//入力確認がOKなら次の画面に遷移
        if(checkInput()){
          System.out.println("Input OK");
          //サーバとの接続
          this.Port = Integer.parseInt(lPane.pText.getText());
          this.Address = lPane.aText.getText();
          //サーバとのソケット通信がOKなら次の画面へ遷移
          if(ConnectServer()){
            System.out.println("ソケットOK");       
            JoinFrame frame = new JoinFrame(this.Sct);
            this.setVisible(false);
            frame.setVisible(true);
          }else{
            System.out.println("ソケットNG");
          }
        }    
    }
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){} 
}
