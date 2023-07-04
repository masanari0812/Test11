import javafx.scene.paint.Color;

public enum PlayerAffiliation {
	NONE(0x0), PLAYER1(0x1), PLAYER2(0x2);

	final private Color color;

	PlayerAffiliation(int i) {
		switch (i) {
		case 0x0:
			color = Color.GRAY;
			break;
		case 0x1:
			color = Color.RED;
			break;
		case 0x2:
			color = Color.YELLOW;
			break;
		default:
			color = Color.MEDIUMPURPLE;
		}
	}
	Color getColor() {
		return color;
	}
}
