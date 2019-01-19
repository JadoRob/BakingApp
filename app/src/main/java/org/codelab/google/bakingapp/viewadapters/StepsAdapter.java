package org.codelab.google.bakingapp.viewadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.codelab.google.bakingapp.R;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.Steps;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    public static final String TAG = StepsAdapter.class.getSimpleName();
    private Recipe mRecipe;
    private List<Steps> mSteps;
    private OnItemClickListener mListener;
    Boolean mNoDescription;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

    public StepsAdapter(Recipe recipe) {
        mRecipe = recipe;
        mSteps = recipe.getSteps();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        StepViewHolder stepViewHolder = new StepViewHolder(v);
        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        int number = mSteps.get(position).getId();
        String stepIndex;
        String shortDescription;
        //displays short description at the start of the list if one is provided. If the default
        //place holder exists, it only displays the recipe name.
        if (position == 0) {
            stepIndex = "Recipe Introduction";
            holder.mStepView.setText(stepIndex);
//            if (!mNoDescription) {
//                shortDescription = mSteps.get(position).getShortDescription();
//                holder.mDescView.setText(shortDescription);
//            }
            return;
        }
        stepIndex = "Step: " + number;
        shortDescription = mSteps.get(position).getShortDescription();
        holder.mDescView.setText(shortDescription);
        holder.mStepView.setText(stepIndex);
    }

    @Override
    public int getItemCount() { return mSteps.size(); }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        public TextView mStepView;
        public TextView mDescView;

        public StepViewHolder(View itemView) {
            super(itemView);
            mStepView = itemView.findViewById(R.id.step_name);
            mDescView = itemView.findViewById(R.id.short_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
