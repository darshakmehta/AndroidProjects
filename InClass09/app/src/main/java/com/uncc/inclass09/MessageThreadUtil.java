package com.uncc.inclass09;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 11/6/2017.
 */

public class MessageThreadUtil {
    static public class MusicJSONParser {
        static ArrayList<MessageThreadResponse> parseTracks(String in) throws JSONException {
            JSONObject root = new JSONObject(in);
            ArrayList<MessageThreadResponse> responseList = new ArrayList<MessageThreadResponse>();
            MessageThreadResponse messageThreadResponse;
            if(root.has("status")) {

                if ("ok".equalsIgnoreCase(root.getString("status"))) {
                    JSONArray jsonArray = root.getJSONArray("threads");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        messageThreadResponse = new MessageThreadResponse();
                        JSONObject messageJSONObject = jsonArray.getJSONObject(i);
                        if (messageJSONObject.has("title"))
                            messageThreadResponse.setTitle(messageJSONObject.getString("title"));
                        if (messageJSONObject.has("id"))
                            messageThreadResponse.setId(messageJSONObject.getString("id"));
                        if (messageJSONObject.has("user_id"))
                            messageThreadResponse.setUser_id(messageJSONObject.getString("user_id"));
                            responseList.add(messageThreadResponse);
                    }
                }
            }
            return responseList;
        }
    }
}
