package ConnectFour.Screen.ResultScreen;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultRecordManageMain {
	String rs;
	ResultRecordManageMain(String rs) {
		this.rs = rs;
	}
	
	ResultRecordManageMain() {
		
	}

   public static void ResultRecord (String rs) {
      try {
         //現在時刻を取得し、yyyy/MM/dd HH:mm:ssのフォーマットに設定
         LocalDateTime Now = LocalDateTime.now();
         DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
         String NowTime = dtformat.format(Now);

         //result.txtを読み込む
         BufferedReader br =
         new BufferedReader(
            new InputStreamReader(
               new FileInputStream("result.txt"), "UTF-8"));

         //result.txt
         PrintWriter pw =
         new PrintWriter(
            new BufferedWriter(
               new OutputStreamWriter (
                  new FileOutputStream("result.txt"), "UTF-8")));

         //result.txtを、一番下の行まで進める
         String line;
         while ((line = br.readLine()) != null) {
            pw.println(line);
         }

         //一番下の行に、結果を追加
         pw.println(NowTime + " " + rs);

         //result.txtを閉じる
         br.close();
         pw.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
	
	public static void ResultRecordShow () {
      try {
            File file = new File("result.txt");
            if(!Desktop.isDesktopSupported()){
               System.out.println("not supported");
               return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists())
               desktop.open(file);
      } catch(Exception e) {
         e.printStackTrace();
      }
	}

}
