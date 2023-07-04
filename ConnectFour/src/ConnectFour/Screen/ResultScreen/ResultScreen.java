package ConnectFour.Screen.ResultScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import ConnectFour.Screen.SelectModeScreen.SelectModeScreen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResultScreen extends OriginScreen {

	private Button button;
	private Button button1;
	private static TextField tf;

	public ResultScreen(boolean win, boolean online, int column, int row) {
		Text result;
		if (win)
			result = new Text("Win!!");
		else
			result = new Text("Lose...");
		BorderPane.setAlignment(result, Pos.CENTER);
		result.setFont(new Font(25));
		button = new Button("続ける");
		button1 = new Button("モード選択");
		button.setOnMousePressed(event -> {
			changeNextScreen(new PlayGameScreen(online, column, row));
		});
		button1.setOnMousePressed(event -> {
			changeNextScreen(new SelectModeScreen());
		});
		button.setFont(new Font(25));
		//buttonの文字の大きさを25にする
		button1.setFont(new Font(25));
		//button1の文字の大きさを25にする
		BorderPane bp = new BorderPane();
		bp.setBottom(result);
		bp.setLeft(button);
		bp.setRight(button1);
		/*//button.setOnAction(new MousePressedHandler());
		
		//継承元のOriginScreenにあるscene変数に格納 */
		this.scene = new Scene(bp, 300, 200);
	}
}
