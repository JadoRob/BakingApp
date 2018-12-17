package org.codelab.google.bakingapp.viewadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.codelab.google.bakingapp.R;
import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.Steps;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    public static final String TAG = StepsAdapter.class.getSimpleName();
    private Recipe mRecipe;
    private List<Ingredients> mIngredientList;
    private List<Steps> mSteps;

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        private TextView mStepView;
        private TextView mDescView;
        private TextView mIngredientsView;

        public StepViewHolder(View itemView) {
            super(itemView);
            mStepView = itemView.findViewById(R.id.step_name);
            mDescView = itemView.findViewById(R.id.short_description);
            //mIngredientsView = itemView.findViewById(R.id.ingredients_view);
        }
    }

    public StepsAdapter(Recipe recipe) {
        mRecipe = recipe;
        mIngredientList = recipe.getIngredients();
        mSteps = recipe.getSteps();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        StepViewHolder stepViewHolder = new StepViewHolder(v);
        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String shortDescription = mSteps.get(position).getShortDescription();
        int stepNumber = mSteps.get(position).getId() + 1;
        holder.mDescView.setText(shortDescription);
        holder.mStepView.setText("Step " + stepNumber + ":");
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

}
