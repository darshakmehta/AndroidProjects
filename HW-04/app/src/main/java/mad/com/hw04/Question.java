package mad.com.hw04;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
    int id,answer;
    String text, image;
    ArrayList<String> choices = new ArrayList<String>();

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", answer=" + answer +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", choices=" + choices +
                '}';
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAnswer() {
        return answer;
    }
    public void setAnswer(int answer) {
        this.answer = answer;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public ArrayList<String> getChoices() {
        return choices;
    }
    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
