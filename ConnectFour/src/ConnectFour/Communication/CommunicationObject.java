package ConnectFour.Communication;

import java.io.Serializable;

public class CommunicationObject implements Serializable {
	private TypeObject to;
	private String text;
	private int x;
	private int y;

	public CommunicationObject(String name, int column, int row) {
		this.to = TypeObject.FirstInfo;
		this.text = name;
		this.x = column;
		this.y = row;
	}

	public CommunicationObject(int x) {
		this.to = TypeObject.SetSpace;
		this.x = x;
	}

	public CommunicationObject(int x, int y) {
		this.to = TypeObject.UseSkill;
		this.x = x;
		this.y = y;
	}

	public CommunicationObject(String chat) {
		this.to = TypeObject.ChatText;
		this.text = chat;
	}

	public TypeObject getTypeObject() {
		return to;
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private enum TypeObject {
		FirstInfo, SetSpace, UseSkill, ChatText,
	}
}
