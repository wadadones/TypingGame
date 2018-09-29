import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.geom.*;

import java.io.*;
import java.net.*;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class ResultFrame extends JFrame{
	ResultLayeredPane lPane;
    Container container;
    
    public ResultFrame (String data, int point) {
    	super( "テスト" );
	    System.out.println("ResultFrame" );
	    String result = "";
	    String[] results = new String[2];
	    result = data;  
	    System.out.println(result);
	    //results = result.split(",");
	    
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
        lPane = new ResultLayeredPane( this.getWidth(), this.getHeight());
        lPane.label.setText("得点 : " + String.valueOf(point));
        lPane.rankLabel.setText("順位 : " + result);
        container = getContentPane();
        lPane.setOpaque(false);
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
}
