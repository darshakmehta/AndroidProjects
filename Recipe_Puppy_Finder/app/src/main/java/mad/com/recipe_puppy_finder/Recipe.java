package mad.com.recipe_puppy_finder;

import java.util.ArrayList;

public class Recipe {
    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
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

    String title, href, ingredients;




}
