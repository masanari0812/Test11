import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ConnectFourDemo extends Application {
	private GameManager gm;
	private HBox gb;
	private ChatManager cm;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		int xSize = 7, ySize = 6;
		this.gm = new GameManager(xSize, ySize);
		this.gb = new HBox();
		gb = new HBox();
		gb.setSpacing(5);
		VBox clvb;
		for (int x = 0; x < xSize; x++) {
			gb.getChildren().add(clvb = new VBox());
			clvb.setSpacing(5);
			for (int y = 0; y < ySize; y++) {
				Circle trout = new Circle(40, 50, 50);
				trout.setFill(Color.GREY);
				VBox column = (VBox) gb.getChildren().get(x);
				column.getChildren().add(trout);
			}
			gb.getChildren().get(x).setOnMousePressed(new ClickTroutEvent(x));
		}

		VBox sideBar = new VBox();
		HBox skillButtons = new HBox();
		Circle sk1 = new Circle(25, 25, 25), sk2 = new Circle(25, 25, 25), sk3 = new Circle(25, 25, 25);
		skillButtons.getChildren().addAll(sk1, sk2, sk3);
		this.cm = new ChatManager();
		sideBar.getChildren().addAll(cm.getChatField(), skillButtons);
		gb.getChildren().addAll(sideBar);
		Scene sc = new Scene(gb);
		stage.setScene(sc);
		stage.setTitle("ConnectFourDemo");
		stage.show();
	}

	class ClickTroutEvent implements EventHandler<MouseEvent> {
		private int x;
		private int y;

		public ClickTroutEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public ClickTroutEvent(int x) {
			this.x = x;
		}

		@Override
		public void handle(MouseEvent e) {
			VBox column = (VBox) gb.getChildren().get(x);
			for (Node node : column.getChildren()) {
				Circle trout = (Circle) node;
				if (trout.getFill() == Color.GRAY) {
					if (e.isPrimaryButtonDown()) {
						trout.setFill(PlayerAffiliation.PLAYER1.getColor());
						gm.putOnTrout(PlayerAffiliation.PLAYER1, x);

					} else if (e.isSecondaryButtonDown()) {
						trout.setFill(PlayerAffiliation.PLAYER2.getColor());
						gm.putOnTrout(PlayerAffiliation.PLAYER2, x);
					}
					break;
				}
			}
		}
	}

}

// mian method
// start method
// EventHandler