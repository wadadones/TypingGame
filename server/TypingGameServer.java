import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;


public class TypingGameServer {
	
	public static final int N = 3;					//プレイヤー人数
	public int PORT = 8080;	
	public Scanner sc = new Scanner(System.in);
	private static int[] point = new int[N+1];						//得点を入れる配列
	
	private static ServerThread[] players = new ServerThread[N];
	private static int plCnt = 0;		//参加プレイヤーカウント　

	public TypingGameServer(){
		try{
			System.out.println("ポート番号を入力してください");
			PORT = sc.nextInt();
			ServerSocket s = new ServerSocket(PORT);
			Socket[] socket = new Socket[N];

			int i, j;
			System.out.println("サーバーを立ち上げました: " + s);

			//単語リスト取得
			Word word = new Word();		//単語リスト作成クラスのインスタンス作成
			for (i = 0; i < word.Words.length; i++) {
				System.out.println(word.Words[i]);
			}
			//得点表の初期化
			for (i = 0; i < point.length; i++) {
				point[i] = 0;
			}
			System.out.println("Connecting...");
			try {
				for(i = 0; i < N; i++){
					socket[i] = s.accept();
					//プレイヤーカウントをインクリメントする
					this.plCnt++;
					System.out.println("プレイヤーが" + (i + 1) + "人集まりました");
					players[i] = new ServerThread(socket[i], (i + 1), word.Words);
					players[i].start();
				}
				//それぞれのクライアントスレッドに他のクライアントスレッドの情報を送る
				for (i = 0; i < N; i++) {
					players[i].pls = players;					
				}

				Arrays.fill(point, 0);	
				System.out.println("-------------");
				System.out.println("ゲームスタート！");
				System.out.println("-------------");
				System.out.println("-------------");
				System.out.println("player 1　:"+point[1]);
				System.out.println("-------------");
			} finally {
				s.close();
			}
		}catch(IOException ex){
			System.out.println(ex.toString());
		}
	}
	//ポート番号設定
	public static void main(String[] args) throws IOException { //ソケット作成
		TypingGameServer typeSrv = new TypingGameServer();
	}
	public static void ShowTimer(int time){
		int i = 0;
		while(i<time){
			try{
				Thread.sleep(1000); //1000ミリ秒Sleepする
				System.out.println(time-i);
				i++;
			}catch(InterruptedException e){}
		}
	}
	//参加プレイヤー数ゲットメソッド
	public static int getPlCnt(){
		return plCnt;
	}
	//得点セットメソッド
	public static void incPoint(int plNum, int p){
		point[plNum] += p;
	}
	public static void setPoint(int plNum, int p){
		point[plNum] = p;
	}
	//得点ゲットメソッド
	public static int getPoint(int plNum){
		return point[plNum];
	}
	//順位ゲットメソッド
	public static int getRank(int plNum){
		int rank = 1;
		for (int i = 0; i < point.length; i++) {
			if (point[plNum] < point[i]) {
				rank++;
			}
		}
		return rank;
	}
}

class ServerThread extends Thread {
	private Socket socket;
	private int playerNum;
	private String[] Wordlist;

	public ServerThread[] pls = new ServerThread[TypingGameServer.N - 1];

	BufferedReader in;
	PrintWriter out;
	
	public ServerThread(Socket socket, int playerNum, String[] words) {
		this.socket = socket;
		this.playerNum = playerNum;
		this.Wordlist = words;
	}
	
	public void run() {										//個々のクライアントへの処理
		System.out.println("run");
		Random r = new Random();							//乱数生成用
		try {
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//データ受信用バッファの設定
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			//out.println(this.Wordlist);
			int wordCnt = 0;
			String str = "";
			String strBefore = "";
			int plCntBefor = 0;
			int plCnt;
			int i;
			String point;

			//プレイヤーナンバーを送信
			out.println(this.playerNum);

			//プレイヤー全員がスタートボタンを押すまで待つ
			while (true){
				//現在の参加プレイヤー数をゲット
				plCnt = TypingGameServer.getPlCnt();
				System.out.println(this.playerNum + ":" + plCnt);
				//前回のプレイヤー数と変わっていた場合、送信
				if (plCntBefor != plCnt) {
					//System.out.println(this.playerNum + ":" + plCnt);
					out.println(plCnt);
					plCntBefor = plCnt;
				}

				//参加プレイヤー数が規定数以上になった場合、処理を抜ける
				if (plCnt >= TypingGameServer.N) {
					break;
				}
			}

			System.out.println(this.Wordlist[wordCnt]);
			out.println(this.Wordlist[wordCnt]);	//最初の単語を送信　
			while (true){
				str = in.readLine();
				//終了コマンドが送信されてきた場合
				if (str.equals("END")) {
					System.out.println("END");
					//得点を取得する
					point = in.readLine();
					System.out.println("得点 : " + point);

					//各プレイヤーの得点を配列に格納する
					TypingGameServer.setPoint(this.playerNum, Integer.parseInt(point) * 10);

					//１秒間待機
					try{
						Thread.sleep(1000);
					}catch(Exception ex){

					}
					//順位を送信する
					out.println(TypingGameServer.getRank(this.playerNum));
					break;
				}

				//クライアントから新しい単語が送信されていた場合
				if (str != null && str != strBefore) {
					System.out.println("player " + playerNum + " が単語「" + str + "」をゲットしました");
					//得点をインクリメントする
					//TypingGameServer.incPoint(playerNum, str.length() * 10);

					//他のプレイヤーに情報を送る
					for (i = 0; i < this.pls.length; i++) {
						System.out.println("プレイヤーナンバー : " + i);
						this.pls[i].out.println(playerNum);
					}

					wordCnt++;		//単語リストを次の単語にする

					//単語カウンタが単語リストの長さよりも大きくなった場合、最初から始める
					if (wordCnt >= this.Wordlist.length){
						wordCnt = 0;
					}
					System.out.println(this.Wordlist[wordCnt]);
					out.println(this.Wordlist[wordCnt]);		//次の単語を送信
					strBefore = str;	//今回送信された単語を記憶する
				}
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("closing..." + playerNum);
				socket.close();
			} catch (IOException e) {
				System.out.println("切断されました " + socket.getRemoteSocketAddress());
			}
		}
	}
}