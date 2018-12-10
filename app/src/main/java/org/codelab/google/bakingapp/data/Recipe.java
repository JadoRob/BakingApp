package org.codelab.google.bakingapp.data;

import java.util.List;

public class Recipe {

        private int id;
        private String name;
        private List<Ingredients> ingredients = null;
        private List<Steps> steps = null;
        private int servings;
        private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
