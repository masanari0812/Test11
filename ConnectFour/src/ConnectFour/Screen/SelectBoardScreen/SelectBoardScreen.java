package ConnectFour.Screen.SelectBoardScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SelectBoardScreen extends OriginScreen {
	private boolean online;
	private TextField columnTF;
	private TextField rowTF;

	public SelectBoardScreen(boolean online) {
		this.online = online;
		//複数のNodeを横に結合できるVBoxを生成
		VBox vb = new VBox();
		//(ButtonやTextなどの)Nodeの感覚を20pxに設定する。
		int column, row;
		vb.setSpacing(20);
		//1から5までのテキストを作りClickButtonイベントを設定する。
		for (int i = 1; i <= 3; i++) {
			//Textに表示する文字列(盤面の大きさ）の作成
			switch (i) {
			case 1:
				column = 7;
				row = 6;
				break;
			case 2:
				column = 9;
				row = 7;
				break;
			case 3:
				column = 12;
				row = 6;
				break;
			default:
				column = -1;
				row = -1;
			}
			//Textインスタンス生成
			Button bt = new Button(String.valueOf(column) + "×" + String.valueOf(row));
			//作成したインスタンスにクリックしたときのイベントを設定
			bt.setOnMouseClicked(new ClickButton(column, row));
			//VBoxに作成したNodeを追加(Textインスタンス)
			vb.getChildren().add(bt);
		}
		/*//メンバ変数tfにTextFieldインスタンスを生成し代入
		
		tf = new TextField();
		
		//VBoxに作成したNodeを追加(今回はTextFieldインスタンス)
		vb.getChildren().add(tf);
		
		
		/*作成したVBoxをもとにSceneインスタンスを生成
		//代入先はこのクラスの継承元OriginScreenのメンバ変数scene */

		HBox size = new HBox();
		this.columnTF = new TextField();
		this.rowTF = new TextField();
		columnTF.setEditable(false);
		rowTF.setEditable(false);
		Text midText = new Text("×");
		size.getChildren().addAll(columnTF, midText, rowTF);
		Button start = new Button("Start");
		start.setOnMousePressed(new ClickStart());
		vb.getChildren().addAll(size, start);
		scene = new Scene(vb,400,300);

	}

	//Textインスタンスがクリックされたときに発生するイベントの定義
	class ClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private int column;
		private int row;

		//コンストラクタ
		public ClickButton(int column, int row) {
			this.column = column;
			this.row = row;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			//TextFieldに表示する文字列(盤面の大きさ)の作成
			//TextFieldの文字列の変更処理
			columnTF.setText(String.valueOf(column));
			rowTF.setText(String.valueOf(row));
		}
	}

	class ClickStart implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent e) {
			int column=Integer.parseInt(columnTF.getText());
			int row=Integer.parseInt(rowTF.getText());
			changeNextScreen(new PlayGameScreen(online,column,row));
		}

	}

}
