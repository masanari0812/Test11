package ConnectFour.Screen;

import ConnectFour.ConnectFour;
import javafx.scene.Scene;

public abstract class  OriginScreen  {
	//各スクリーンを格納する変数
	protected Scene scene;
	
	//スクリーン転換時の処理
	//(例:SelectModeScreenからPlayGameScreenへの画面切り替え処理)
	public void changeNextScreen(OriginScreen sc) {
		/*
		//ConnctFour.getStage()で起動中のStageを持ってこれる
		//持ってきたStageに作成したSceneを設定(この時点で画面が切り替わる)
		ConnectFour.getStage().setScene(scene);
		*/
		ConnectFour.getStage().setScene(sc.getScene());
	}
	
	public Scene getScene() {
		return scene;
	}
}
