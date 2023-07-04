package ConnectFour;

import ConnectFour.Screen.DemoScreen.DemoScreen;
import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import ConnectFour.Screen.ResultScreen.ResultScreen;
import ConnectFour.Screen.SelectBoardScreen.SelectBoardScreen;
import ConnectFour.Screen.SelectModeScreen.SelectModeScreen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConnectFour extends Application {
	private static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setStage(stage);
		VBox vb = new VBox();
		vb.setSpacing(20);
		for (int i = 1; i <= 5; i++) {
			//Textに表示する文字列の作成
			String str = String.valueOf(i) + "番目のボタン";
			//Textインスタンス生成
			Text text = new Text(str);
			//作成したインスタンスにクリックしたときのイベントを設定
			text.setOnMouseClicked(new DebugClickButton(i));
			//VBoxに作成したNodeを追加(今回はTextインスタンス)
			vb.getChildren().add(text);
		}
		setScene(new Scene(vb,400,300));
		stage.setTitle("ConnectFour");
		stage.show();
	}

	public static void setScene(Scene scene) {
		getStage().setScene(scene);
	}

	public static void setStage(Stage stage) {
		ConnectFour.stage = stage;
	}

	public static Stage getStage() {
		return ConnectFour.stage;
	}

	class DebugClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private int num;

		//コンストラクタ
		public DebugClickButton(int num) {
			this.num = num;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			switch (num) {
			case 1:
				DemoScreen ds = new DemoScreen();
				setScene(ds.getScene());
				break;
			case 2:
				PlayGameScreen pgs = new PlayGameScreen(false,7, 6);
				setScene(pgs.getScene());
				break;
			case 3:
				ResultScreen rs = new ResultScreen();
				setScene(rs.getScene());
				break;
			case 4:
				SelectBoardScreen sbs = new SelectBoardScreen(false);
				setScene(sbs.getScene());
				break;
			case 5:
				SelectModeScreen sms = new SelectModeScreen();
				ConnectFour.getStage().setScene(sms.getScene());
				break;
			}
		}
	}
}
