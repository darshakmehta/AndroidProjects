package com.uncc.inclass08;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by gaurav on 9/25/2017.
 */

public class Recepie implements Serializable {

    String title,href,ingredients,thumbnil;

    public static Recepie createRecepie(JSONObject js) throws JSONException {
        Recepie recepie = new Recepie();
        recepie.setTitle(js.getString("title"));
        recepie.setHref(js.getString("href"));
        recepie.setIngredients(js.getString("ingredients"));
        recepie.setThumbnil(js.getString("thumbnail"));
        return recepie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getThumbnil() {
        return thumbnil;
    }

    public void setThumbnil(String thumbnil) {
        this.thumbnil = thumbnil;
    }

}
