package org.codelab.google.bakingapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "recipes") public class Recipe {

        @PrimaryKey
        private int id;
        private String name;
        private List<Ingredients> ingredients = null;
        private List<Steps> steps = null;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
