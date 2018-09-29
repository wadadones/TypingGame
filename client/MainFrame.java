import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.*;
import java.net.*;
 
public class MainFrame{
    public String[] PanelNames = {"input", "join", "type", "result"};
    //InputPanel input = new InputPanel(this);
    //JoinPanel join = new JoinPanel(this);
    //TypingGamePanel type = new TypingGamePanel(this);
    //ResultPanel res = new ResultPanel(this,PanelNames[3]);
     
    public MainFrame(){
        //this.add(input);input.setVisible(true);
        //this.add(join);join.setVisible(false);
        //this.add(type);type.setVisible(true);
        
        //入力画面へ遷移
        InputFrame frame = new InputFrame();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
    }
    
}