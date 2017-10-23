package mad.com.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieUtil {
    static public class MovieSONParser {
        static ArrayList<Movie> parseMovie(String in) throws JSONException {
            ArrayList<Movie> movieList = new ArrayList<Movie>();
            JSONObject root = new JSONObject(in);
            JSONArray results = root.getJSONArray("results");

            for(int i = 0; i<results.length();i++) {
                JSONObject movieObject = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setSetStar(android.R.drawable.btn_star_big_off+"");
                if(movieObject.has("original_title"))
                    movie.setMovie_name(movieObject.getString("original_title").trim());
                if(movieObject.has("overview"))
                    movie.setOverview(movieObject.getString("overview").trim());
                if(movieObject.has("release_date"))
                    movie.setRelease_date(movieObject.getString("release_date").trim());
                if(movieObject.has("vote_average"))
                    movie.setRating(movieObject.getString("vote_average").trim());
                if(movieObject.has("popularity"))
                    movie.setPopularity(movieObject.getString("popularity").trim());
                if(movieObject.has("poster_path"))
                    movie.setPoster_path(movieObject.getString("poster_path").trim());



                movieList.add(movie);
            }
            return movieList;

        }
    }
}
