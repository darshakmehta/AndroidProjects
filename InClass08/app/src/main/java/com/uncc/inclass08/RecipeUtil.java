package com.uncc.inclass08;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gaurav on 9/25/2017.
 */

public class RecipeUtil {

    static public class PersonJsonParser{
        static ArrayList<Recepie> parsePerson(String in) throws JSONException {
            ArrayList<Recepie> personList = new ArrayList<Recepie>();
            JSONObject root = new JSONObject(in);
            JSONArray jsonArray = root.getJSONArray("results");

            if(jsonArray!=null && jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject personJsonObject = jsonArray.getJSONObject(i);
                    Recepie person = Recepie.createRecepie(personJsonObject);
                    personList.add(person);
                }
            }
            return personList;
        }
    }
}
