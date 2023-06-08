package Model.ChatBot;

public class ChatBot {
    private String chat;

    public ChatBot() {
        this.chat = null;
    }
    public ChatBot builder() {
        return new ChatBot();
    }

    public String getChat() {
        return this.chat;
    }

    public void setChat(String txt) {
        this.chat = txt;
    }
}
