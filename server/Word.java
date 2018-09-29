import java.io.*;
import java.util.Random;

public class Word {
											//WordList内の単語数
	public static String line;
	public String FileName = "WordList.txt";	//単語リストを読み込むファイル名
	String[] Words;

	//コンストラクタ
	public Word(){
		WordListMaker();
	}

	public int GetFileLineNumbers() {
		int i = 0;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.FileName), "UTF-8"));
			while ((line = reader.readLine()) != null) i++;				//読み込む単語の行数を数える
			reader.close();
		}catch (FileNotFoundException e) {
			System.out.println(this.FileName + "が見つかりません");
		} catch (IOException e) {
			System.out.println(e);
		}
		return i;
	}

	public void WordListMaker() {
		int N = GetFileLineNumbers();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.FileName), "UTF-8"));
			Random rnd = new Random();//WordList.txtから1文字ずつ読み込む
			this.Words = new String[N];							//単語リストとして用いる配列wordを用意
			for (int j = 0; (line = reader.readLine()) != null; j++) {
				System.out.println(line);
				//this.Words[j] = line.split(",", 0);	
				this.Words[j] = line;						//ここで単語分割
			}
			for (int j = 0; j < N; j++) {
				int num = rnd.nextInt(N);
			} //単語をランダムに表示
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println(this.FileName + "が見つかりません");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}