package ConnectFour.Screen.PlayGameScreen;

import javafx.scene.paint.Color;

// プレイヤーを識別する処理
public enum PlayerAffiliation {
	NONE(0), PLAYER1(1), PLAYER2(2), BLOCK(3);
	
	final private Color color;
	
	PlayerAffiliation(int i) {
		switch(i) {
		
		case 0 :
			color = Color.GREY;				// 埋まっていないマス
			break;
		case 1 :
			color = Color.RED;				// PLAYER1は赤いマス
			break;
		case 2 :
			color = Color.YELLOW;			// PLAYER2は黄色いマス
			break;
		case 3 :
			color = Color.BLUE;				// スキルのブロックの色
			break;
		default :
			color = Color.MEDIUMPURPLE;	// 例外
			break;
		}
	}
	
	Color getColor() {
		return color;
	}
}
