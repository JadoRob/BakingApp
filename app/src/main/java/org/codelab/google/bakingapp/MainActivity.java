package org.codelab.google.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.network.JsonRecipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_display);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonRecipes jsonRecipes = retrofit.create(JsonRecipes.class);

        Call<List<Recipe>> call = jsonRecipes.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Recipe> recipies = response.body();

                for (Recipe recipe : recipies) {
                    StringBuilder content = new StringBuilder();
                    content.append("ID: ").append(recipe.getId()).append("\n");
                    content.append("Name: ").append(recipe.getName()).append("\n");
                    content.append("Servings: ").append(recipe.getServings()).append("\n");
                    content.append("Image: ").append(recipe.getImage()).append("\n");
                    content.append("Ingredients: ");
                    List<Ingredients> ingredients = recipe.getIngredients();
                    for (Ingredients ingredients1 : ingredients) {
                        content.append(ingredients1.getIngredient()).append(", ");
                    }
                    content.append("\n");
                    List<Steps> steps = recipe.getSteps();
                    for (Steps steps1 : steps) {
                        content.append("Step: ").append(steps1.getId()+1).append("\n");
                        content.append("Short Description: ").append(steps1.getShortDescription()).append("\n");
                        content.append("Description: ").append(steps1.getDescription()).append("\n");
                        content.append("Video URL: ").append(steps1.getThumbnailURL()).append("\n");
                        content.append("Thumbnail URL: ").append(steps1.getVideoURL()).append("\n");
                    }
                    content.append("\n");

                    textViewResult.append(content.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
