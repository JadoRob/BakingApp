package org.codelab.google.bakingapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeFragment fragment = new RecipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

//        DetailFragment fragment = new DetailFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

//        StepsFragment fragment = new StepsFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();



    }
}
