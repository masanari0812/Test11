import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GameManager {
	private List<List<PlayerAffiliation>> boardState;
	private int xSize, ySize;

	public GameManager(int xSize, int ySize) {
		boardState = new ArrayList<>();
		for (int x = 0; x < xSize; x++)
			boardState.add(new ArrayList<>());
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public PlayerAffiliation putOnTrout(PlayerAffiliation player, int x) {
		if (boardState.get(x).size() <= ySize)
			boardState.get(x).add(player);
		//ChatManager.outputLog("Column:" + String.valueOf(countColumnTrout(player)));
		//ChatManager.outputLog("Row:" + String.valueOf(countRowTrout(player)));
		ChatManager.outputLog("rSlash:" + String.valueOf(countRightSlashTrout(player)));
		ChatManager.outputLog("lSlash:" + String.valueOf(countLeftSlashTrout(player)));
		return PlayerAffiliation.NONE;
	}

	public PlayerAffiliation getTrout(int x, int y) {
		if (x < 0 || x >= xSize || y < 0 || y >= boardState.get(x).size())
			return PlayerAffiliation.NONE;
		return boardState.get(x).get(y);

	}

	public int getFirstNoneTrout(int x) {
		return boardState.get(x).size() - 1;
	}

	public class useSkill implements EventHandler<MouseEvent> {
		public useSkill(SkillEnum s) {
			switch (s) {
			case BREAK_TROUT:
			case REMOVE_RULE:
			case ROTATE_BOARD:
			}

		}

		@Override
		public void handle(MouseEvent e) {

		}
	}

	public int countColumnTrout(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < xSize; x++) {
			count = 0;
			for (int y = 0; y < ySize; y++) {
				if (getTrout(x, y).equals(team))
					count++;
				else
					count = 0;
				if (count >= 4) {
					count = 0;
					point++;

				}
			}

		}
		return point;
	}

	public int countRowTrout(PlayerAffiliation team) {
		int count, point = 0;
		for (int y = 0; y < ySize; y++) {
			count = 0;
			for (int x = 0; x < xSize; x++) {
				if (getTrout(x, y).equals(team))
					count++;
				else
					count = 0;
				if (count >= 4) {
					count = 0;
					point++;

				}
			}

		}
		return point;
	}

	public int countRightSlashTrout(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < xSize; x++) {
			count = 0;
			for (int y = 0; y < ySize; y++) {
				if (getTrout(x + y, y).equals(team))
					count++;
				else
					count = 0;
				if (count >= 4) {
					count = 0;
					point++;

				}
			}

		}
		return point;
	}

	public int countLeftSlashTrout(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < xSize; x++) {
			count = 0;
			for (int y = 0; y < ySize; y++) {
				if (getTrout(xSize - x - y - 1, y).equals(team))
					count++;
				else
					count = 0;
				if (count >= 4) {
					count = 0;
					point++;

				}
			}

		}
		return point;
	}

	public boolean breakTrout(int x,int y) {
		if(getTrout(x,y).equals(PlayerAffiliation.NONE))
			return false;
		
		return true;
	}
	

}
