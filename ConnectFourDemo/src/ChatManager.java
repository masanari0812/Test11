import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatManager {
	private VBox chatField;
	private static TextArea chatHistory;
	private TextField chatBox;

	public ChatManager() {
		this.chatField = new VBox();
		ChatManager.chatHistory = new TextArea();
		chatHistory.setEditable(false);
		this.chatBox = new TextField();
		chatField.getChildren().addAll(chatHistory, chatBox);

	}

	public static void outputLog(String str) {
		chatHistory.appendText(str + "\n");
	}

	public VBox getChatField() {
		return chatField;
	}
}
