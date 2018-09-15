package com.uncc.inclass09;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by gaurav on 11/7/2017.
 */

public class ChatRoomThreadUtil {
    static public class MusicJSONParser {
        static ArrayList<ChatRoomThreadResponse> parseTracks(String in) throws JSONException {
            JSONObject root = new JSONObject(in);
            ArrayList<ChatRoomThreadResponse> responseList = new ArrayList<ChatRoomThreadResponse>();
            ChatRoomThreadResponse messageThreadResponse;
            if(root.has("status")) {
                if ("ok".equalsIgnoreCase(root.getString("status"))) {
                    JSONArray jsonArray = root.getJSONArray("messages");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        messageThreadResponse = new ChatRoomThreadResponse();
                        JSONObject messageJSONObject = jsonArray.getJSONObject(i);
                        if (messageJSONObject.has("user_fname"))
                            messageThreadResponse.setUser_fname(messageJSONObject.getString("user_fname"));
                        if (messageJSONObject.has("user_lname"))
                            messageThreadResponse.setUser_lname(messageJSONObject.getString("user_lname"));
                        if (messageJSONObject.has("user_id"))
                            messageThreadResponse.setUser_id(messageJSONObject.getString("user_id"));
                        if (messageJSONObject.has("id"))
                            messageThreadResponse.setId(messageJSONObject.getString("id"));
                        if (messageJSONObject.has("message"))
                            messageThreadResponse.setMessage(messageJSONObject.getString("message"));
                        if (messageJSONObject.has("created_at")) {
                            PrettyTime p = new PrettyTime();
                            String pattern = "yyyy-MM-dd HH:mm:ss";
                            Date date = null;
                            try {
                                DateFormat dateFormat = new SimpleDateFormat(pattern);
                                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                                date = dateFormat.parse(messageJSONObject.getString("created_at"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            messageThreadResponse.setCreated_at(p.format(date));
                        }
                        responseList.add(messageThreadResponse);
                    }
                }
            }
            return responseList;
        }
    }
}
