package mad.com.inclass07;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by darsh on 10/23/2017.
 */

public class AppUtil {
    static public class AppJSONParser {
        static ArrayList<App> parseMusic(String in) throws JSONException {
            ArrayList<App> appList = new ArrayList<App>();
            JSONObject root = new JSONObject(in);
            JSONObject feed = root.getJSONObject("feed");
            JSONArray entry = feed.getJSONArray("entry");

            for (int i = 0; i < entry.length(); i++) {
                App app = new App();
                JSONObject appObject = entry.getJSONObject(i);
                if (appObject.has("im:name")) {
                    JSONObject nameObject = appObject.getJSONObject("im:name");
                    if (nameObject.has("label")) {
                        app.setName(nameObject.getString("label").trim());
                    }
                }
                if (appObject.has("im:price")) {
                    JSONObject priceObject = appObject.getJSONObject("im:price");
                    if (priceObject.has("label")) {
                        //Log.d("demo",priceObject.getString("label"));
                        app.setPrice(priceObject.getString("label").substring(1));
                    }
                }
                JSONArray imageArray = appObject.getJSONArray("im:image");
                for (int j = 0; j < imageArray.length(); j++) {
                    if (j == 0) {
                        JSONObject imageObject = imageArray.getJSONObject(j);
                        app.setSmall_thumb_url(imageObject.getString("label").trim());
                    } else if (j == 2) {
                        JSONObject imageObject = imageArray.getJSONObject(j);
                        app.setLarge_thumb_url(imageObject.getString("label").trim());
                    }
                }
                appList.add(app);
            }
            return appList;

        }
    }
}
