package Service;

import DataAccess.DataAccess;
import Model.ChatBot.ChatBot;
import Model.Form.Feedback;
import Model.Product.Product;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ChatBotService {
    private ChatBot GPT;

    public ChatBotService() {
        this.GPT = new ChatBot();
    }

    public ChatBotService builder() {
        return new ChatBotService();
    }

    public String getChat() {
        return GPT.getChat();
    }

    public void setChat(String txt) {
        GPT.setChat(txt);
    }

    public TreeMap<Product, String> getTopProduct() {
        return DataAccess.getTopProducts();
    }
    public void response(String userMessages) {
        String rep;
        if(userMessages.toLowerCase().equals("hello")) {
            this.setChat("Hello! How Can I help you?");
        }
        else if(userMessages.toLowerCase().contains("most rated games")) {
            rep = "The top rated products: ";
            for(Map.Entry<Product, String> tmp : getTopProduct().entrySet()) {
                rep += " " + tmp.getKey().getTitle();
            }
            this.setChat(rep);
        }
        else if(userMessages.toLowerCase().contains("most hated games")) {
            rep = "The most hated products: ";
            for(Feedback fb : FeedbackService.getLowFeedBack()) {
                rep += " " + fb.getProductId();
            }
            this.setChat(rep);
        }
        else if(userMessages.toLowerCase().contains("bye")) {
            rep = "See you again!";
            this.setChat(rep);
        }
        else {
            rep = "Sorry I dont get what do you mean?";
            this.setChat(rep);
        }
    }
}
