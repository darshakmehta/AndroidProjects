package mad.com.recipe_puppy_finder;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeUtil {
    static public class RecipesPullParse {
        static public ArrayList<Recipe> parseRecipes(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF_8");
            Recipe recipe = null;
            ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
            int event = parser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("recipe")){
                            recipe =  new Recipe();
                        } else if(parser.getName().equals("title")){
                            recipe.setTitle(parser.nextText().trim());
                        } else if(parser.getName().equals("href")){
                            recipe.setHref(parser.nextText().trim());
                        } else if(parser.getName().equals("ingredients")){
                            recipe.setIngredients(parser.nextText().trim());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("recipe")){
                            recipeList.add(recipe);
                            recipe = null;
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
            return recipeList;

        }
    }
}
