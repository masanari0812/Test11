package ConnectFour.Screen.SelectModeScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.SelectBoardScreen.SelectBoardScreen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SelectModeScreen extends OriginScreen{
	
	private static TextField tf;
	private Button button;
	

	public SelectModeScreen() {
		//複数のNodeを盾に結合できるVBoxを生成
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		//(ButtonやTextなどの)Nodeの感覚を20pxに設定する。

		vb.setSpacing(20);
		//1から5までのテキストを作りClickButtonイベントを設定する。
		for (int i = 1; i <= 2; i++) {
			/*//Textに表示する文字列の作成
			
			//Textインスタンス生成
			
			//作成したインスタンスにクリックしたときのイベントを設定
			
			//VBoxに作成したNodeを追加(Textインスタンス) */
			String str = String.valueOf(i) + "人で対戦";
			Button bt = new Button(str);
			/* Button button = new Button(str);
			//button.setPrefSize(30,20); */
			vb.getChildren().add(bt);
			
			//Text text = new Text(button);
			if(i==1)
			bt.setOnMouseClicked(new ClickButton(false));
			else
				bt.setOnMouseClicked(new ClickButton(true));
			
		}
		
		//メンバ変数tfにTextFieldインスタンスを生成し代入

		/*//作成したVBoxをもとにSceneインスタンスを生成
		//代入先はこのクラスの継承元OriginScreenのメンバ変数scene */
		scene=new Scene(vb,400,300);
	}

	

	//Textインスタンスがクリックされたときに発生するイベントの定義
	class ClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private boolean online;

		//コンストラクタ
		public ClickButton(boolean multi) {
			this.online=multi;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			changeNextScreen(new SelectBoardScreen(online));
				
		}
	}


}




	