package mad.com.hw04;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class QuestionUtil {
    static public class QuestionJSONParser {
        static ArrayList<Question> parseQuestions(String in) throws JSONException {
            ArrayList<Question> questionList = new ArrayList<Question>();
            JSONObject root = new JSONObject(in);
            JSONArray questionsJSONArray = root.getJSONArray("questions");

            for(int i=0; i<questionsJSONArray.length(); i++) {
                JSONObject questionJSONObject = questionsJSONArray.getJSONObject(i);
                Question question = new Question();
                if(questionJSONObject.has("id"))
                question.setId(questionJSONObject.getInt("id"));
                if(questionJSONObject.has("text"))
                question.setText(questionJSONObject.getString("text"));
                if(questionJSONObject.has("image"))
                question.setImage(questionJSONObject.getString("image"));

                if(questionJSONObject.has("choices")) {
                    JSONObject choicesObject = questionJSONObject.getJSONObject("choices");
                    JSONArray choiceArray = choicesObject.getJSONArray("choice");
                    ArrayList<String> questionsChoices = new ArrayList<String>();
                    for (int j = 0; j < choiceArray.length(); j++) {
                        questionsChoices.add(choiceArray.getString(j));
                    }
                    if(choicesObject.has("answer"))
                    question.setAnswer(choicesObject.getInt("answer"));
                    question.setChoices(questionsChoices);
                }

                questionList.add(question);
                //Log.d("trying",question.toString());
            }
            return questionList;
        }
    }
}
