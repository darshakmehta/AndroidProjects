package com.uncc.inclass09;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaurav on 11/7/2017.
 */

public class DeleteMessageUtil {
    static public class RecipeJSONParser {
        static MessageThreadResponse parseRecipes(String in) throws JSONException {
            JSONObject parse = new JSONObject(in);
            MessageThreadResponse threadClass = new MessageThreadResponse();
            threadClass.setStatus(parse.getString("status"));
            JSONObject thread = parse.getJSONObject("thread");
            if(parse.getString("status").matches("ok")) {
                threadClass.setUser_id(thread.getString("user_id"));
                threadClass.setUser_lname(thread.getString("user_lname"));
                threadClass.setUser_fname(thread.getString("user_fname"));
                threadClass.setCreated_at(thread.getString("created_at"));
                threadClass.setTitle(thread.getString("title"));
                threadClass.setId(thread.getString("id"));

            }
            else{

            }
            return threadClass;
        }
    }
}
