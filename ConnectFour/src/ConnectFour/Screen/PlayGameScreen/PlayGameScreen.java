package ConnectFour.Screen.PlayGameScreen;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ConnectFour.ConnectFour;
import ConnectFour.Communication.CommunicationObject;
import ConnectFour.Communication.ServerManager;
import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.ResultScreen.ResultScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayGameScreen extends OriginScreen {
	private List<List<PlayerAffiliation>> boardState;
	private int column, row;
	private int used_skill = 0; // スキルを使用した回数を判断する変数
	private boolean online;
	private boolean host;
	private boolean end;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Thread onlineMgr;
	private PlayerAffiliation turn;

	// columnとrowをコンストラクタで取得
	public PlayGameScreen(boolean online, int column, int row) {
		this.online = online;
		this.end = false;
		this.turn = PlayerAffiliation.PLAYER1;
		if (online) {
			Button stopBT = new Button("Stop Matching");
			Text text = new Text("Matching now");
			BorderPane bp = new BorderPane();
			bp.setCenter(text);
			bp.setBottom(stopBT);
			ConnectFour.getStage().setScene(new Scene(bp, 400, 300));
			onlineMgr = new ServerManager(this);
			onlineMgr.start();
		} else {
			this.column = column;
			this.row = row;
			makeBoard();
		}
	}

	public void makeBoard() {
		System.out.println("d");
		if (online) {
			try {
				if (host) {
					oos.writeObject(new CommunicationObject("Player1", column, row));
					oos.flush();
				} else {
					while (true) {
						CommunicationObject size = (CommunicationObject) ois.readObject();
						if (size == null)
							continue;
						column = size.getX();
						row = size.getY();
						break;
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println("d");
		boardState = new ArrayList<>();
		for (int x = 0; x < column; x++) { // 列の数だけArrayListを追加
			boardState.add(new ArrayList<>());
		}

		reloadBoard();

	}

	// x 列の一番下のマスを染める
	public PlayerAffiliation putOnSpace(PlayerAffiliation player, int x) { // x: 列の場所 0 to 6
		if (boardState.get(x).size() <= row) {
			boardState.get(x).add(player);
		}
		return PlayerAffiliation.NONE;
	}

	// 特定のマスを返り値として返す
	public PlayerAffiliation getSpace(int x, int y) {
		if (x < 0 || x >= column || y < 0 || y >= boardState.get(x).size()) {
			return PlayerAffiliation.NONE;
		}
		return boardState.get(x).get(y);
	}

	// x 列 y 行目のマスをplayerの色で染め，盤面を更新
	public void setSpace(PlayerAffiliation player, int x, int y) {
		if (player == PlayerAffiliation.NONE)
			return;
		boardState.get(x).remove(y);
		boardState.get(x).add(y, player); // y: 追加する場所  player: 追加する値
		reloadBoard();
	}

	// x 列の一番下のマスを染め，盤面を更新
	public void setSpace(PlayerAffiliation player, int x) {
		if (player == PlayerAffiliation.NONE || player != turn||end)
			return;
		boardState.get(x).add(player);
		System.out.println(player.toString());
		reloadBoard();
		if (judgeWin()) {
			this.end=true;
			Stage simpleResult = new Stage();
			Text result = new Text(turn.toString() + " Win!");
			result.setFont(new Font(25));
			Button nextScreen = new Button("OK");
			nextScreen.setOnMousePressed(event -> {
				simpleResult.hide();
				if (player == PlayerAffiliation.PLAYER1)
					changeNextScreen(new ResultScreen(true,online,column,row));
				else
					changeNextScreen(new ResultScreen(false,online,column,row));
			});
			VBox sr = new VBox();
			sr.getChildren().addAll(result, nextScreen);
			simpleResult.setScene(new Scene(sr));
			simpleResult.show();
		}
			changeTurn();
	}

	// スキルの処理(青色のマスで層を作る)
	public void activateSkill(List<List<PlayerAffiliation>> boardState) {
		used_skill += 1;
		int count;

		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (count == 0 && boardState.get(x).get(row - 1 - y) == PlayerAffiliation.NONE) {
					setSpace(PlayerAffiliation.BLOCK, x, row - 1 - y);
					count += 1;
				}
			}
		}
		reloadBoard();
	}

	// 盤面の生成・更新，イベントハンドラの登録(マウス，ボタン)
	// used_skill が 2 の場合，ボタンは生成しない
	public void reloadBoard() {
		HBox hb = new HBox();
		hb.setSpacing(5);
		for (int x = 0; x < column; x++) {
			VBox vb;
			hb.getChildren().add(vb = new VBox());
			vb.setSpacing(5);
			for (int y = row - 1; y >= 0; y--) {
				Circle space = new Circle(40, 50, 50); // space: マス
				space.setFill(getSpace(x, y).getColor());
				vb.getChildren().add(space);
			}
			vb.setOnMousePressed(new ClickBoardEventHandler(x));
		}

		VBox sideBar = new VBox();
		if (used_skill != 2) {
			Button bt = new Button("Skill");
			bt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			Rectangle r = new Rectangle(40, 50, 120, 360);
			r.setFill(Color.GREY);
			sideBar.getChildren().addAll(r, bt);
			bt.setOnAction(new ClickButtonEventHandler(boardState));
		} else {
			Rectangle r = new Rectangle(40, 50, 120, 360);
			r.setFill(Color.GREY);
			sideBar.getChildren().add(r);
		}
		hb.getChildren().add(sideBar);
		this.scene = new Scene(hb);
		ConnectFour.getStage().setScene(scene);
	}

	// x 列の要素数を返す
	public int getFirstNoneSpace(int x) {
		return boardState.get(x).size();
	}

	// 盤面の把握 列の確認
	public int countColumnSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	// 行の確認
	public int countRowSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int y = 0; y < row; y++) {
			count = 0;
			for (int x = 0; x < column; x++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	// 斜めの確認1
	public int countRightSlashSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x + y, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	// 斜めの確認2
	public int countLeftSlashSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(column - x - y - 1, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	public boolean breakSpace(int x, int y) {
		if (getSpace(x, y).equals(PlayerAffiliation.NONE)) {
			return false;
		} else {
			return true;
		}
	}

	public void setHost(boolean host) {
		this.host = host;
	}

	public boolean getOnline() {
		return online;
	}

	public void setObjectInputStream(InputStream is) throws IOException {
		this.ois = new ObjectInputStream(is);
	}

	public void setObjectOutputStream(OutputStream os) throws IOException {
		this.oos = new ObjectOutputStream(os);
	}

	public void setOnlineMgr(Thread t) {
		this.onlineMgr = t;
	}

	public void changeTurn() {
		switch (turn) {
		case PLAYER1:
			this.turn = PlayerAffiliation.PLAYER2;
			setComTrout();
			break;
		case PLAYER2:
			this.turn = PlayerAffiliation.PLAYER1;
			break;
		default:
			break;
		}
	}

	public boolean judgeWin() {
		if (countRowSpace(turn) > 0 || countColumnSpace(turn) > 0 || countRightSlashSpace(turn) > 0
				|| countLeftSlashSpace(turn) > 0)
			return true;
		return false;
	}

	public void setComTrout() {
		Random rand = new Random();
		int x = rand.nextInt(column);
		while (getFirstNoneSpace(x) == row)
			x = rand.nextInt(column);
		;
		setSpace(PlayerAffiliation.PLAYER2, x);
	}

	// マウスでマスをクリックしたら赤or黄色に染まる処理
	class ClickBoardEventHandler implements EventHandler<MouseEvent> {
		private int x;
		private int y;

		public ClickBoardEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public ClickBoardEventHandler(int x) {
			this.x = x;
		}

		@Override
		public void handle(MouseEvent e) {
			if (e.isPrimaryButtonDown() && getFirstNoneSpace(x) != row) {
				setSpace(PlayerAffiliation.PLAYER1, x);
			} else if (e.isSecondaryButtonDown())
				;//setSpace(PlayerAffiliation.PLAYER2, x);
			System.out.println(String.valueOf(x) + " " + String.valueOf(y));

		}
	}

	// Skillボタンをクリックしたらスキルの効果を反映させる処理
	class ClickButtonEventHandler implements EventHandler<ActionEvent> {
		private List<List<PlayerAffiliation>> boardState;

		public ClickButtonEventHandler(List<List<PlayerAffiliation>> boardState) { // 盤面の情報を受け取る
			this.boardState = boardState;
		}

		@Override
		public void handle(ActionEvent e) {
			activateSkill(boardState);
			System.out.println("activate skill");
		}
	}

}
