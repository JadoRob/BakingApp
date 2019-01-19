package org.codelab.google.bakingapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ingredients_table")
public class Ingredients {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredients(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }


}
